package modelo;

public class Telefono{
    private int idTelefono;
    private int tipo;
    private String numero;

    private Usuarios usuarios;

    public Telefono() {
    }

    public Telefono(int tipo, String numero) {
        this.tipo = tipo;
        this.numero = numero;
    }

    public Telefono(int tipo, String numero, Usuarios usuarios) {
        this.tipo = tipo;
        this.numero = numero;
        this.usuarios = usuarios;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        return "Telefono{" +
                "idTelefono=" + idTelefono +
                ", tipo=" + tipo +
                ", numero='" + numero + '\'' +
                '}';
    }
}
