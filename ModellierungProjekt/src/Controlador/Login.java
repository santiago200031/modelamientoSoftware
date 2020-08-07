package Controlador;

import Vista.AfiliadoVentanaPrincipal;
import Vista.NaturalVentanaPrincipal;
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
    public static boolean ingresar(String cedula, String password) {
        if (esUsuarioVacio(cedula)) {
            JOptionPane.showMessageDialog(null, "El campo de Usuario se encuentra vacío.");
            return false;
        }
        if (esPasswordVacio(password)) {
            JOptionPane.showMessageDialog(null, "La Contraseña se encuentra vacía.");
            return false;
        }
        try {
            // Seleccionar usuario y contraseña
            String sql = "SELECT CED_USU, CONT_USU, ROL_USU FROM USUARIOS WHERE CED_USU = '" + cedula + "';";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (!cedula.equals(rs.getString("CED_USU"))) {
                JOptionPane.showMessageDialog(null, "El Usuario no existe o está incorrecto.");
                return false;
            }
            if (cedula.equals(rs.getString("CED_USU")) && password.equals(rs.getString("CONT_USU"))) {
                switch (rs.getString("ROL_USU")) {
                    case "NATURAL":
                        new NaturalVentanaPrincipal().setVisible(true);
                        break;
                    case "AFILIADO":
                        new AfiliadoVentanaPrincipal().setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "No se determinó el rol/tipo del usuario.");
                        return false;
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Clave incorrecta.");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "El Usuario no existe o está incorrecto.");
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
