package vista.usuario;

import controlador.ControladorUsuario;
import modelo.Telefono;
import modelo.Ubicacion;
import modelo.Usuarios;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroUsuarioView extends JPanel {

    private final ControladorUsuario controladorUsuario;

    private JTextField txtNombre;
    private JTextField txtAlias;
    private JPasswordField txtContrasenia;
    private JTextField txtCodigoUsuario;
    private JTextField txtPais;
    private JTextField txtCiudad;
    private JTextField txtCodigoPostal;
    private JTextArea txtTelefonos;
    private JTextField txtNombreAutor;

    public RegistroUsuarioView() {
        controladorUsuario = new ControladorUsuario();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        add(new JLabel("Registro de usuario", SwingConstants.CENTER), BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre = new JTextField();
        txtAlias = new JTextField();
        txtContrasenia = new JPasswordField();
        txtCodigoUsuario = new JTextField();
        txtPais = new JTextField();
        txtCiudad = new JTextField();
        txtCodigoPostal = new JTextField();
        txtTelefonos = new JTextArea(3, 20);
        txtNombreAutor = new JTextField();

        int fila = 0;
        addCampo(formulario, gbc, fila++, "Nombre", txtNombre);
        addCampo(formulario, gbc, fila++, "Alias", txtAlias);
        addCampo(formulario, gbc, fila++, "Contraseña", txtContrasenia);
        addCampo(formulario, gbc, fila++, "Código de usuario", txtCodigoUsuario);
        addCampo(formulario, gbc, fila++, "País", txtPais);
        addCampo(formulario, gbc, fila++, "Ciudad", txtCiudad);
        addCampo(formulario, gbc, fila++, "Código postal", txtCodigoPostal);

        gbc.gridx = 0;
        gbc.gridy = fila;
        formulario.add(new JLabel("Teléfonos (separados por coma)"), gbc);
        gbc.gridx = 1;
        formulario.add(new JScrollPane(txtTelefonos), gbc);
        fila++;

        addCampo(formulario, gbc, fila++, "Nombre de autor (opcional)", txtNombreAutor);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrar());

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        formulario.add(btnRegistrar, gbc);

        add(formulario, BorderLayout.CENTER);
    }

    private void addCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel(etiqueta + ":"), gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String alias = txtAlias.getText().trim();
        String contrasenia = new String(txtContrasenia.getPassword());
        String codigo = txtCodigoUsuario.getText().trim();

        if (nombre.isEmpty() || alias.isEmpty() || contrasenia.isEmpty() || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios.");
            return;
        }

        Usuarios usuario = new Usuarios(nombre, alias, contrasenia, codigo);

        Ubicacion ubicacion = new Ubicacion(
                txtPais.getText().trim(),
                txtCiudad.getText().trim(),
                txtCodigoPostal.getText().trim(),
                usuario
        );

        List<Telefono> telefonos = new ArrayList<>();
        String telefonosTexto = txtTelefonos.getText().trim();
        if (!telefonosTexto.isEmpty()) {
            String[] numeros = telefonosTexto.split(",");
            for (String numero : numeros) {
                String limpio = numero.trim();
                if (!limpio.isEmpty()) {
                    telefonos.add(new Telefono(0, limpio, usuario));
                }
            }
        }

        boolean registrado = controladorUsuario.registrarUsuario(usuario, telefonos, ubicacion);

        if (registrado) {
            if (!txtNombreAutor.getText().trim().isEmpty()) {
                controladorUsuario.convertirEnAutor(usuario, txtNombreAutor.getText().trim());
            }
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtAlias.setText("");
        txtContrasenia.setText("");
        txtCodigoUsuario.setText("");
        txtPais.setText("");
        txtCiudad.setText("");
        txtCodigoPostal.setText("");
        txtTelefonos.setText("");
        txtNombreAutor.setText("");
    }
}
