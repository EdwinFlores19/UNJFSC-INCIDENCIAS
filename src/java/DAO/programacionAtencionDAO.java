/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import MODELO.V_PROGRAMACION_ATENCION;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author P3017OSI
 */
public class programacionAtencionDAO {

    private Connection con;
    private Statement statment;
    private ResultSet resultado;

    public programacionAtencionDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public List<V_PROGRAMACION_ATENCION> detallesProgramacionAtencion(String id_programacion) {
        String cadena = "SELECT* FROM V_PROGRAMACION_ATENCION WHERE id_progra='%s'";
        String sql = String.format(cadena, id_programacion);
        List<V_PROGRAMACION_ATENCION> lista = new ArrayList<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                lista.add(new V_PROGRAMACION_ATENCION(resultado.getString("id_progra"), resultado.getInt("id_pers"), resultado.getString("NOMBRE")));
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en el metodo detallesProgramacionAtencion() de la clase programacionAtencionDAO mas detalles: " + e.getMessage());
        }
        return lista;
    }
}
