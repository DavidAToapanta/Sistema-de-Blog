package controlador;

import modelo.Telefono;
import modelo.Ubicacion;
import modelo.Usuarios;
import modelo.Autor;
import service.UsuarioService;

import java.util.List;

public class ControladorUsuario {

    private final UsuarioService usuarioService = new UsuarioService();


    public Usuarios login(String alias, String contrasenia) {

        try {
            Usuarios usuario = usuarioService.login(alias, contrasenia);

            if (usuario == null) {
                System.out.println("Credenciales incorrectas");
                return null;
            }

            return usuario;

        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
            return null;
        }
    }


    public boolean registrarUsuario(Usuarios usuario,
                                    List<Telefono> telefonos,
                                    Ubicacion ubicacion) {
        try {
            return usuarioService.registrarUsuarioCompleto(usuario, telefonos, ubicacion);
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }


    public Autor convertirEnAutor(Usuarios usuario, String nombreAutor) {
        try {
            return usuarioService.convertirEnAutor(usuario, nombreAutor);
        } catch (Exception e) {
            System.out.println("Error al convertir en autor: " + e.getMessage());
            return null;
        }
    }


    public Usuarios obtenerUsuarioCompleto(int id) {
        try {
            return usuarioService.obtenerUsuarioCompletoPorId(id);
        } catch (Exception e) {
            System.out.println("Error al obtener usuario completo: " + e.getMessage());
            return null;
        }
    }


    public List<Usuarios> listarUsuarios() {
        try {
            return usuarioService.listarUsuarios();
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
            return null;
        }
    }
}
