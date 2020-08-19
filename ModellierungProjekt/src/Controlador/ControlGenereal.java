/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Marlon 1
 */
public class ControlGenereal {

    public ArrayList<ArrayList> buscarProductos(String nombre) throws SQLException {
        ArrayList<ArrayList> productos = new ArrayList<ArrayList>();
         ArrayList<String> datos = new ArrayList<String>();
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
        sql = "select *from estudiantes where NOM_PRO ='"+nombre+"' OR NOM_PRO LIKE '"+cc1+"%"+cc2+"%"+cc3+"'";
        Statement psd = cn.createStatement();
        ResultSet rs = psd.executeQuery(sql);
       String[] datosProducto = new String[5];
        while(rs.next()){
             datos.add(rs.getString("NOM_PRO"));
             datos.add(rs.getString("MAR_PRO"));
             datos.add(rs.getString("PRE_PRO"));
             datos.add(rs.getString("CAN_PRO"));
             datos.add(rs.getString("FEC_VEN_PRO"));
             productos.add(datos);
             datos.clear();
          }
        

        return productos;
    }

}
