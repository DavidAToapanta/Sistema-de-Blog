package vista.anexo;

import controlador.ControladorAnexo;
import modelo.Anexo;
import modelo.Comentario;
import vista.componentes.TablaDefault;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaAnexosView extends JPanel {

    private final ControladorAnexo controladorAnexo;
    private final Comentario comentario;

    private DefaultTableModel modeloTabla;

    public ListaAnexosView(Comentario comentario) {
        this.comentario = comentario;
        this.controladorAnexo = new ControladorAnexo();
        initUI();
        cargarAnexos();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JLabel("Anexos del comentario", SwingConstants.CENTER), BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Tamaño"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new TablaDefault(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnDescargar = new JButton("Descargar");
        btnDescargar.addActionListener(e -> descargarSeleccion(tabla));
        acciones.add(btnDescargar);
        add(acciones, BorderLayout.SOUTH);
    }

    private void cargarAnexos() {
        modeloTabla.setRowCount(0);
        List<Anexo> anexos = controladorAnexo.listarPorComentario(comentario.getIdComentario());
        if (anexos == null) {
            return;
        }

        for (Anexo anexo : anexos) {
            modeloTabla.addRow(new Object[]{anexo.getIdAnexo(), anexo.getNombreArchivo(), anexo.getPeso()});
        }
    }

    private void descargarSeleccion(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un anexo.");
            return;
        }

        int idAnexo = (int) modeloTabla.getValueAt(fila, 0);
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int opcion = chooser.showSaveDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            boolean descargado = controladorAnexo.descargarArchivo(idAnexo, chooser.getSelectedFile().getAbsolutePath());
            if (descargado) {
                JOptionPane.showMessageDialog(this, "Archivo descargado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo descargar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
