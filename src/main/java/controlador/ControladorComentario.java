package controlador;

import modelo.Anexo;
import modelo.Comentario;
import service.ComentarioService;

import java.util.List;

public class ControladorComentario {

    private final ComentarioService comentarioService = new ComentarioService();


    public boolean crearComentario(Comentario comentario) {
        try {
            return comentarioService.crearComentarioCompleto(comentario, null);
        } catch (Exception e) {
            System.out.println("Error al crear comentario: " + e.getMessage());
            return false;
        }
    }


    public boolean crearComentarioConAnexos(Comentario comentario, List<Anexo> anexos) {
        try {
            return comentarioService.crearComentarioCompleto(comentario, anexos);
        } catch (Exception e) {
            System.out.println("Error al crear comentario con anexos: " + e.getMessage());
            return false;
        }
    }


    public Comentario obtenerComentarioCompleto(int idComentario) {
        try {
            return comentarioService.obtenerComentarioCompleto(idComentario);
        } catch (Exception e) {
            System.out.println("Error al obtener comentario completo: " + e.getMessage());
            return null;
        }
    }


    public List<Comentario> listarComentariosDePublicacion(int idPublicacion) {
        try {
            return comentarioService.listarComentariosDePublicacion(idPublicacion);
        } catch (Exception e) {
            System.out.println("Error al listar comentarios de la publicación: " + e.getMessage());
            return null;
        }
    }


    public List<Comentario> listarComentarios() {
        try {
            return comentarioService.listarComentarios();
        } catch (Exception e) {
            System.out.println("Error al listar comentarios: " + e.getMessage());
            return null;
        }
    }


    public boolean eliminarComentario(int idComentario) {
        try {
            return comentarioService.eliminarComentario(idComentario);
        } catch (Exception e) {
            System.out.println("Error al eliminar comentario: " + e.getMessage());
            return false;
        }
    }
}

