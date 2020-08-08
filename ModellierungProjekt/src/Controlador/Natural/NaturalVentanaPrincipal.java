package Controlador.Natural;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Santiago Villavicencio villavicencioandrs@gmail.com
 */
public class NaturalVentanaPrincipal {

    public static String USUARIO_NATURAL;
    private static final String[] titulos = {"Codigo", "Nombre", "Cantidad", "Precio U.", "Subtotal"};
    private static DefaultTableModel model;

    public static void busquedaProductoCodigo(String codigo) {
        String sql = "SELECT ID_PRO FROM PRODUCTOS WHERE ID_PRO LIKE '" + codigo + "%';";
        try {
            PreparedStatement ps = Controlador.ConnectionDB.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //model.addElement(rs.getString("ID_PRO"));
            }
            // jcbCodigoProducto.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(NaturalVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void busquedaProductoNombre(String nombre, JPopupMenu jpmSugerenciaNombre, JFrame ventana) {
        String sql = "SELECT NOM_PRO FROM PRODUCTOS WHERE ID_PRO LIKE '" + nombre + "%';";
        jpmSugerenciaNombre.removeAll();
        try {
            PreparedStatement ps = Controlador.ConnectionDB.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jpmSugerenciaNombre.add(rs.getString("NOM_PRO"));
            }
            jpmSugerenciaNombre.show(ventana, 500, 500);
        } catch (SQLException ex) {
            Logger.getLogger(NaturalVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void toUpperCase(KeyEvent evt) {
        evt.setKeyChar(String.valueOf(evt.getKeyChar()).toUpperCase().charAt(0));
    }

    public static void agregarDetalle(String nombre, String codigo, JTable jtblDetalle, int cantidad) {
        String sql = "SELECT * FROM PRODUCTOS WHERE NOM_PRO = ? AND ID_PRO = ?;";
        model = new DefaultTableModel(null, titulos);
        try {
            PreparedStatement ps = Controlador.ConnectionDB.getConnection().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, codigo);
            Object[] datos = new Object[5];
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                datos[0] = rs.getString("ID_PRO");
                datos[1] = rs.getString("NOM_PRO");
                datos[2] = cantidad;
                datos[3] = rs.getFloat("PRE_PRO");
                datos[4] = cantidad * (float) rs.getFloat("PRE_PRO");
                model.addRow(datos);
                jtblDetalle.setModel(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NaturalVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean realizarReserva() {
        if (JOptionPane.showConfirmDialog(null, "Desea continuar con la reserva?", "Confirmación de la reserva", JOptionPane.YES_NO_OPTION) == 1) {
            return true;
        }
        return false;
    }
}
