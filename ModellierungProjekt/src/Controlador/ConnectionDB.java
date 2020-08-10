package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Santiago Villavicencio villavicencioandrs@gmail.com
 */
public class ConnectionDB {

    private static final String URL = "jdbc:mysql://127.0.0.1/motordebusqueda", USERNAME = "root", PASSWORD = "";

    public  static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            
            
        }
        return conn;
    }

}
