/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author P3017OSI
 */
public class inicidenciaDetalleDAO {
    
    private Connection con;
    private Statement statment;
    private ResultSet resultado;

    public inicidenciaDetalleDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public Map<Integer, String> listaIncidenciaDetalle() {
        String sql = "SELECT* FROM INCIDENCIA_DETALLE";
        Map<Integer, String> mapa = new HashMap<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                mapa.put(resultado.getInt("id_inci_deta"), resultado.getString("inicidencia"));
            }
            statment.close();
        } catch (Exception e) {
        }
        return mapa;
    }
}
