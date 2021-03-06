package Controlador.Afiliado;

import Controlador.ConnectionDB;
import Vista.Login;
import static Vista.UsuarioAfiliado.CrearAfiliado.jpwdContrasenia;
import static Vista.UsuarioAfiliado.CrearAfiliado.jtxtApellido;
import static Vista.UsuarioAfiliado.CrearAfiliado.jtxtCedula;
import static Vista.UsuarioAfiliado.CrearAfiliado.jtxtDireccion;
import static Vista.UsuarioAfiliado.CrearAfiliado.jtxtDireccionX;
import static Vista.UsuarioAfiliado.CrearAfiliado.jtxtDireccionY;
import static Vista.UsuarioAfiliado.CrearAfiliado.jtxtNombre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Santiago Villavicencio villavicencioandrs@gmail.com
 */
public class CrearAfiliado {

    public static void limpiar() {
        jtxtCedula.setText("");
        jpwdContrasenia.setText("");
        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtDireccionX.setText("");
        jtxtDireccionY.setText("");
        jtxtDireccion.setText("");
    }

    public static void guardar() {
        if (jtxtCedula.getText().isEmpty() || jtxtCedula.getText().equals("0000000000")) {
            JOptionPane.showMessageDialog(null, "INGRESAR CÉDULA");
            jtxtCedula.requestFocus();
        } else if (jtxtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR  NOMBRE");
            jtxtNombre.requestFocus();
        } else if (jtxtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR APELLIDO");
            jtxtApellido.requestFocus();
        } else if (jpwdContrasenia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR CONTRASEÑA");
            jpwdContrasenia.requestFocus();
        } else if (jtxtDireccionX.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR DIRECCION");
            jtxtDireccionX.requestFocus();
        } else if (jtxtDireccionY.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR DIRECCION");
            jtxtDireccionY.requestFocus();
        } else if (jtxtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR DIRECCION");
            jtxtDireccion.requestFocus();
        } else {
            try {
                ConnectionDB cc = new ConnectionDB();
                try (Connection conn = cc.getConnection()) {
                    String CED_USU, CONT_USU, ROL_USU, NOM_USU, APE_USU, DIR_X, DIR_Y, DIR_USU_NORMAL;
                    String sql = "";
                    CED_USU = jtxtCedula.getText();
                    CONT_USU = String.valueOf(jpwdContrasenia.getPassword());
                    ROL_USU = "AFILIADO";
                    NOM_USU = jtxtNombre.getText();
                    APE_USU = jtxtApellido.getText();
                    DIR_X = jtxtDireccionX.getText();
                    DIR_Y = jtxtDireccionY.getText();
                    DIR_USU_NORMAL = jtxtDireccion.getText();
                    sql = "INSERT INTO USUARIOS"
                            + "(CED_USU, "
                            + "CONT_USU, "
                            + "ROL_USU, "
                            + "NOM_USU, "
                            + "APE_USU, "
                            + "DIR_X, "
                            + "DIR_Y, "
                            + "DIR_USU_NORMAL)"
                            + "values(?,SHA1(?),?,?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, CED_USU);
                    ps.setString(2, CONT_USU);
                    ps.setString(3, ROL_USU);
                    ps.setString(4, NOM_USU);
                    ps.setString(5, APE_USU);
                    ps.setString(6, DIR_X);
                    ps.setString(7, DIR_Y);
                    ps.setString(8, DIR_USU_NORMAL);
                    int n = ps.executeUpdate();
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "¡ERROR! Datos no guardados");
                        limpiar();
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public static void abrirLogin() {
        new Login().setVisible(true);
    }

}
