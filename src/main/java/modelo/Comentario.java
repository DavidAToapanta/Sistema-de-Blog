package modelo;

import java.util.Date;
import java.util.List;

public class Comentario {
    private int idComentario;
    private String cuerpo;
    private Date fecha;

    private Usuarios usuarioid;
    private Publicaciones publicacion;
    private List<Anexo>  anexos;

    public Comentario() {
    }

    public Comentario(String cuerpo, Date fecha) {
        this.cuerpo = cuerpo;
        this.fecha = fecha;
    }

    public Comentario(String cuerpo, Date fecha,  Usuarios usuarioid, Publicaciones publicacion, List<Anexo> anexos) {
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.usuarioid = usuarioid;
        this.publicacion = publicacion;
        this.anexos = anexos;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuarios getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuarios usuarioid) {
        this.usuarioid = usuarioid;
    }

    public Publicaciones getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicaciones publicacion) {
        this.publicacion = publicacion;
    }

    public List<Anexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexo> anexos) {
        this.anexos = anexos;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "idComentario=" + idComentario +
                ", cuerpo='" + cuerpo + '\'' +
                ", fecha=" + fecha +
                ", usuarioid=" + usuarioid +
                ", publicacion=" + publicacion +
                ", anexos=" + anexos +
                '}';
    }
}
