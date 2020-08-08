package Controlador.Natural;

import Vista.UsuarioNatural.BusquedaAproximada;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    private static final DefaultTableModel tableModel = new DefaultTableModel(null, titulos);
    static BusquedaAproximada ba = new BusquedaAproximada();
    private static DefaultListModel listModel = new DefaultListModel();

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

    public static void agregarDetalle(String nombre, String codigo, JTable jtblDetalle, int cantidad, JLabel jlblTotal) {
        String sql = "SELECT * FROM PRODUCTOS WHERE NOM_PRO = ? AND ID_PRO = ?;";
        try {
            PreparedStatement ps = Controlador.ConnectionDB.getConnection().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, codigo);
            Object[] datos = new Object[5];
            for (int i = 0; i < jtblDetalle.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).toString().equals(codigo)) {
                    tableModel.setValueAt(cantidad, i, 2);
                    jtblDetalle.setModel(tableModel);
                    tableModel.setValueAt(cantidad * (float) tableModel.getValueAt(i, 3), i, 4);
                    calcularTotal(jtblDetalle, jlblTotal);
                    return;
                }
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                datos[0] = rs.getString("ID_PRO");
                datos[1] = rs.getString("NOM_PRO");
                datos[2] = cantidad;
                datos[3] = rs.getFloat("PRE_PRO");
                datos[4] = cantidad * (float) rs.getFloat("PRE_PRO");
                tableModel.addRow(datos);
                jtblDetalle.setModel(tableModel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NaturalVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean realizarReserva() {
        if (JOptionPane.showConfirmDialog(null, "Desea continuar con la reserva?", "Confirmación de la reserva", JOptionPane.YES_NO_OPTION) == 0) {
            JOptionPane.showMessageDialog(null, "Reserva registrada correctamente.");
            //insertarReservaBD();
            return true;
        }
        return false;
    }

    public static void calcularTotal(JTable jtblDetalle, JLabel jlblTotal) {
        if (!jtblDetalle.getValueAt(0, 0).toString().equals("")) {
            float total = 0;
            for (int i = 0; i < jtblDetalle.getRowCount(); i++) {
                total += Float.parseFloat(jtblDetalle.getValueAt(i, 4).toString().replace(",", "."));
            }
            jlblTotal.setText(String.valueOf(total));
        }
    }

    public static void busquedaAproximadaNombre(String nombre) {
        String sql = "SELECT NOM_PRO FROM PRODUCTOS WHERE NOM_PRO LIKE '" + nombre + "%';";
        if (nombre.length() >= 2) {
            if (!ba.isActive()) {
                ba.setVisible(true);
            }
            try {
                PreparedStatement ps = Controlador.ConnectionDB.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    listModel.addElement(rs.getString("NOM_PRO"));
                }
                ba.jlBusqueda.setModel(listModel);
            } catch (SQLException ex) {
                Logger.getLogger(NaturalVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void busquedaAproximadaCodigo(String codigo) {
        String sql = "SELECT NOM_PRO FROM PRODUCTOS WHERE ID_PRO LIKE '" + codigo + "%';";
        if (codigo.length() >= 6) {
            if (!ba.isActive()) {
                ba.setVisible(true);
            }
            try {
                PreparedStatement ps = Controlador.ConnectionDB.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    listModel.addElement(rs.getString("ID_PRO"));
                }
                ba.jlBusqueda.setModel(listModel);
            } catch (SQLException ex) {
                Logger.getLogger(NaturalVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
