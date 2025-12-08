package vista.componentes;

import javax.swing.*;
import java.awt.*;

/**
 * Panel simple para mostrar títulos en las vistas.
 */
public class PanelTitulo extends JPanel {

    public PanelTitulo(String texto) {
        setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel(texto);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitulo, BorderLayout.CENTER);
    }
}
