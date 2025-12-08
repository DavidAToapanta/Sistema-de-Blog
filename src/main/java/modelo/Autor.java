package modelo;

import java.util.List;

public class Autor {
    private int idAutor;
    private String nombreAutor;

    private Usuarios usuarios;
    private List<Publicaciones> publicaciones;

    public Autor() {
    }

    public Autor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Autor(String nombreAutor, Usuarios usuarios) {
        this.nombreAutor = nombreAutor;
        this.usuarios = usuarios;
    }

    public Autor(String nombreAutor, Usuarios usuarios, List<Publicaciones> publicaciones) {
        this.nombreAutor = nombreAutor;
        this.usuarios = usuarios;
        this.publicaciones = publicaciones;
    }


    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public List<Publicaciones> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicaciones> publicaciones) {
        this.publicaciones = publicaciones;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "idAutor=" + idAutor +
                ", nombreAutor='" + nombreAutor + '\'' +
                '}';
    }
}
