package dao;

import ConexionBD.Conexion;
import modelo.Publicaciones;
import modelo.Tags;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TagsDAO {

    public boolean insertar(Tags t) {
        String sql = "INSERT INTO tags (nombretag, publicacionesid) VALUES (?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, t.getNombreTag());
            ps.setInt(2, t.getPublicaciones().getIdPublicacion()); // FK publicación

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    t.setIdTag(rs.getInt(1)); // asigna PK generada
                }
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar Tag: " + e.getMessage());
        }

        return false;
    }

    public List<Tags> listar() {
        List<Tags> lista = new ArrayList<>();
        String sql = "SELECT * FROM tags";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tags t = new Tags();
                t.setIdTag(rs.getInt("idtags"));
                t.setNombreTag(rs.getString("nombretag"));

                Publicaciones p = new Publicaciones();
                p.setIdPublicacion(rs.getInt("publicacionesid"));
                t.setPublicaciones(p);

                lista.add(t);
            }

        } catch (Exception e) {
            System.out.println("Error al listar Tags: " + e.getMessage());
        }

        return lista;
    }

    public List<Tags> listarPorPublicacion(int publicacionId) {
        List<Tags> lista = new ArrayList<>();
        String sql = "SELECT * FROM tags WHERE publicacionesid = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, publicacionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tags t = new Tags();
                t.setIdTag(rs.getInt("idtags"));
                t.setNombreTag(rs.getString("nombretag"));

                Publicaciones p = new Publicaciones();
                p.setIdPublicacion(publicacionId);
                t.setPublicaciones(p);

                lista.add(t);
            }

        } catch (Exception e) {
            System.out.println("Error al listar Tags por publicación: " + e.getMessage());
        }

        return lista;
    }

    public Tags buscarPorId(int id) {
        String sql = "SELECT * FROM tags WHERE idtags = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Tags t = new Tags();
                t.setIdTag(rs.getInt("idtags"));
                t.setNombreTag(rs.getString("nombretag"));

                Publicaciones p = new Publicaciones();
                p.setIdPublicacion(rs.getInt("publicacionesid"));
                t.setPublicaciones(p);

                return t;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar Tag: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Tags t) {
        String sql = """
                UPDATE tags
                SET nombretag = ?, publicacionesid = ?
                WHERE idtags = ?
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getNombreTag());
            ps.setInt(2, t.getPublicaciones().getIdPublicacion());
            ps.setInt(3, t.getIdTag());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar Tag: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM tags WHERE idtags = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar Tag: " + e.getMessage());
        }

        return false;
    }
}
