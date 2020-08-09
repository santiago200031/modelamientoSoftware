package Controlador;

import Vista.UsuarioAfiliado.AfiliadoVentanaPrincipal;
import Vista.UsuarioNatural.NaturalVentanaPrincipal;
import Vista.CrearUsuario;
import Vista.InterfazDatosAfiliado;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Santiago Villavicencio villavicencioandrs@gmail.com
 * @author Marlon Maisanche
 * @author Monserrath Núñez
 * @author Dayana Puetate
 */
public class Login {

    private static final Connection conn = ConnectionDB.getConnection();
    private static PreparedStatement ps;

    public static boolean ingresarSistema(String cedula, String password) {
        if (esUsuarioVacio(cedula)) {
            JOptionPane.showMessageDialog(null, "El campo de Usuario se encuentra vacío.");
            return false;
        }
        if (esPasswordVacio(password)) {
            JOptionPane.showMessageDialog(null, "La Contraseña se encuentra vacía.");
            return false;
        }
        try {
            // Seleccionar usuario, contraseña y tipo de usuario
            String sql = "SELECT CED_USU, CONT_USU, ROL_USU FROM USUARIOS WHERE CED_USU = '" + cedula + "';";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (!cedula.equals(rs.getString("CED_USU"))) {
                JOptionPane.showMessageDialog(null, "El Usuario no existe o está incorrecto.");
                return false;
            }
            password = getSHA1(password);
            if (cedula.equals(rs.getString("CED_USU")) && password.equals(rs.getString("CONT_USU"))) {
                switch (rs.getString("ROL_USU")) {
                    case "NATURAL":
                        Controlador.Natural.NaturalVentanaPrincipal.USUARIO_NATURAL = cedula;
                        new NaturalVentanaPrincipal().setVisible(true);
                        break;
                    case "AFILIADO":
                        
                        InterfazDatosAfiliado afiliado = new InterfazDatosAfiliado();
                        afiliado.
                        afiliado.show();
                        /*
                        Controlador.Afiliado.AfiliadoVentanaPrincipal.USUARIO_AFILIADO = cedula;
                        new AfiliadoVentanaPrincipal().setVisible(true);
                        */
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

    public static void abrirRegistrar() {
        new CrearUsuario().setVisible(true);
    }

    public static String getSHA1(String contrasenia) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA1");
            byte[] array = md.digest(contrasenia.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
