package modelo;

import java.util.Arrays;

public class Anexo {

    private int idAnexo;
    private String nombreArchivo;
    private byte[] archivo;   // El BYTEA
    private double peso;

    private Comentario comentario; // Relación muchos a uno

    public Anexo() {
    }

    public Anexo(String nombreArchivo, byte[] archivo, double peso) {
        this.nombreArchivo = nombreArchivo;
        this.archivo = archivo;
        this.peso = peso;
    }

    public Anexo(String nombreArchivo, byte[] archivo, double peso, Comentario comentario) {
        this.nombreArchivo = nombreArchivo;
        this.archivo = archivo;
        this.peso = peso;
        this.comentario = comentario;
    }

    // Getters y setters
    public int getIdAnexo() {
        return idAnexo;
    }

    public void setIdAnexo(int idAnexo) {
        this.idAnexo = idAnexo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Anexo{" +
                "idAnexo=" + idAnexo +
                ", nombreArchivo='" + nombreArchivo + '\'' +
                ", archivo=" + Arrays.toString(archivo) +
                ", peso=" + peso +
                '}';
    }
}




