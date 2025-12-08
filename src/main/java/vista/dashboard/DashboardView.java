package vista.dashboard;

import modelo.Usuarios;
import vista.publicacion.CrearPublicacionView;
import vista.publicacion.ListaPublicacionesView;
import vista.usuario.PerfilUsuarioView;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {

    private Usuarios usuarioLogueado;

    private JPanel panelMenu;
    private JPanel panelContenido;

    public DashboardView(Usuarios usuario) {
        this.usuarioLogueado = usuario;

        setTitle("Dashboard - Sistema de Blog");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ===== PANEL MENÚ LATERAL =====
        panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1, 5, 5));
        panelMenu.setPreferredSize(new Dimension(200, 600));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnCrearPublicacion = new JButton("Crear Publicación");
        JButton btnVerPublicaciones = new JButton("Ver Publicaciones");
        JButton btnPerfil = new JButton("Mi Perfil");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        // Eventos
        btnCrearPublicacion.addActionListener(e -> cargarVista(new CrearPublicacionView(usuarioLogueado)));
        btnVerPublicaciones.addActionListener(e -> cargarVista(new ListaPublicacionesView(usuarioLogueado)));
        btnPerfil.addActionListener(e -> cargarVista(new PerfilUsuarioView(usuarioLogueado)));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        panelMenu.add(btnCrearPublicacion);
        panelMenu.add(btnVerPublicaciones);
        panelMenu.add(btnPerfil);
        panelMenu.add(btnCerrarSesion);

        // ===== PANEL CONTENIDO (CENTRAL) =====
        panelContenido = new JPanel();
        panelContenido.setLayout(new BorderLayout());

        JLabel labelBienvenida = new JLabel("Bienvenido, " + usuarioLogueado.getNombre(), SwingConstants.CENTER);
        labelBienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        panelContenido.add(labelBienvenida, BorderLayout.CENTER);

        // Añadir al frame
        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);
    }

    private void cargarVista(JPanel vista) {
        panelContenido.removeAll();
        panelContenido.add(vista, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas cerrar sesión?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            dispose(); // cierra dashboard
            vista.usuario.LoginView.main(null); // vuelve al login
        }
    }
}
