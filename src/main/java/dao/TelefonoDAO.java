package dao;

import ConexionBD.Conexion;
import modelo.Telefono;
import modelo.Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TelefonoDAO {
    public boolean insertar(Telefono t) {
        String sql = "INSERT INTO telefono (tipo, numero, usuariosid) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, t.getTipo());
            ps.setString(2, t.getNumero());
            ps.setInt(3, t.getUsuarios().getId()); // FK hacia Usuarios

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    t.setIdTelefono(rs.getInt(1)); // asigna el ID generado
                }
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar teléfono: " + e.getMessage());
        }

        return false;
    }

    public List<Telefono> listar() {
        List<Telefono> lista = new ArrayList<>();
        String sql = "SELECT * FROM telefono";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Telefono t = new Telefono();
                t.setIdTelefono(rs.getInt("idtelefono"));
                t.setTipo(rs.getInt("tipo"));
                t.setNumero(rs.getString("numero"));

                // Cargar solo el ID del usuario (opcional cargar objeto completo)
                Usuarios u = new Usuarios();
                u.setId(rs.getInt("usuariosid"));
                t.setUsuarios(u);

                lista.add(t);
            }

        } catch (Exception e) {
            System.out.println("Error al listar teléfonos: " + e.getMessage());
        }

        return lista;
    }

    public Telefono buscarPorId(int id) {
        String sql = "SELECT * FROM telefono WHERE idtelefono = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Telefono t = new Telefono();
                t.setIdTelefono(rs.getInt("idtelefono"));
                t.setTipo(rs.getInt("tipo"));
                t.setNumero(rs.getString("numero"));

                Usuarios u = new Usuarios();
                u.setId(rs.getInt("usuariosid"));
                t.setUsuarios(u);

                return t;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar teléfono: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Telefono t) {
        String sql = """
                UPDATE telefono
                SET tipo = ?, numero = ?, usuariosid = ?
                WHERE idtelefono = ?
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getTipo());
            ps.setString(2, t.getNumero());
            ps.setInt(3, t.getUsuarios().getId());
            ps.setInt(4, t.getIdTelefono());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar teléfono: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM telefono WHERE idtelefono = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar teléfono: " + e.getMessage());
        }

        return false;
    }

}
