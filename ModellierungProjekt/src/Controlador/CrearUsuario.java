package Controlador;

import Vista.Login;

/**
 *
 * @author Santiago Villavicencio villavicencioandrs@gmail.com
 */
public class CrearUsuario {

    public static void abrirLogin() {
        new Login().setVisible(true);
    }

    public static void abrirCrearNatural() {
         new Vista.UsuarioNatural.CrearNatural().setVisible(true);
    }

    public static void abrirCrearAfiliado() {
        new Vista.UsuarioAfiliado.CrearAfiliado().setVisible(true);
    }

}
