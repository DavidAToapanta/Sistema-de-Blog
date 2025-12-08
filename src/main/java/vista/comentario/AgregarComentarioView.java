package vista.comentario;

import controlador.ControladorComentario;
import modelo.Comentario;
import modelo.Publicaciones;
import modelo.Usuarios;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class AgregarComentarioView extends JPanel {

    private final ControladorComentario controladorComentario;
    private final Publicaciones publicacion;
    private final Usuarios usuario;
    private final Runnable onComentarioCreado;

    private JTextArea txtComentario;

    public AgregarComentarioView(Publicaciones publicacion, Usuarios usuario, Runnable onComentarioCreado) {
        this.publicacion = publicacion;
        this.usuario = usuario;
        this.onComentarioCreado = onComentarioCreado;
        this.controladorComentario = new ControladorComentario();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(5, 5));
        add(new JLabel("Nuevo comentario"), BorderLayout.NORTH);

        txtComentario = new JTextArea(4, 20);
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        add(new JScrollPane(txtComentario), BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        add(btnGuardar, BorderLayout.SOUTH);
    }

    private void guardar() {
        String texto = txtComentario.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un comentario.");
            return;
        }

        Comentario comentario = new Comentario(texto, new Date(), usuario, publicacion, null);
        boolean creado = controladorComentario.crearComentario(comentario);
        if (creado) {
            JOptionPane.showMessageDialog(this, "Comentario agregado.");
            txtComentario.setText("");
            if (onComentarioCreado != null) {
                onComentarioCreado.run();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el comentario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
