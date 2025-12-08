package dao;

import ConexionBD.Conexion;
import modelo.Autor;
import modelo.Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {


    public boolean insertar(Autor a) {
        String sql = "INSERT INTO autor (nombre, usuariosid) VALUES (?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getNombreAutor());
            ps.setInt(2, a.getUsuarios().getId()); // FK hacia Usuarios

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    a.setIdAutor(rs.getInt(1)); // PK generada
                }
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar autor: " + e.getMessage());
        }

        return false;
    }

    public List<Autor> listar() {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autor";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Autor a = new Autor();
                a.setIdAutor(rs.getInt("id"));
                a.setNombreAutor(rs.getString("nombre"));

                Usuarios u = new Usuarios();
                u.setId(rs.getInt("usuariosid"));
                a.setUsuarios(u);

                lista.add(a);
            }

        } catch (Exception e) {
            System.out.println("Error al listar autores: " + e.getMessage());
        }

        return lista;
    }

    public Autor buscarPorId(int id) {
        String sql = "SELECT * FROM autor WHERE id = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Autor a = new Autor();
                a.setIdAutor(rs.getInt("id"));
                a.setNombreAutor(rs.getString("nombre"));

                Usuarios u = new Usuarios();
                u.setId(rs.getInt("usuariosid"));
                a.setUsuarios(u);

                return a;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar autor: " + e.getMessage());
        }

        return null;
    }


    public boolean actualizar(Autor a) {
        String sql = """
                UPDATE autor
                SET nombre = ?, usuariosid = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getNombreAutor());
            ps.setInt(2, a.getUsuarios().getId());
            ps.setInt(3, a.getIdAutor());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar autor: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM autor WHERE id = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar autor: " + e.getMessage());
        }

        return false;
    }
}
