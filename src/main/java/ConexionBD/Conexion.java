package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_blog";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    private Conexion() {}

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (Exception e) {
            System.out.println("Error al conectar" + e.getMessage());
            return null;
        }
    }
}

