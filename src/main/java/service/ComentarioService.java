package service;

import dao.AnexoDAO;
import dao.ComentarioDAO;
import dao.PublicacionesDAO;
import dao.UsuariosDAO;
import modelo.Anexo;
import modelo.Comentario;
import modelo.Publicaciones;
import modelo.Usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComentarioService {

    private final ComentarioDAO comentarioDAO = new ComentarioDAO();
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
    private final PublicacionesDAO publicacionesDAO = new PublicacionesDAO();
    private final AnexoDAO anexoDAO = new AnexoDAO();

    // ===============================
    // VALIDACIONES
    // ===============================

    private void validarComentario(Comentario c) {
        if (c == null) {
            throw new IllegalArgumentException("El comentario no puede ser null");
        }
        if (c.getUsuarioid() == null || c.getUsuarioid().getId() == 0) {
            throw new IllegalArgumentException("Debe existir un usuario válido");
        }
        if (c.getPublicacion() == null || c.getPublicacion().getIdPublicacion() == 0) {
            throw new IllegalArgumentException("Debe existir una publicación válida");
        }
        if (c.getCuerpo() == null || c.getCuerpo().isBlank()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío");
        }
        if (c.getFecha() == null) {
            c.setFecha(new Date()); // fallback automático
        }
    }

    // ===============================
    // CREAR COMENTARIO COMPLETO
    // ===============================

    public boolean crearComentarioCompleto(Comentario comentario, List<Anexo> anexos) {

        validarComentario(comentario);

        // Validar usuario
        Usuarios usuario = usuariosDAO.buscarPorId(comentario.getUsuarioid().getId());
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no existe en BD");
        }

        // Validar publicación
        Publicaciones pub = publicacionesDAO.buscarPorId(comentario.getPublicacion().getIdPublicacion());
        if (pub == null) {
            throw new IllegalArgumentException("La publicación no existe en BD");
        }

        // Insertar comentario
        boolean ok = comentarioDAO.insertar(comentario);
        if (!ok) return false;

        // Insertar anexos
        if (anexos != null) {
            for (Anexo a : anexos) {
                a.setComentario(comentario);
                anexoDAO.insertar(a);
            }
        }

        return true;
    }

    // ===============================
    // OBTENER UN COMENTARIO COMPLETO
    // ===============================

    public Comentario obtenerComentarioCompleto(int idComentario) {
        Comentario c = comentarioDAO.buscarPorId(idComentario);
        if (c == null) {
            return null;
        }

        // Usuario completo
        Usuarios usuario = usuariosDAO.buscarPorId(c.getUsuarioid().getId());
        c.setUsuarioid(usuario);

        // Publicación de este comentario
        Publicaciones pub = publicacionesDAO.buscarPorId(c.getPublicacion().getIdPublicacion());
        c.setPublicacion(pub);

        // Anexos de este comentario
        List<Anexo> anexos = new ArrayList<>();
        for (Anexo a : anexoDAO.listar()) {
            if (a.getComentario().getIdComentario() == idComentario) {
                anexos.add(a);
            }
        }
        c.setAnexos(anexos);

        return c;
    }

    // ===============================
    // LISTAR COMENTARIOS POR PUBLICACIÓN
    // ===============================

    public List<Comentario> listarComentariosDePublicacion(int idPublicacion) {
        List<Comentario> lista = new ArrayList<>();

        for (Comentario c : comentarioDAO.listar()) {
            if (c.getPublicacion().getIdPublicacion() == idPublicacion) {
                lista.add(obtenerComentarioCompleto(c.getIdComentario()));
            }
        }

        return lista;
    }

    // ===============================
    // ELIMINAR COMENTARIO
    // ===============================

    public boolean eliminarComentario(int idComentario) {
        return comentarioDAO.eliminar(idComentario);
    }

    // ===============================
    // LISTAR TODOS (BÁSICO)
    // ===============================

    public List<Comentario> listarComentarios() {
        return comentarioDAO.listar();
    }
}
