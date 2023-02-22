/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ivan Basilio
 */
@Service
public class usuarioDAO {

    private Connection con;
    private Statement statment;
    private CallableStatement call;
    private ResultSet resultado;

    public usuarioDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public ResultSet validadarUsuario(String usuario, String password) {
        try {
            call = con.prepareCall("{call INICIOSESION(?,?)}");
            call.setString("USUARIO", usuario);
            call.setString("PASSWORD", password);
            resultado = call.executeQuery();
        } catch (Exception e) {
            System.out.println("ivan error en el metodo validadarUsuario() de la clase usuarioDAO detalles:  "+e.getMessage());
        }
        return resultado;
    }
    
    public ResultSet cambiarPassword(String currentpass,String newPass,int id_pers){
        String sql="{call SP_CHANGE_PASSWORD(?,?,?)}";
    try {
            call = con.prepareCall(sql);
            call.setString("passOld", currentpass);
            call.setString("passNew", newPass);
            call.setInt("id_pers", id_pers);
            resultado= call.executeQuery();
        } catch (Exception e) {
            System.out.println("ivan error en el metodo cambiarPassword de la clase usuarioDAO detalles: "+e.getMessage());
        }
    return resultado;   
    }
}
