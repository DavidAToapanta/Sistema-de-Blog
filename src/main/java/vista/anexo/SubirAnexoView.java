package vista.anexo;

import controlador.ControladorAnexo;
import modelo.Comentario;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SubirAnexoView extends JPanel {

    private final ControladorAnexo controladorAnexo;
    private final Comentario comentario;
    private final Runnable onAnexoSubido;

    private JTextField txtRuta;

    public SubirAnexoView(Comentario comentario, Runnable onAnexoSubido) {
        this.comentario = comentario;
        this.onAnexoSubido = onAnexoSubido;
        this.controladorAnexo = new ControladorAnexo();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(5, 5));
        add(new JLabel("Adjuntar archivo"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(5, 5));
        txtRuta = new JTextField();
        JButton btnBuscar = new JButton("Seleccionar archivo");
        btnBuscar.addActionListener(e -> seleccionarArchivo());

        centro.add(txtRuta, BorderLayout.CENTER);
        centro.add(btnBuscar, BorderLayout.EAST);
        add(centro, BorderLayout.CENTER);

        JButton btnSubir = new JButton("Subir");
        btnSubir.addActionListener(e -> subir());
        add(btnSubir, BorderLayout.SOUTH);
    }

    private void seleccionarArchivo() {
        JFileChooser chooser = new JFileChooser();
        int opcion = chooser.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            txtRuta.setText(archivo.getAbsolutePath());
        }
    }

    private void subir() {
        if (txtRuta.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un archivo.");
            return;
        }

        boolean subido = controladorAnexo.subirArchivo(txtRuta.getText().trim(), comentario);
        if (subido) {
            JOptionPane.showMessageDialog(this, "Archivo subido correctamente.");
            if (onAnexoSubido != null) {
                onAnexoSubido.run();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo subir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}