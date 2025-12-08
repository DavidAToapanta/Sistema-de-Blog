package service;

import dao.AutorDAO;
import dao.TelefonoDAO;
import dao.UbicacionDAO;
import dao.UsuariosDAO;
import modelo.Autor;
import modelo.Telefono;
import modelo.Ubicacion;
import modelo.Usuarios;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
    private final TelefonoDAO telefonoDAO = new TelefonoDAO();
    private final UbicacionDAO ubicacionDAO = new UbicacionDAO();
    private final AutorDAO autorDAO = new AutorDAO();

    // ===============================
    // VALIDACIONES BÁSICAS
    // ===============================

    public boolean aliasDisponible(String alias) {
        return usuariosDAO.listar().stream()
                .noneMatch(u -> u.getAlias().equalsIgnoreCase(alias));
    }

    public boolean codigoDisponible(String codigo) {
        return usuariosDAO.listar().stream()
                .noneMatch(u -> u.getCodigoDeUsuario().equalsIgnoreCase(codigo));
    }

    private Usuarios buscarPorAlias(String alias) {
        return usuariosDAO.listar().stream()
                .filter(u -> u.getAlias().equalsIgnoreCase(alias))
                .findFirst()
                .orElse(null);
    }

    private Autor buscarAutorPorUsuarioId(int usuarioId) {
        return autorDAO.listar().stream()
                .filter(a -> a.getUsuarios() != null && a.getUsuarios().getId() == usuarioId)
                .findFirst()
                .orElse(null);
    }

    // ===============================
    // REGISTRO COMPLETO
    // ===============================

    public boolean registrarUsuarioCompleto(Usuarios usuario,
                                            List<Telefono> telefonos,
                                            Ubicacion ubicacion) {

        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        if (usuario.getAlias() == null || usuario.getAlias().isBlank()) {
            throw new IllegalArgumentException("El alias es obligatorio");
        }
        if (usuario.getContrasenia() == null || usuario.getContrasenia().length() < 4) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres");
        }
        if (!aliasDisponible(usuario.getAlias())) {
            throw new IllegalArgumentException("El alias ya está en uso");
        }
        if (!codigoDisponible(usuario.getCodigoDeUsuario())) {
            throw new IllegalArgumentException("El código de usuario ya está en uso");
        }

        // Hashear contraseña con bcrypt
        String hashed = BCrypt.hashpw(usuario.getContrasenia(), BCrypt.gensalt());
        usuario.setContrasenia(hashed);

        // 1. Insertar usuario
        boolean insertado = usuariosDAO.insertar(usuario);
        if (!insertado) {
            return false;
        }

        // 2. Insertar teléfonos
        if (telefonos != null) {
            for (Telefono t : telefonos) {
                t.setUsuarios(usuario);
                telefonoDAO.insertar(t);
            }
        }

        // 3. Insertar ubicación
        if (ubicacion != null) {
            ubicacion.setUsuario(usuario);
            ubicacionDAO.insertar(ubicacion);
        }

        return true;
    }

    // ===============================
    // LOGIN
    // ===============================

    public Usuarios login(String alias, String contraseniaPlano) {
        Usuarios usuario = buscarPorAlias(alias);

        if (usuario == null) {
            // alias no existe
            return null;
        }

        String hashGuardado = usuario.getContrasenia();

        if (!BCrypt.checkpw(contraseniaPlano, hashGuardado)) {
            // contraseña incorrecta
            return null;
        }

        // Si las credenciales son correctas, devolvemos el usuario COMPLETO
        return obtenerUsuarioCompletoPorId(usuario.getId());
    }

    // ===============================
    // CONVERTIR USUARIO EN AUTOR
    // ===============================

    public Autor convertirEnAutor(Usuarios usuario, String nombreAutor) {
        if (usuario == null || usuario.getId() == 0) {
            throw new IllegalArgumentException("El usuario debe existir en la base de datos");
        }

        Autor existente = buscarAutorPorUsuarioId(usuario.getId());
        if (existente != null) {
            throw new IllegalStateException("Este usuario ya es autor");
        }

        if (nombreAutor == null || nombreAutor.isBlank()) {
            nombreAutor = usuario.getNombre(); // fallback
        }

        Autor autor = new Autor(nombreAutor, usuario);
        boolean ok = autorDAO.insertar(autor);
        if (!ok) {
            throw new RuntimeException("No se pudo crear el autor");
        }

        // También lo asociamos en el objeto Usuarios
        usuario.setAutor(autor);

        return autor;
    }

    // ===============================
    // OBTENER USUARIO COMPLETO
    // ===============================

    public Usuarios obtenerUsuarioCompletoPorId(int id) {
        Usuarios usuario = usuariosDAO.buscarPorId(id);
        if (usuario == null) {
            return null;
        }

        // Telefonos del usuario
        List<Telefono> telefonosUsuario = new ArrayList<>();
        for (Telefono t : telefonoDAO.listar()) {
            if (t.getUsuarios() != null && t.getUsuarios().getId() == id) {
                telefonosUsuario.add(t);
            }
        }
        usuario.setTelefonos(telefonosUsuario);

        // Ubicaciones del usuario
        List<Ubicacion> ubicacionesUsuario = new ArrayList<>();
        for (Ubicacion u : ubicacionDAO.listar()) {
            if (u.getUsuario() != null && u.getUsuario().getId() == id) {
                ubicacionesUsuario.add(u);
            }
        }
        usuario.setUbicaciones(ubicacionesUsuario);

        // Autor (si existe)
        Autor autor = buscarAutorPorUsuarioId(id);
        usuario.setAutor(autor);

        return usuario;
    }

    // ===============================
    // LISTAR TODOS (CON DATOS BÁSICOS)
    // ===============================

    public List<Usuarios> listarUsuarios() {
        return usuariosDAO.listar();
    }
}
