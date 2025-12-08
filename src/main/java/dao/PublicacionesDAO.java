package dao;

import ConexionBD.Conexion;
import modelo.Autor;
import modelo.Publicaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PublicacionesDAO {

    public boolean insertar(Publicaciones p) {
        String sql = """
                INSERT INTO publicaciones 
                (titulo, descripcion, cuerpo, paisvisitado, ciudadvisitada, autorid) 
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getCuerpo());
            ps.setString(4, p.getPaisVisitado());
            ps.setString(5, p.getCiudadvisitada());
            ps.setInt(6, p.getAutores().getIdAutor()); // FK autor

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    p.setIdPublicacion(rs.getInt(1)); // asigna PK generada
                }
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar publicación: " + e.getMessage());
        }

        return false;
    }

    public List<Publicaciones> listar() {
        List<Publicaciones> lista = new ArrayList<>();
        String sql = "SELECT * FROM publicaciones";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Publicaciones p = new Publicaciones();
                p.setIdPublicacion(rs.getInt("idpublicacion"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setCuerpo(rs.getString("cuerpo"));
                p.setPaisVisitado(rs.getString("paisvisitado"));
                p.setCiudadvisitada(rs.getString("ciudadvisitada"));

                Autor autor = new Autor();
                autor.setIdAutor(rs.getInt("autorid"));
                p.setAutores(autor);

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al listar publicaciones: " + e.getMessage());
        }

        return lista;
    }

    public Publicaciones buscarPorId(int id) {
        String sql = "SELECT * FROM publicaciones WHERE idpublicacion = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Publicaciones p = new Publicaciones();
                p.setIdPublicacion(rs.getInt("idpublicacion"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setCuerpo(rs.getString("cuerpo"));
                p.setPaisVisitado(rs.getString("paisvisitado"));
                p.setCiudadvisitada(rs.getString("ciudadvisitada"));

                Autor autor = new Autor();
                autor.setIdAutor(rs.getInt("autorid"));
                p.setAutores(autor);

                return p;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar publicación: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Publicaciones p) {
        String sql = """
                UPDATE publicaciones
                SET titulo = ?, descripcion = ?, cuerpo = ?, paisvisitado = ?, ciudadvisitada = ?, autorid = ?
                WHERE idpublicacion = ?
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getCuerpo());
            ps.setString(4, p.getPaisVisitado());
            ps.setString(5, p.getCiudadvisitada());
            ps.setInt(6, p.getAutores().getIdAutor());
            ps.setInt(7, p.getIdPublicacion());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar publicación: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM publicaciones WHERE idpublicacion = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar publicación: " + e.getMessage());
        }

        return false;
    }
}
