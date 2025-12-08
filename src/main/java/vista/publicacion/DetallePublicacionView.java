package vista.publicacion;

import controlador.ControladorComentario;
import modelo.Comentario;
import modelo.Publicaciones;
import modelo.Tags;
import modelo.Usuarios;
import vista.comentario.AgregarComentarioView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class DetallePublicacionView extends JPanel {

    private final Publicaciones publicacion;
    private final Usuarios usuario;
    private final ControladorComentario controladorComentario;

    private DefaultTableModel modeloComentarios;

    public DetallePublicacionView(Publicaciones publicacion, Usuarios usuario) {
        this.publicacion = publicacion;
        this.usuario = usuario;
        this.controladorComentario = new ControladorComentario();
        initUI();
        cargarComentarios();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel(publicacion.getTitulo());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        JTextArea areaCuerpo = new JTextArea(publicacion.getCuerpo());
        areaCuerpo.setEditable(false);
        areaCuerpo.setLineWrap(true);
        areaCuerpo.setWrapStyleWord(true);

        String descripcion = publicacion.getDescripcion();
        String ubicacion = publicacion.getPaisVisitado() + " - " + publicacion.getCiudadvisitada();
        String tags = formatearTags(publicacion.getTags());
        String autor = publicacion.getAutores() != null ? publicacion.getAutores().getNombreAutor() : "";

        JPanel datos = new JPanel(new GridLayout(3, 1));
        datos.add(new JLabel("Descripción: " + descripcion));
        datos.add(new JLabel("Ubicación: " + ubicacion));
        datos.add(new JLabel("Autor: " + autor + " | Tags: " + tags));

        JPanel cuerpoPanel = new JPanel(new BorderLayout());
        cuerpoPanel.add(datos, BorderLayout.NORTH);
        cuerpoPanel.add(new JScrollPane(areaCuerpo), BorderLayout.CENTER);

        add(cuerpoPanel, BorderLayout.CENTER);

        modeloComentarios = new DefaultTableModel(new Object[]{"Usuario", "Comentario", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaComentarios = new JTable(modeloComentarios);

        JButton btnAgregarComentario = new JButton("Agregar comentario");
        btnAgregarComentario.addActionListener(e -> abrirFormularioComentario());

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(new JScrollPane(tablaComentarios), BorderLayout.CENTER);
        sur.add(btnAgregarComentario, BorderLayout.SOUTH);

        add(sur, BorderLayout.SOUTH);
    }

    private String formatearTags(List<Tags> tags) {
        if (tags == null || tags.isEmpty()) {
            return "Sin tags";
        }
        return tags.stream().map(Tags::getNombreTag).collect(Collectors.joining(", "));
    }

    private void cargarComentarios() {
        modeloComentarios.setRowCount(0);
        List<Comentario> comentarios = controladorComentario.listarComentariosDePublicacion(publicacion.getIdPublicacion());
        if (comentarios == null) {
            return;
        }

        for (Comentario comentario : comentarios) {
            String usuarioNombre = comentario.getUsuarioid() != null ? comentario.getUsuarioid().getAlias() : "";
            modeloComentarios.addRow(new Object[]{usuarioNombre, comentario.getCuerpo(), comentario.getFecha()});
        }
    }

    private void abrirFormularioComentario() {
        AgregarComentarioView formulario = new AgregarComentarioView(publicacion, usuario, this::cargarComentarios);
        JOptionPane.showMessageDialog(this, formulario, "Nuevo comentario", JOptionPane.PLAIN_MESSAGE);
    }
}