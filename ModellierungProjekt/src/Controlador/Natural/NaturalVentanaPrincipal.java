package Controlador.Natural;

import Controlador.ConnectionDB;
import Vista.UsuarioNatural.BusquedaAproximada;
import static Vista.UsuarioNatural.NaturalVentanaPrincipal.jtxtCodigoProducto;
import static Vista.UsuarioNatural.NaturalVentanaPrincipal.jtxtNombreProducto;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    public static String tipoBusqueda = "";

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
        listModel = new DefaultListModel();
        String sql = "SELECT NOM_PRO FROM PRODUCTOS WHERE NOM_PRO LIKE '" + nombre + "%';";
        tipoBusqueda = "NOMBRE";
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
        listModel = new DefaultListModel();
        String sql = "SELECT ID_PRO FROM PRODUCTOS WHERE ID_PRO LIKE '" + codigo + "%';";
        tipoBusqueda = "CODIGO";
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

    public static void cargarOtroDato(String nomCodProducto) {
        String sql = "SELECT ID_PRO, NOM_PRO FROM PRODUCTOS WHERE ";
        switch (tipoBusqueda) {
            case "CODIGO":
                sql += "ID_PRO = '" + nomCodProducto + "';";
                break;
            case "NOMBRE":
                sql += "NOM_PRO = '" + nomCodProducto + "';";
                break;
        }
        try {
            PreparedStatement ps = Controlador.ConnectionDB.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            switch (tipoBusqueda) {
                case "CODIGO":
                    jtxtNombreProducto.setText(rs.getString("NOM_PRO"));
                    break;
                case "NOMBRE":
                    jtxtCodigoProducto.setText(rs.getString("ID_PRO"));
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NaturalVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<ArrayList> buscarProductos(String nombre, KeyEvent evt) throws SQLException {
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            ArrayList<ArrayList> productos = new ArrayList<>();
            ArrayList<String> datos = new ArrayList<>();
            int longitud = nombre.length();
            int pos = 0;
            int pos2 = (longitud / 2) - 1;
            int pos3 = longitud - 1;
            String cc1 = String.valueOf(nombre.charAt(pos));
            String cc2 = String.valueOf(nombre.charAt(pos2));
            String cc3 = String.valueOf(nombre.charAt(pos3));
            ConnectionDB cc = new ConnectionDB();
            Connection cn = cc.getConnection();
            String sql = "";
            // OR NOM_PRO LIKE '" + cc1 + "%" + cc2 + "%" + cc3 + "'
            sql = "select * from productos where NOM_PRO ='" + nombre + "' OR NOM_PRO LIKE '" + cc1 + "%" + cc2 + "%" + cc3 + "'";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                datos.add(rs.getString("NOM_PRO"));
                productos.add(datos);
                listModel.addElement(rs.getString("NOM_PRO"));
                /*
                datos.add(rs.getString("MAR_PRO"));
                datos.add(rs.getString("PRE_PRO"));
                datos.add(rs.getString("CAN_PRO"));
                datos.add(rs.getString("FEC_VEN_PRO"));
                 */
                datos.clear();
            }
            ba.jlBusqueda.setModel(listModel);
            if (!ba.isActive()) {
                ba.setVisible(true);
            }
            return productos;
        }
        return null;
    }
}
