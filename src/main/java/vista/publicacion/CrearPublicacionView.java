package vista.publicacion;

import controlador.ControladorPublicacion;
import controlador.ControladorUsuario;
import modelo.Autor;
import modelo.Publicaciones;
import modelo.Tags;
import modelo.Usuarios;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CrearPublicacionView extends JPanel {

    private final ControladorPublicacion controladorPublicacion;
    private final ControladorUsuario controladorUsuario;
    private final Usuarios usuario;

    private JTextField txtTitulo;
    private JTextField txtDescripcion;
    private JTextArea txtCuerpo;
    private JTextField txtPais;
    private JTextField txtCiudad;
    private JTextField txtTags;
    private JTextField txtNombreAutor;

    public CrearPublicacionView(Usuarios usuario) {
        this.usuario = usuario;
        this.controladorPublicacion = new ControladorPublicacion();
        this.controladorUsuario = new ControladorUsuario();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Crear publicación", SwingConstants.CENTER), BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        txtTitulo = new JTextField();
        txtDescripcion = new JTextField();
        txtCuerpo = new JTextArea(5, 20);
        txtPais = new JTextField();
        txtCiudad = new JTextField();
        txtTags = new JTextField();
        txtNombreAutor = new JTextField(usuario.getAutor() != null ? usuario.getAutor().getNombreAutor() : "");

        int fila = 0;
        addCampo(formulario, gbc, fila++, "Título", txtTitulo);
        addCampo(formulario, gbc, fila++, "Descripción", txtDescripcion);

        gbc.gridx = 0;
        gbc.gridy = fila;
        formulario.add(new JLabel("Cuerpo"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        formulario.add(new JScrollPane(txtCuerpo), gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fila++;

        addCampo(formulario, gbc, fila++, "País visitado", txtPais);
        addCampo(formulario, gbc, fila++, "Ciudad visitada", txtCiudad);
        addCampo(formulario, gbc, fila++, "Tags (separados por coma)", txtTags);
        addCampo(formulario, gbc, fila++, "Nombre de autor", txtNombreAutor);

        JButton btnGuardar = new JButton("Publicar");
        btnGuardar.addActionListener(e -> guardarPublicacion());

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        formulario.add(btnGuardar, gbc);

        add(formulario, BorderLayout.CENTER);
    }

    private void addCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel(etiqueta + ":"), gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private void guardarPublicacion() {
        String titulo = txtTitulo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String cuerpo = txtCuerpo.getText().trim();

        if (titulo.isEmpty() || descripcion.isEmpty() || cuerpo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios.");
            return;
        }

        Autor autor = usuario.getAutor();
        if (autor == null) {
            String nombreAutor = txtNombreAutor.getText().trim();
            if (nombreAutor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un nombre de autor para continuar.");
                return;
            }
            autor = controladorUsuario.convertirEnAutor(usuario, nombreAutor);
        }

        Publicaciones publicacion = new Publicaciones(
                titulo,
                descripcion,
                cuerpo,
                txtPais.getText().trim(),
                txtCiudad.getText().trim(),
                autor,
                null,
                null
        );

        List<Tags> tags = obtenerTags(publicacion);
        boolean creada = controladorPublicacion.crearPublicacion(publicacion, tags);

        if (creada) {
            JOptionPane.showMessageDialog(this, "Publicación creada correctamente.");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo crear la publicación.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Tags> obtenerTags(Publicaciones publicacion) {
        String textoTags = txtTags.getText().trim();
        if (textoTags.isEmpty()) {
            return new ArrayList<>();
        }

        return List.of(textoTags.split(","))
                .stream()
                .map(String::trim)
                .filter(t -> !t.isEmpty())
                .map(nombre -> new Tags(nombre, publicacion))
                .collect(Collectors.toList());
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtDescripcion.setText("");
        txtCuerpo.setText("");
        txtPais.setText("");
        txtCiudad.setText("");
        txtTags.setText("");
    }
}
