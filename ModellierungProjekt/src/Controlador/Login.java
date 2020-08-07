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
            String sql = "SELECT USUARIO, CONTRASENIA FROM USUARIOS WHERE USUARIO = '" + usuario + "';";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            rs.next();
            if (!usuario.equals(rs.getString("USUARIO"))) {
                JOptionPane.showMessageDialog(null, "El Usuario no existe o está incorrecto.");
                return false;
            }
            if (usuario.equals(rs.getString("USUARIO")) && password.equals(rs.getString("CONTRASENIA"))) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Clave incorrecta.");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "El Usuario no existe o está incorrecto.");
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
