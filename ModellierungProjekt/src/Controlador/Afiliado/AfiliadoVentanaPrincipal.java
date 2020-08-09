package Controlador.Afiliado;

/**
 *
 * @author Santiago Villavicencio villavicencioandrs@gmail.com
 */
public class AfiliadoVentanaPrincipal {

    public static String USUARIO_AFILIADO;

    public static void abrirNuevoNegocio() {
        new Vista.UsuarioAfiliado.CrearNegocio().setVisible(true);
    }

}
