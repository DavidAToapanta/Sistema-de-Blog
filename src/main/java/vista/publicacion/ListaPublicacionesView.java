package vista.publicacion;

import controlador.ControladorPublicacion;
import modelo.Publicaciones;
import modelo.Usuarios;
import vista.componentes.TablaDefault;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaPublicacionesView extends JPanel {

    private final ControladorPublicacion controladorPublicacion;
    private final Usuarios usuario;

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JPanel panelDetalle;

    public ListaPublicacionesView(Usuarios usuario) {
        this.usuario = usuario;
        this.controladorPublicacion = new ControladorPublicacion();
        initUI();
        cargarPublicaciones();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Listado de publicaciones", SwingConstants.CENTER), BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Título", "Descripción", "Autor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new TablaDefault(modeloTabla);
        tabla.getSelectionModel().addListSelectionListener(e -> mostrarDetalleSeleccionado());

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRefrescar = new JButton("Actualizar");
        btnRefrescar.addActionListener(e -> cargarPublicaciones());
        acciones.add(btnRefrescar);

        panelDetalle = new JPanel(new BorderLayout());
        panelDetalle.add(new JLabel("Selecciona una publicación para ver detalles", SwingConstants.CENTER), BorderLayout.CENTER);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelDetalle, BorderLayout.SOUTH);
        add(acciones, BorderLayout.PAGE_END);
    }

    private void cargarPublicaciones() {
        modeloTabla.setRowCount(0);
        List<Publicaciones> publicaciones = controladorPublicacion.listarPublicaciones();
        if (publicaciones == null) {
            return;
        }

        for (Publicaciones pub : publicaciones) {
            String autor = pub.getAutores() != null ? pub.getAutores().getNombreAutor() : "";
            modeloTabla.addRow(new Object[]{pub.getIdPublicacion(), pub.getTitulo(), pub.getDescripcion(), autor});
        }
    }

    private void mostrarDetalleSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        Publicaciones completa = controladorPublicacion.obtenerPublicacionCompleta(id);
        if (completa == null) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la publicación seleccionada.");
            return;
        }

        panelDetalle.removeAll();
        panelDetalle.add(new DetallePublicacionView(completa, usuario), BorderLayout.CENTER);
        panelDetalle.revalidate();
        panelDetalle.repaint();
    }
}
