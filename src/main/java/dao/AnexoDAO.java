package dao;

import ConexionBD.Conexion;
import modelo.Anexo;
import modelo.Comentario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AnexoDAO {

    public boolean insertar(Anexo a) {
        String sql = """
                INSERT INTO anexos (comentarioidcomentario, nombrearchivo, archivo, peso)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, a.getComentario().getIdComentario());
            ps.setString(2, a.getNombreArchivo());
            ps.setBytes(3, a.getArchivo());
            ps.setDouble(4, a.getPeso());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    a.setIdAnexo(rs.getInt(1));
                }
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar anexo: " + e.getMessage());
        }

        return false;
    }

    public List<Anexo> listar() {
        List<Anexo> lista = new ArrayList<>();
        String sql = "SELECT * FROM anexos";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Anexo a = new Anexo();
                a.setIdAnexo(rs.getInt("idanexo"));
                a.setNombreArchivo(rs.getString("nombrearchivo"));
                a.setArchivo(rs.getBytes("archivo"));
                a.setPeso(rs.getDouble("peso"));

                Comentario c = new Comentario();
                c.setIdComentario(rs.getInt("comentarioidcomentario"));

                a.setComentario(c);

                lista.add(a);
            }

        } catch (Exception e) {
            System.out.println("Error al listar anexos: " + e.getMessage());
        }

        return lista;
    }

    public List<Anexo> listarPorComentario(int idComentario) {
        List<Anexo> lista = new ArrayList<>();
        String sql = "SELECT * FROM anexos WHERE comentarioidcomentario = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idComentario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Anexo a = new Anexo();
                a.setIdAnexo(rs.getInt("idanexo"));
                a.setNombreArchivo(rs.getString("nombrearchivo"));
                a.setArchivo(rs.getBytes("archivo"));
                a.setPeso(rs.getDouble("peso"));

                Comentario c = new Comentario();
                c.setIdComentario(idComentario);
                a.setComentario(c);

                lista.add(a);
            }

        } catch (Exception e) {
            System.out.println("Error al listar anexos por comentario: " + e.getMessage());
        }

        return lista;
    }

    public Anexo buscarPorId(int id) {
        String sql = "SELECT * FROM anexos WHERE idanexo = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Anexo a = new Anexo();
                a.setIdAnexo(rs.getInt("idanexo"));
                a.setNombreArchivo(rs.getString("nombrearchivo"));
                a.setArchivo(rs.getBytes("archivo"));
                a.setPeso(rs.getDouble("peso"));

                Comentario c = new Comentario();
                c.setIdComentario(rs.getInt("comentarioidcomentario"));
                a.setComentario(c);

                return a;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar anexo: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Anexo a) {
        String sql = """
                UPDATE anexos
                SET nombrearchivo = ?, archivo = ?, peso = ?, comentarioidcomentario = ?
                WHERE idanexo = ?
                """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getNombreArchivo());
            ps.setBytes(2, a.getArchivo());
            ps.setDouble(3, a.getPeso());
            ps.setInt(4, a.getComentario().getIdComentario());
            ps.setInt(5, a.getIdAnexo());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar anexo: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM anexos WHERE idanexo = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar anexo: " + e.getMessage());
        }

        return false;
    }

}
