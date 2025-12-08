package dao;

import ConexionBD.Conexion;
import modelo.Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {

    public boolean insertar(Usuarios u) {
        String sql = "INSERT INTO usuarios (nombre, alias, contrasenia, codigodeusuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getAlias());
            ps.setString(3, u.getContrasenia());
            ps.setString(4, u.getCodigoDeUsuario());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    u.setId(rs.getInt(1)); // asigna la PK generada
                }
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
        }

        return false;
    }

    public List<Usuarios> listar() {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuarios u = new Usuarios();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setAlias(rs.getString("alias"));
                u.setContrasenia(rs.getString("contrasenia"));
                u.setCodigoDeUsuario(rs.getString("codigodeusuario"));

                lista.add(u);
            }

        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    public Usuarios buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuarios u = new Usuarios();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setAlias(rs.getString("alias"));
                u.setContrasenia(rs.getString("contrasenia"));
                u.setCodigoDeUsuario(rs.getString("codigodeusuario"));
                return u;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar usuario por ID: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Usuarios u) {
        String sql = """
                UPDATE usuarios 
                SET nombre = ?, alias = ?, contrasenia = ?, codigodeusuario = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getAlias());
            ps.setString(3, u.getContrasenia());
            ps.setString(4, u.getCodigoDeUsuario());
            ps.setInt(5, u.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }

        return false;
    }
}
