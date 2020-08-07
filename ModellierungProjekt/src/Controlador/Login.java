package Controlador;

import javax.swing.JOptionPane;

/**
 *
 * @author Santiago Villavicencio villavicencioandrs@gmail.com
 */
public class Login {

    //ingresar
    public static boolean ingresar(String usuario, String password) {
        if(esUsuarioVacio(usuario)){
            JOptionPane.showMessageDialog(null, "El campo de Usuario se encuentra vacío.");
            return false;
        }
        if(esPasswordVacio(password)){
            JOptionPane.showMessageDialog(null, "La Contraseña se encuentra vacía.");
            return false;
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
