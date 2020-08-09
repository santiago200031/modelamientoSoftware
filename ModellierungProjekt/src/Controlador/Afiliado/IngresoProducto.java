/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Afiliado;

import Controlador.ConnectionDB;
import static Vista.UsuarioAfiliado.IngresoProducto.jtxtCantidad;
import static Vista.UsuarioAfiliado.IngresoProducto.jtxtID;
import static Vista.UsuarioAfiliado.IngresoProducto.jtxtMarca;
import static Vista.UsuarioAfiliado.IngresoProducto.jtxtNombre;
import static Vista.UsuarioAfiliado.IngresoProducto.jtxtPrecio;
import static Vista.UsuarioAfiliado.IngresoProducto.jtxtRUC;
import static Vista.UsuarioAfiliado.IngresoProducto.jtxtVencimiento;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Dayis
 */
public class IngresoProducto {

    public static void limpiar() {
        jtxtID.setText("");
        jtxtNombre.setText("");
        jtxtMarca.setText("");
        jtxtPrecio.setText("");
        jtxtCantidad.setText("");
        jtxtVencimiento.setText("");
        jtxtRUC.setText("");
    }

    public static void guardarPro() {
        if (jtxtID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR ID DEL PRODUCTO");
        } else if (jtxtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR  NOMBRE DEL PRODUCTO");
        } else if (jtxtMarca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR MARCA DEL PRODUCTO");
        } else if (jtxtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR PRECIO DE PRODUCTO");
        } else if (jtxtCantidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR CANTIDAD");
        } else if (jtxtVencimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR DIRECCION");
        } else if (jtxtRUC.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESAR RUC DEL NEGOCIO AL QUE PERTENECE");
        } else {
            try {
                ConnectionDB cc = new ConnectionDB();
                Connection conn = cc.getConnection();
                String ID_PRO, NOM_PRO, MAR_PRO, PRE_PRO, CAN_PRO, FEC_VEN_PRO, RUC_NEG_PER;
                String sql = "";
                ID_PRO = jtxtID.getText();
                NOM_PRO = jtxtNombre.getText();
                MAR_PRO = jtxtMarca.getText();
                PRE_PRO = jtxtPrecio.getText();
                CAN_PRO = jtxtCantidad.getText();
                //Date.valueOf(jtxtVencimiento.getText());
                RUC_NEG_PER = jtxtRUC.getText();
                sql = "insert into productos(ID_PRO, NOM_PRO, MAR_PRO, PRE_PRO, CAN_PRO, FEC_VEN_PRO, RUC_NEG_PER) values(?,?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, ID_PRO);
                ps.setString(2, NOM_PRO);
                ps.setString(3, MAR_PRO);
                ps.setString(4, PRE_PRO);
                ps.setString(5, CAN_PRO);
                ps.setDate(6, Date.valueOf(jtxtVencimiento.getText()));
                ps.setString(7, RUC_NEG_PER);
                int n = ps.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "¡ERROR! Datos no guardados");
                    limpiar();
                }
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    public static void numeros(KeyEvent evt){
        int nCaracteres = 10;
        if (jtxtID.getText().length() >= nCaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo cuenta con 10 caracteres");
        }

        char c;
        c = evt.getKeyChar();
        if ((c < '0') || (c > '9')) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo números");
        }
    }
    
    public static void fecha(KeyEvent evt){
        int nCaracteres = 10;
        if (jtxtVencimiento.getText().length() >= nCaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo cuenta con 10 caracteres");
        }

        char c;
        c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String s = ("" + c).toUpperCase();
            c = s.charAt(0);
            evt.setKeyChar(c);
        }
            evt.setKeyChar(c);
        if ((int) evt.getKeyChar() >= 0 && (int) evt.getKeyChar() <= 44
                || (int) evt.getKeyChar() >= 46 && (int) evt.getKeyChar() <= 47
                || (int) evt.getKeyChar() >= 58 && (int) evt.getKeyChar() <= 255) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese fecha de vencimiento en formato: YYYY-MM-DD");
        }
    }
    
}
