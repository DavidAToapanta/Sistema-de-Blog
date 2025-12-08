package controlador;

import modelo.Autor;
import modelo.Publicaciones;
import modelo.Tags;
import service.PublicacionService;

import java.util.List;

public class ControladorPublicacion {

    private final PublicacionService publicacionService = new PublicacionService();

    public boolean crearPublicacion(Publicaciones publicacion, List<Tags> tags) {
        try {
            return publicacionService.crearPublicacionCompleta(publicacion, tags);
        } catch (Exception e) {
            System.out.println("Error al crear publicación: " + e.getMessage());
            return false;
        }
    }


    public Publicaciones obtenerPublicacionCompleta(int idPublicacion) {
        try {
            return publicacionService.obtenerPublicacionCompleta(idPublicacion);
        } catch (Exception e) {
            System.out.println("Error al obtener publicación completa: " + e.getMessage());
            return null;
        }
    }


    public List<Publicaciones> listarPublicaciones() {
        try {
            return publicacionService.listarPublicaciones();
        } catch (Exception e) {
            System.out.println("Error al listar publicaciones: " + e.getMessage());
            return null;
        }
    }


    public List<Publicaciones> listarPorAutor(Autor autor) {
        try {
            return publicacionService.listarPorAutor(autor.getIdAutor());
        } catch (Exception e) {
            System.out.println("Error al listar publicaciones del autor: " + e.getMessage());
            return null;
        }
    }


    public boolean eliminarPublicacion(int idPublicacion) {
        try {
            return publicacionService.eliminarPublicacion(idPublicacion);
        } catch (Exception e) {
            System.out.println("Error al eliminar publicación: " + e.getMessage());
            return false;
        }
    }
}
