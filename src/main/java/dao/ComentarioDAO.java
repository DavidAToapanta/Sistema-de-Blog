package dao;

import ConexionBD.Conexion;
import modelo.Comentario;
import modelo.Publicaciones;
import modelo.Usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    public boolean insertar(Comentario c) {
        String sql = """
                INSERT INTO comentario (cuerpo, fecha, usuariosid, publicacionesid)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getCuerpo());
            ps.setDate(2, new Date(c.getFecha().getTime())); // java.util.Date → java.sql.Date
            ps.setInt(3, c.getUsuarioid().getId());
            ps.setInt(4, c.getPublicacion().getIdPublicacion());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    c.setIdComentario(rs.getInt(1));
                }
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar comentario: " + e.getMessage());
        }

        return false;
    }

    public List<Comentario> listar() {
        List<Comentario> lista = new ArrayList<>();
        String sql = "SELECT * FROM comentario";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Comentario c = new Comentario();
                c.setIdComentario(rs.getInt("idcomentario"));
                c.setCuerpo(rs.getString("cuerpo"));
                c.setFecha(rs.getDate("fecha"));

                Usuarios u = new Usuarios();
                u.setId(rs.getInt("usuariosid"));
                c.setUsuarioid(u);

                Publicaciones p = new Publicaciones();
                p.setIdPublicacion(rs.getInt("publicacionesid"));
                c.setPublicacion(p);

                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("Error al listar comentarios: " + e.getMessage());
        }

        return lista;
    }

    public List<Comentario> listarPorPublicacion(int publicacionId) {
        List<Comentario> lista = new ArrayList<>();
        String sql = "SELECT * FROM comentario WHERE publicacionesid = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, publicacionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comentario c = new Comentario();
                c.setIdComentario(rs.getInt("idcomentario"));
                c.setCuerpo(rs.getString("cuerpo"));
                c.setFecha(rs.getDate("fecha"));

                Usuarios u = new Usuarios();
                u.setId(rs.getInt("usuariosid"));
                c.setUsuarioid(u);

                Publicaciones p = new Publicaciones();
                p.setIdPublicacion(publicacionId);
                c.setPublicacion(p);

                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("Error al listar comentarios por publicación: " + e.getMessage());
        }

        return lista;
    }

    public Comentario buscarPorId(int id) {
        String sql = "SELECT * FROM comentario WHERE idcomentario = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Comentario c = new Comentario();
                c.setIdComentario(rs.getInt("idcomentario"));
                c.setCuerpo(rs.getString("cuerpo"));
                c.setFecha(rs.getDate("fecha"));

                Usuarios u = new Usuarios();
                u.setId(rs.getInt("usuariosid"));
                c.setUsuarioid(u);

                Publicaciones p = new Publicaciones();
                p.setIdPublicacion(rs.getInt("publicacionesid"));
                c.setPublicacion(p);

                return c;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar comentario: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Comentario c) {
        String sql = """
                UPDATE comentario
                SET cuerpo = ?, fecha = ?, usuariosid = ?, publicacionesid = ?
                WHERE idcomentario = ?
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getCuerpo());
            ps.setDate(2, new Date(c.getFecha().getTime()));
            ps.setInt(3, c.getUsuarioid().getId());
            ps.setInt(4, c.getPublicacion().getIdPublicacion());
            ps.setInt(5, c.getIdComentario());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar comentario: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM comentario WHERE idcomentario = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar comentario: " + e.getMessage());
        }

        return false;
    }
}
