package modelo;

public class Tags {
    private int idTag;
    private String nombreTag;

    private Publicaciones publicaciones;

    public Tags() {
    }

    public Tags(String nombreTag) {
        this.nombreTag = nombreTag;
    }

    public Tags(String nombreTag, Publicaciones publicaciones) {
        this.nombreTag = nombreTag;
        this.publicaciones = publicaciones;
    }

    public int getIdTag() {
        return idTag;
    }

    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }

    public String getNombreTag() {
        return nombreTag;
    }

    public void setNombreTag(String nombreTag) {
        this.nombreTag = nombreTag;
    }

    public Publicaciones getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(Publicaciones publicaciones) {
        this.publicaciones = publicaciones;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "idTag=" + idTag +
                ", nombreTag='" + nombreTag + '\'' +
                '}';
    }
}
