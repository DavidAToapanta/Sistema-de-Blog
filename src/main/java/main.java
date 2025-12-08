import modelo.Usuarios;
import service.UsuarioService;

public class main {

    public static void main(String[] args) {

        UsuarioService us = new UsuarioService();

        Usuarios u = new Usuarios(
                "Samira",
                "cham123",
                "12345",
                "USR008"
        );

        // IMPORTANTE: guardar el resultado
        boolean ok = us.registrarUsuarioCompleto(u, null, null);

        if (ok) {
            System.out.println("Usuario creado con ID: " + u.getId());
        } else {
            System.out.println("Error creando usuario");
        }
    }
}
