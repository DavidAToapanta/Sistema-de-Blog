package controlador;

import modelo.Anexo;
import modelo.Comentario;
import service.AnexoService;

import java.util.List;

public class ControladorAnexo {

    private final AnexoService anexoService = new AnexoService();


    public boolean subirArchivo(String rutaArchivo, Comentario comentario) {
        try {
            return anexoService.subirArchivo(rutaArchivo, comentario);
        } catch (Exception e) {
            System.out.println("Error al subir archivo: " + e.getMessage());
            return false;
        }
    }


    public boolean descargarArchivo(int idAnexo, String carpetaDestino) {
        try {
            return anexoService.descargarArchivo(idAnexo, carpetaDestino);
        } catch (Exception e) {
            System.out.println("Error al descargar archivo: " + e.getMessage());
            return false;
        }
    }


    public Anexo obtenerAnexo(int idAnexo) {
        try {
            return anexoService.obtenerAnexo(idAnexo);
        } catch (Exception e) {
            System.out.println("Error al obtener anexo: " + e.getMessage());
            return null;
        }
    }

    public List<Anexo> listarPorComentario(int idComentario) {
        try {
            return anexoService.listarPorComentario(idComentario);
        } catch (Exception e) {
            System.out.println("Error al listar anexos del comentario: " + e.getMessage());
            return null;
        }
    }

    public boolean eliminarAnexo(int idAnexo) {
        try {
            return anexoService.eliminar(idAnexo);
        } catch (Exception e) {
            System.out.println("Error al eliminar anexo: " + e.getMessage());
            return false;
        }
    }
}
