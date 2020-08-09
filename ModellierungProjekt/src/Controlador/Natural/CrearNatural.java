package Controlador.Natural;

import Controlador.ConnectionDB;
import static Vista.UsuarioNatural.CrearNatural.jpwdContrasenia;
import static Vista.UsuarioNatural.CrearNatural.jtxtNombre;
import static Vista.UsuarioNatural.CrearNatural.jtxtApellido;
import static Vista.UsuarioNatural.CrearNatural.jtxtCedula;
import static Vista.UsuarioNatural.CrearNatural.jtxtDireccion;
import static Vista.UsuarioNatural.CrearNatural.jtxtDireccionX;
import static Vista.UsuarioNatural.CrearNatural.jtxtDireccionY;
import static Vista.UsuarioNatural.CrearNatural.jcbxRol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Santiago Villavicencio villavicencioandrs@gmail.com
 */
public class CrearNatural {

    public static void limpiar() {
        jtxtCedula.setText("");
        jpwdContrasenia.setText("");
        jcbxRol.setSelectedIndex(0);
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
        } else if (jcbxRol.getSelectedItem().equals("Seleccione")) {
            JOptionPane.showMessageDialog(null, "SELECCIONAR ROL");
            jcbxRol.requestFocus();
        } else {
            try {
                ConnectionDB cc = new ConnectionDB();
                Connection conn = cc.getConnection();
                String CED_USU, CONT_USU, ROL_USU, NOM_USU, APE_USU, DIR_X, DIR_Y, DIR_USU_NORMAL;
                String sql = "";
                CED_USU = jtxtCedula.getText();
                CONT_USU = String.valueOf(jpwdContrasenia.getPassword());
                ROL_USU = jcbxRol.getSelectedItem().toString();
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
                        + "values(?,?,?,?,?,?,?,?)";
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
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
}
