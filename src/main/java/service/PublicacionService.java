package service;

import dao.AutorDAO;
import dao.ComentarioDAO;
import dao.PublicacionesDAO;
import dao.TagsDAO;
import modelo.Autor;
import modelo.Comentario;
import modelo.Publicaciones;
import modelo.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PublicacionService {

    private final PublicacionesDAO publicacionesDAO = new PublicacionesDAO();
    private final TagsDAO tagsDAO = new TagsDAO();
    private final ComentarioDAO comentarioDAO = new ComentarioDAO();
    private final AutorDAO autorDAO = new AutorDAO();

    // ===============================
    // VALIDACIONES
    // ===============================

    private void validarPublicacion(Publicaciones p) {
        if (p == null) {
            throw new IllegalArgumentException("La publicación no puede ser null");
        }
        if (p.getAutores() == null || p.getAutores().getIdAutor() == 0) {
            throw new IllegalArgumentException("Debe existir un autor válido");
        }
        if (p.getTitulo() == null || p.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        if (p.getCuerpo() == null || p.getCuerpo().isBlank()) {
            throw new IllegalArgumentException("El cuerpo es obligatorio");
        }
    }

    private List<Tags> limpiarTags(List<Tags> tags) {
        if (tags == null) return new ArrayList<>();

        // Quitar tags vacíos y repetidos
        return tags.stream()
                .filter(t -> t.getNombreTag() != null && !t.getNombreTag().isBlank())
                .collect(Collectors.toMap(
                        Tags::getNombreTag,
                        t -> t,
                        (t1, t2) -> t1
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    // ===============================
    // CREAR PUBLICACIÓN COMPLETA
    // ===============================

    public boolean crearPublicacionCompleta(Publicaciones p, List<Tags> tags) {

        validarPublicacion(p);

        // Validar que el autor exista en BD
        Autor autor = autorDAO.buscarPorId(p.getAutores().getIdAutor());
        if (autor == null) {
            throw new IllegalArgumentException("El autor no existe en la base de datos");
        }

        // Limpiar lista de tags
        List<Tags> tagsLimpios = limpiarTags(tags);

        // Insertar la publicación
        boolean ok = publicacionesDAO.insertar(p);
        if (!ok) return false;

        // Insertar tags asociados
        for (Tags t : tagsLimpios) {
            t.setPublicaciones(p);
            tagsDAO.insertar(t);
        }

        return true;
    }

    // ===============================
    // OBTENER PUBLICACIÓN COMPLETA (con tags + comentarios)
    // ===============================

    public Publicaciones obtenerPublicacionCompleta(int idPublicacion) {
        Publicaciones pub = publicacionesDAO.buscarPorId(idPublicacion);

        if (pub == null) {
            return null;
        }

        // Obtener autor
        Autor autor = autorDAO.buscarPorId(pub.getAutores().getIdAutor());
        pub.setAutores(autor);

        // Obtener tags
        List<Tags> tags = new ArrayList<>();
        for (Tags t : tagsDAO.listar()) {
            if (t.getPublicaciones().getIdPublicacion() == idPublicacion) {
                tags.add(t);
            }
        }
        pub.setTags(tags);

        // Obtener comentarios
        List<Comentario> comentarios = new ArrayList<>();
        for (Comentario c : comentarioDAO.listar()) {
            if (c.getPublicacion().getIdPublicacion() == idPublicacion) {
                comentarios.add(c);
            }
        }
        pub.setComentarios(comentarios);

        return pub;
    }

    // ===============================
    // LISTAR TODAS LAS PUBLICACIONES
    // ===============================

    public List<Publicaciones> listarPublicaciones() {
        return publicacionesDAO.listar();
    }

    // ===============================
    // LISTAR PUBLICACIONES POR AUTOR
    // ===============================

    public List<Publicaciones> listarPorAutor(int idAutor) {
        return publicacionesDAO.listar().stream()
                .filter(p -> p.getAutores().getIdAutor() == idAutor)
                .collect(Collectors.toList());
    }

    // ===============================
    // ELIMINAR PUBLICACIÓN COMPLETA
    // ===============================

    public boolean eliminarPublicacion(int idPublicacion) {
        return publicacionesDAO.eliminar(idPublicacion);
    }
}
