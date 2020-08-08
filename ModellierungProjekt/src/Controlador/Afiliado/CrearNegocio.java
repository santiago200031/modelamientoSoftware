/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Afiliado;

import static Controlador.Afiliado.AfiliadoVentanaPrincipal.USUARIO_AFILIADO;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Santiago Villavicencio villavicencioandrs@gmail.com
 */
public class CrearNegocio {

    private static final Connection conn = Controlador.ConnectionDB.getConnection();

    public static void setRucDefault(String USUARIO_AFILIADO) {
        Vista.UsuarioAfiliado.CrearNegocio.jtxtRuc.setText(USUARIO_AFILIADO + "001");
    }

    public static boolean guardarNegocio(String ruc, String categoria, String nombreNegocio, String direccion, int coordenadaX, int coordenadaY, String provincia, String ciudad) {
        if (isRucVacio(ruc)) {
            JOptionPane.showMessageDialog(null, "El RUC se encuentra vacío.");
            return false;
        }
        if (isCategoriaVacio(categoria)) {
            JOptionPane.showMessageDialog(null, "La categoría se encuentra vacía.");
            return false;
        }
        if (isNombreNegocioVacio(nombreNegocio)) {
            JOptionPane.showMessageDialog(null, "El nombre del negicio se encuentra vacío.");
            return false;
        }
        if (isDireccionVacio(direccion)) {
            JOptionPane.showMessageDialog(null, "La dirección se encuentra vacía.");
            return false;
        }
        if (isProvinciaVacio(provincia)) {
            JOptionPane.showMessageDialog(null, "La provincia se encuentra vacía.");
            return false;
        }
        if (isCiudadVacio(ciudad)) {
            JOptionPane.showMessageDialog(null, "La ciudad se encuentra vacía.");
            return false;
        }
        String sql = "INSERT INTO NEGOCIOS (RUC_NEG, CED_USU_PER, CAT_NEG, NOM_NEG, DIR_NEG_NOR, DIR_X, DIR_Y, CIU_NEG, PRO_NEG) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ruc);
            ps.setString(2, USUARIO_AFILIADO);
            ps.setString(3, categoria);
            ps.setString(4, nombreNegocio);
            ps.setString(5, direccion);
            ps.setInt(6, coordenadaX);
            ps.setInt(7, coordenadaY);
            ps.setString(8, ciudad);
            ps.setString(9, provincia);
            int n = ps.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Negocio registrado correctamente.");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CrearNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static boolean isRucVacio(String ruc) {
        return ruc.equals("");
    }

    private static boolean isCategoriaVacio(String categoria) {
        return categoria.equals("");
    }

    private static boolean isNombreNegocioVacio(String nombreNegocio) {
        return nombreNegocio.equals("");
    }

    private static boolean isDireccionVacio(String direccion) {
        return direccion.equals("");
    }

    private static boolean isProvinciaVacio(String provincia) {
        return provincia.equals("");
    }

    private static boolean isCiudadVacio(String ciudad) {
        return ciudad.equals("");
    }

    public static void toUpperCaseTyped(KeyEvent evt) {
        evt.setKeyChar(String.valueOf(evt.getKeyChar()).toUpperCase().charAt(0));
    }

}
