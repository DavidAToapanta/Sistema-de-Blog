package modelo;

import java.util.List;

public class Usuarios {

    private int id;
    private String nombre;
    private String alias;
    private String contrasenia;
    private String codigoDeUsuario;

    // Relaciones
    private List<Ubicacion> ubicaciones;
    private List<Telefono> telefonos;
    private List<Comentario> comentarios;
    private Autor autor;

    public Usuarios() {}

    public Usuarios(String nombre, String alias, String contrasenia, String codigoDeUsuario) {
        this.nombre = nombre;
        this.alias = alias;
        this.contrasenia = contrasenia;
        this.codigoDeUsuario = codigoDeUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { // <-- NECESARIO
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCodigoDeUsuario() {
        return codigoDeUsuario;
    }

    public void setCodigoDeUsuario(String codigoDeUsuario) {
        this.codigoDeUsuario = codigoDeUsuario;
    }

    public List<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<Ubicacion> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", alias='" + alias + '\'' +
                ", codigoDeUsuario='" + codigoDeUsuario + '\'' +
                '}';
    }
}
