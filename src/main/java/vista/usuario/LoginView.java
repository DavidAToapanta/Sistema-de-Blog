package vista.usuario;

import controlador.ControladorUsuario;
import modelo.Usuarios;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtAlias;
    private JPasswordField txtContrasenia;
    private JButton btnLogin;

    private final ControladorUsuario controladorUsuario;

    public LoginView() {

        controladorUsuario = new ControladorUsuario();

        setTitle("Iniciar Sesión - Sistema de Blog");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initUI();
    }

    private void initUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblAlias = new JLabel("Alias:");
        JLabel lblContrasenia = new JLabel("Contraseña:");

        txtAlias = new JTextField();
        txtContrasenia = new JPasswordField();

        btnLogin = new JButton("Iniciar Sesión");

        btnLogin.addActionListener(e -> login());

        panel.add(lblAlias);
        panel.add(txtAlias);

        panel.add(lblContrasenia);
        panel.add(txtContrasenia);

        panel.add(new JLabel(""));
        panel.add(btnLogin);

        add(panel);
    }

    private void login() {
        String alias = txtAlias.getText();
        String contrasenia = new String(txtContrasenia.getPassword());

        if (alias.isBlank() || contrasenia.isBlank()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        Usuarios usuario = controladorUsuario.login(alias, contrasenia);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Alias o contraseña incorrectos.");
        } else {
            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getNombre());

            // ✨ Aquí abrirás Dashboard (lo haremos después)
            // new DashboardView(usuario).setVisible(true);
            // dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
