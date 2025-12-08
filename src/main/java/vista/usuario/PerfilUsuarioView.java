package vista.usuario;

import controlador.ControladorUsuario;
import modelo.Telefono;
import modelo.Ubicacion;
import modelo.Usuarios;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class PerfilUsuarioView extends JPanel {

    private final ControladorUsuario controladorUsuario;
    private Usuarios usuario;

    private JLabel lblNombre;
    private JLabel lblAlias;
    private JLabel lblCodigo;
    private JLabel lblAutor;
    private JTextArea txtTelefonos;
    private JTextArea txtUbicaciones;

    public PerfilUsuarioView(Usuarios usuario) {
        this.usuario = usuario;
        this.controladorUsuario = new ControladorUsuario();
        initUI();
        cargarDatos();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Mi perfil", SwingConstants.CENTER), BorderLayout.NORTH);

        JPanel datosPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        lblNombre = new JLabel();
        lblAlias = new JLabel();
        lblCodigo = new JLabel();
        lblAutor = new JLabel();

        datosPanel.add(lblNombre);
        datosPanel.add(lblAlias);
        datosPanel.add(lblCodigo);
        datosPanel.add(lblAutor);

        txtTelefonos = new JTextArea(4, 25);
        txtTelefonos.setEditable(false);
        txtUbicaciones = new JTextArea(4, 25);
        txtUbicaciones.setEditable(false);

        JPanel listadoPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listadoPanel.add(crearBloque("Teléfonos", txtTelefonos));
        listadoPanel.add(crearBloque("Ubicaciones", txtUbicaciones));

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarDatos());

        JButton btnConvertir = new JButton("Convertir en autor");
        btnConvertir.addActionListener(e -> convertirEnAutor());

        acciones.add(btnRefrescar);
        acciones.add(btnConvertir);

        add(datosPanel, BorderLayout.CENTER);
        add(listadoPanel, BorderLayout.SOUTH);
        add(acciones, BorderLayout.PAGE_END);
    }

    private JPanel crearBloque(String titulo, JTextArea area) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(titulo), BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    private void cargarDatos() {
        Usuarios usuarioCompleto = controladorUsuario.obtenerUsuarioCompleto(usuario.getId());
        if (usuarioCompleto != null) {
            this.usuario = usuarioCompleto;
        }

        lblNombre.setText("Nombre: " + usuario.getNombre());
        lblAlias.setText("Alias: " + usuario.getAlias());
        lblCodigo.setText("Código: " + usuario.getCodigoDeUsuario());
        lblAutor.setText("Autor: " + (usuario.getAutor() != null ? usuario.getAutor().getNombreAutor() : "No registrado"));

        txtTelefonos.setText(formatearTelefonos(usuario.getTelefonos()));
        txtUbicaciones.setText(formatearUbicaciones(usuario.getUbicaciones()));
    }

    private String formatearTelefonos(List<Telefono> telefonos) {
        if (telefonos == null || telefonos.isEmpty()) {
            return "Sin teléfonos";
        }
        return telefonos.stream()
                .map(t -> t.getNumero())
                .collect(Collectors.joining("\n"));
    }

    private String formatearUbicaciones(List<Ubicacion> ubicaciones) {
        if (ubicaciones == null || ubicaciones.isEmpty()) {
            return "Sin ubicaciones";
        }
        return ubicaciones.stream()
                .map(u -> u.getPais() + " - " + u.getCiudad() + " (" + u.getCodigoPostal() + ")")
                .collect(Collectors.joining("\n"));
    }

    private void convertirEnAutor() {
        if (usuario.getAutor() != null) {
            JOptionPane.showMessageDialog(this, "Ya eres autor registrado.");
            return;
        }

        String nombreAutor = JOptionPane.showInputDialog(this, "Nombre artístico o de autor:");
        if (nombreAutor == null || nombreAutor.isBlank()) {
            return;
        }

        controladorUsuario.convertirEnAutor(usuario, nombreAutor.trim());
        cargarDatos();
        JOptionPane.showMessageDialog(this, "El usuario ahora es autor.");
    }
}
