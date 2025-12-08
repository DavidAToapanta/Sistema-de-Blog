package modelo;

public class Ubicacion {
    private   int idUbicacion;
    private String pais;
    private String ciudad;
    private String codigoPostal;

    private Usuarios usuario;

    public Ubicacion() {
    }

    public Ubicacion(String pais, String ciudad, String codigoPostal) {
        this.pais = pais;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    public Ubicacion(String pais, String ciudad, String codigoPostal, Usuarios usuario) {
        this.pais = pais;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.usuario = usuario;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "idUbicacion=" + idUbicacion +
                ", pais='" + pais + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                '}';
    }
}
