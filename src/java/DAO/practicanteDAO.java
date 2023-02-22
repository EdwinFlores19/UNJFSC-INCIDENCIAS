/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import MODELO.practicante;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author P3017OSI
 */
public class practicanteDAO {

    private Connection con;
    private Statement statment;
    private CallableStatement call;
    private ResultSet resultado;

    public practicanteDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public practicante detallesPracticante(int id) {
        practicante p =new practicante(0, 0, 0, 0);
        String sql = String.format("SELECT * FROM  PRACTICANTE WHERE id_pers=%d", id);
        resultado = null;
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            if (resultado.next()) {
                p.setId_prac(resultado.getInt("id_prac"));
                p.setId_pers(resultado.getInt("id_pers"));
                p.setHoras_x_dia(resultado.getInt("HORAS_x_DIA"));
                p.setTotal_dias_laborables(resultado.getInt("TOTAL_DIAS_LABORABLES"));
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("Error ivan en el metodo detallesPracticante de la clase  practicanteDAO detalles aqui: "+e.getMessage());
        }
        return p;
    }
    
    public void updatePracticante(practicante p) {
        String sql = "{call SP_UPDATE_PRACTICANTE(?,?,?)}";
        
        try {
            call = con.prepareCall(sql);
            call.setInt("PERSONA", p.getId_pers());
            call.setInt("HORAS", p.getHoras_x_dia());
            call.setInt("DIAS", p.getTotal_dias_laborables());

            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("ivan error em iupdatePracticante detalles:" + e.getMessage());
        }
    }
}
