package vista.utils;

import javax.swing.*;
import java.net.URL;

public class Iconos {

    private Iconos() {
    }

    public static Icon cargar(String rutaRecurso) {
        URL url = Iconos.class.getResource(rutaRecurso);
        return url != null ? new ImageIcon(url) : null;
    }
}
