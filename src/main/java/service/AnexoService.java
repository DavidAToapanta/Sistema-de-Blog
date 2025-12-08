package service;

import dao.AnexoDAO;
import dao.ComentarioDAO;
import modelo.Anexo;
import modelo.Comentario;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AnexoService {

    private final AnexoDAO anexoDAO = new AnexoDAO();
    private final ComentarioDAO comentarioDAO = new ComentarioDAO();

    // Tamaño máximo: 5MB (puedes cambiarlo)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    // Extensiones permitidas
    private static final List<String> EXTENSIONES_VALIDAS = List.of("png", "jpg", "jpeg", "pdf");

    // ===============================
    // VALIDACIONES
    // ===============================

    private void validarArchivo(String ruta) throws Exception {
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            throw new IllegalArgumentException("El archivo no existe: " + ruta);
        }

        if (!archivo.isFile()) {
            throw new IllegalArgumentException("La ruta no es un archivo válido");
        }

        if (archivo.length() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("El archivo excede el tamaño máximo permitido (5MB)");
        }

        String extension = obtenerExtension(archivo.getName());
        if (!EXTENSIONES_VALIDAS.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException("Extensión no permitida: " + extension);
        }
    }

    private String obtenerExtension(String nombre) {
        int index = nombre.lastIndexOf(".");
        if (index == -1) return "";
        return nombre.substring(index + 1);
    }

    private String obtenerNombreArchivo(String ruta) {
        return new File(ruta).getName();
    }

    // ===============================
    // SUBIR ARCHIVO Y CREAR ANEXO
    // ===============================

    public boolean subirArchivo(String rutaArchivo, Comentario comentario) {
        try {
            validarArchivo(rutaArchivo);

            byte[] bytes = Files.readAllBytes(Paths.get(rutaArchivo));

            Anexo anexo = new Anexo();
            anexo.setArchivo(bytes);
            anexo.setNombreArchivo(obtenerNombreArchivo(rutaArchivo));
            anexo.setPeso(bytes.length);
            anexo.setComentario(comentario);

            return anexoDAO.insertar(anexo);

        } catch (Exception e) {
            System.out.println("Error al subir archivo: " + e.getMessage());
            return false;
        }
    }

    // ===============================
    // DESCARGAR ARCHIVO DE LA BD
    // ===============================

    public boolean descargarArchivo(int idAnexo, String carpetaDestino) {
        Anexo anexo = anexoDAO.buscarPorId(idAnexo);

        if (anexo == null) {
            throw new IllegalArgumentException("El anexo no existe");
        }

        try {
            File carpeta = new File(carpetaDestino);
            if (!carpeta.exists()) carpeta.mkdirs();

            String rutaSalida = carpetaDestino + "/" + anexo.getNombreArchivo();

            FileOutputStream fos = new FileOutputStream(rutaSalida);
            fos.write(anexo.getArchivo());
            fos.close();

            System.out.println("Archivo descargado correctamente en: " + rutaSalida);
            return true;

        } catch (Exception e) {
            System.out.println("Error al descargar archivo: " + e.getMessage());
            return false;
        }
    }

    // ===============================
    // LISTAR ANEXOS POR COMENTARIO
    // ===============================

    public List<Anexo> listarPorComentario(int idComentario) {
        return anexoDAO.listar().stream()
                .filter(a -> a.getComentario().getIdComentario() == idComentario)
                .toList();
    }

    // ===============================
    // ELIMINAR UN ANEXO
    // ===============================

    public boolean eliminar(int idAnexo) {
        return anexoDAO.eliminar(idAnexo);
    }

    // ===============================
    // OBTENER UN ANEXO COMPLETO
    // ===============================

    public Anexo obtenerAnexo(int idAnexo) {
        return anexoDAO.buscarPorId(idAnexo);
    }
}
