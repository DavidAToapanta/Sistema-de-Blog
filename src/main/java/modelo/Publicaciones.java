package modelo;

import java.util.List;

public class Publicaciones {
    private int idPublicacion;
    private String titulo;
    private String descripcion;
    private String cuerpo;
    private String paisVisitado;
    private String ciudadvisitada;

    private Autor  autores;
    private List<Tags> tags;
    private List<Comentario> comentarios;

    public Publicaciones() {
    }

    public Publicaciones(String titulo, String descripcion, String cuerpo, String paisVisitado, String ciudadvisitada) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cuerpo = cuerpo;
        this.paisVisitado = paisVisitado;
        this.ciudadvisitada = ciudadvisitada;
    }

    public Publicaciones(String titulo, String descripcion, String cuerpo, String paisVisitado, String ciudadvisitada, Autor autores,  List<Tags> tags,  List<Comentario> comentarios) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cuerpo = cuerpo;
        this.paisVisitado = paisVisitado;
        this.ciudadvisitada = ciudadvisitada;
        this.autores = autores;
        this.tags = tags;
        this.comentarios = comentarios;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Publicaciones(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getPaisVisitado() {
        return paisVisitado;
    }

    public void setPaisVisitado(String paisVisitado) {
        this.paisVisitado = paisVisitado;
    }

    public String getCiudadvisitada() {
        return ciudadvisitada;
    }

    public void setCiudadvisitada(String ciudadvisitada) {
        this.ciudadvisitada = ciudadvisitada;
    }

    public Autor getAutores() {
        return autores;
    }

    public void setAutores(Autor autores) {
        this.autores = autores;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Publicaciones{" +
                "idPublicacion=" + idPublicacion +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cuerpo='" + cuerpo + '\'' +
                ", paisVisitado='" + paisVisitado + '\'' +
                ", ciudadvisitada='" + ciudadvisitada + '\'' +
                '}';
    }
}
