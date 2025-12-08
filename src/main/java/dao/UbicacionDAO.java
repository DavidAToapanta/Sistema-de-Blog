package dao;

import ConexionBD.Conexion;
import modelo.Ubicacion;
import modelo.Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO {

    public boolean insertar(Ubicacion u) {
        String sql = "INSERT INTO ubicacion (pais, ciudad, codigopostal, usuariosid) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getPais());
            ps.setString(2, u.getCiudad());
            ps.setString(3, u.getCodigoPostal());
            ps.setInt(4, u.getUsuario().getId()); // FK a Usuarios

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    u.setIdUbicacion(rs.getInt(1)); // asigna PK generada
                }
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar ubicación: " + e.getMessage());
        }

        return false;
    }

    public List<Ubicacion> listar() {
        List<Ubicacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ubicacion";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ubicacion u = new Ubicacion();
                u.setIdUbicacion(rs.getInt("idubicacion"));
                u.setPais(rs.getString("pais"));
                u.setCiudad(rs.getString("ciudad"));
                u.setCodigoPostal(rs.getString("codigopostal"));

                Usuarios usu = new Usuarios();
                usu.setId(rs.getInt("usuariosid"));
                u.setUsuario(usu);

                lista.add(u);
            }

        } catch (Exception e) {
            System.out.println("Error al listar ubicaciones: " + e.getMessage());
        }

        return lista;
    }

    public Ubicacion buscarPorId(int id) {
        String sql = "SELECT * FROM ubicacion WHERE idubicacion = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Ubicacion u = new Ubicacion();
                u.setIdUbicacion(rs.getInt("idubicacion"));
                u.setPais(rs.getString("pais"));
                u.setCiudad(rs.getString("ciudad"));
                u.setCodigoPostal(rs.getString("codigopostal"));

                Usuarios usu = new Usuarios();
                usu.setId(rs.getInt("usuariosid"));
                u.setUsuario(usu);

                return u;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar ubicación: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Ubicacion u) {
        String sql = """
                UPDATE ubicacion 
                SET pais = ?, ciudad = ?, codigopostal = ?, usuariosid = ?
                WHERE idubicacion = ?
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getPais());
            ps.setString(2, u.getCiudad());
            ps.setString(3, u.getCodigoPostal());
            ps.setInt(4, u.getUsuario().getId());
            ps.setInt(5, u.getIdUbicacion());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar ubicación: " + e.getMessage());
        }

        return false;
    }


    public boolean eliminar(int id) {
        String sql = "DELETE FROM ubicacion WHERE idubicacion = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar ubicación: " + e.getMessage());
        }

        return false;
    }

}
