package vista.comentario;

import controlador.ControladorComentario;
import modelo.Comentario;
import modelo.Publicaciones;
import vista.componentes.TablaDefault;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaComentariosView extends JPanel {

    private final ControladorComentario controladorComentario;
    private final Publicaciones publicacion;

    private DefaultTableModel modeloTabla;

    public ListaComentariosView(Publicaciones publicacion) {
        this.publicacion = publicacion;
        this.controladorComentario = new ControladorComentario();
        initUI();
        cargarComentarios();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Comentarios de la publicación", SwingConstants.CENTER), BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new Object[]{"Usuario", "Comentario", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new TablaDefault(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    public void cargarComentarios() {
        modeloTabla.setRowCount(0);
        List<Comentario> comentarios = controladorComentario.listarComentariosDePublicacion(publicacion.getIdPublicacion());
        if (comentarios == null) {
            return;
        }

        for (Comentario c : comentarios) {
            String usuario = c.getUsuarioid() != null ? c.getUsuarioid().getAlias() : "";
            modeloTabla.addRow(new Object[]{usuario, c.getCuerpo(), c.getFecha()});
        }
    }
}
