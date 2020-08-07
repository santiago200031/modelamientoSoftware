package Controlador;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Santiago Villavicencio villavicencioandrs@gmail.com
 */
public class Login {

    public static Connection conn = ConnectionDB.getConnection();
    static PreparedStatement ps;

    // Ingresar
    public static boolean ingresar(String usuario, String password) {
        if (esUsuarioVacio(usuario)) {
            JOptionPane.showMessageDialog(null, "El campo de Usuario se encuentra vacío.");
            return false;
        }
        if (esPasswordVacio(password)) {
            JOptionPane.showMessageDialog(null, "La Contraseña se encuentra vacía.");
            return false;
        }

        try {
            // Seleccionar usuario y contraseña
            String sqlUsername = "SELECT USERNAME FROM --- WHERE USU = ;";
            ps = conn.prepareStatement(sqlUsername);
            ResultSet rs = ps.executeQuery();
            if (!usuario.equals(rs.getString("USUARIO"))) {
                JOptionPane.showMessageDialog(null, "El Usuario no existe o está incorrecto.");
                return false;
            }
            String sqlPassword = "SELECT CONTRASENA FROM --- WHERE USU = ;";
            ps = conn.prepareStatement(sqlPassword);
            if (!password.equals(rs.getString("CONTRASENIA"))) {
                JOptionPane.showMessageDialog(null, "Clave incorrecta.");
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    private static boolean esUsuarioVacio(String usuario) {
        return usuario.equals("");
    }

    private static boolean esPasswordVacio(String password) {
        return password.equals("");
    }
}
