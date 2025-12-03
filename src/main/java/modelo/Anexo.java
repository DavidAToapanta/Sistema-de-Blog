package modelo;

import java.util.Arrays;

public class Anexo {
    private int idAnexo;
    private int comentarioidComeentario;
    private String nombreComentario;
    private byte[] extension;
    private double peso;

    public Anexo() {

    }

    public Anexo(int idAnexo, int comentarioidComeentario,  String nombreComentario, byte[] extension, double peso) {
        this.idAnexo = idAnexo;
        this.comentarioidComeentario = comentarioidComeentario;
        this.nombreComentario = nombreComentario;
        this.extension = extension;
        this.peso = peso;
    }

    public int getIdAnexo() {
        return idAnexo;
    }

    public void setIdAnexo(int idAnexo) {
        this.idAnexo = idAnexo;
    }

    public String getNombreComentario() {
        return nombreComentario;
    }

    public void setNombreComentario(String nombreComentario) {
        this.nombreComentario = nombreComentario;
    }

    public int getComentarioidComeentario() {
        return comentarioidComeentario;
    }

    public void setComentarioidComeentario(int comentarioidComeentario) {
        this.comentarioidComeentario = comentarioidComeentario;
    }

    public byte[] getExtension() {
        return extension;
    }

    public void setExtension(byte[] extension) {
        this.extension = extension;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "Anexo{" +
                "idAnexo=" + idAnexo +
                ", comentarioidComeentario=" + comentarioidComeentario +
                ", nombreComentario='" + nombreComentario + '\'' +
                ", extension=" + Arrays.toString(extension) +
                ", peso=" + peso +
                '}';
    }


}

