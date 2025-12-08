package vista.componentes;

import javax.swing.*;
import java.awt.*;

/**
 * Botón reutilizable con estilo sencillo para las vistas.
 */
public class BotonPrimario extends JButton {

    public BotonPrimario(String texto) {
        super(texto);
        setFocusPainted(false);
        setBackground(new Color(52, 152, 219));
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }
}
