import dao.*;
import modelo.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class main {

    public static void main(String[] args) {

        // Instancias DAO
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        TelefonoDAO telefonoDAO = new TelefonoDAO();
        UbicacionDAO ubicacionDAO = new UbicacionDAO();
        AutorDAO autorDAO = new AutorDAO();
        PublicacionesDAO publicacionesDAO = new PublicacionesDAO();
        TagsDAO tagsDAO = new TagsDAO();
        ComentarioDAO comentarioDAO = new ComentarioDAO();
        AnexoDAO anexoDAO = new AnexoDAO();

        System.out.println("=== PRUEBA GLOBAL DEL SISTEMA ===");

        // ===============================
        // 1. CREAR USUARIO
        // ===============================
        Usuarios u = new Usuarios("Brandon", "Bran", "pass987", "USR010");
        usuariosDAO.insertar(u);
        System.out.println("Usuario creado: " + u);

        // ===============================
        // 2. ASIGNAR TELEFONOS
        // ===============================
        Telefono t1 = new Telefono(1, "0988776655", u);
        Telefono t2 = new Telefono(2, "022345678", u);

        telefonoDAO.insertar(t1);
        telefonoDAO.insertar(t2);

        System.out.println("Teléfonos añadidos.");

        // ===============================
        // 3. ASIGNAR UBICACION
        // ===============================
        Ubicacion ub = new Ubicacion("Colombia", "Medellín", "050021", u);
        ubicacionDAO.insertar(ub);

        System.out.println("Ubicación añadida.");

        // ===============================
        // 4. CREAR AUTOR PARA ESTE USUARIO
        // ===============================
        Autor autor = new Autor("Brandon Writer", u);
        autorDAO.insertar(autor);

        System.out.println("Autor creado: " + autor);

        // ===============================
        // 5. CREAR PUBLICACIÓN
        // ===============================
        Publicaciones pub = new Publicaciones(
                "Aventura en la Sierra Colombiana",
                "Explorando nuevas montañas",
                "Texto descriptivo de la travesía por la sierra...",
                "Colombia",
                "Medellín",
                autor,
                null,
                null
        );

        publicacionesDAO.insertar(pub);

        System.out.println("Publicación creada: " + pub);

        // ===============================
        // 6. CREAR TAGS PARA LA PUBLICACIÓN
        // ===============================
        Tags tag1 = new Tags("montañas", pub);
        Tags tag2 = new Tags("aventura_extrema", pub);

        tagsDAO.insertar(tag1);
        tagsDAO.insertar(tag2);

        System.out.println("Tags creados.");

        // ===============================
        // 7. AGREGAR COMENTARIO A LA PUBLICACIÓN
        // ===============================
        Comentario comentario = new Comentario(
                "Broo eso se ve épico 🔥🔥",
                new Date(),
                u,
                pub,
                null
        );

        comentarioDAO.insertar(comentario);

        System.out.println("Comentario añadido: " + comentario);

        // ===============================
        // 8. SUBIR UN ARCHIVO COMO ANEXO
        // ===============================
        try {
            byte[] archivoBytes = Files.readAllBytes(
                    Paths.get("C:/Users/boliv/OneDrive/Documentos/Programacion 1/imagenPrueba.png")
            );

            Anexo anexo = new Anexo(
                    "montana_epica.png",
                    archivoBytes,
                    archivoBytes.length,
                    comentario
            );

            anexoDAO.insertar(anexo);

            System.out.println("Anexo subido correctamente.");

        } catch (Exception e) {
            System.out.println("⚠ Error cargando archivo: " + e.getMessage());
        }

        // ===============================
        // 9. LISTAR TODO PARA VER QUE FUNCIONA
        // ===============================
        System.out.println("\n=== Usuarios ===");
        usuariosDAO.listar().forEach(System.out::println);

        System.out.println("\n=== Publicaciones ===");
        publicacionesDAO.listar().forEach(System.out::println);

        System.out.println("\n=== Comentarios ===");
        comentarioDAO.listar().forEach(System.out::println);

        System.out.println("\n=== Anexos ===");
        anexoDAO.listar().forEach(System.out::println);

        System.out.println("\nFINALIZADO.");
    }
}
