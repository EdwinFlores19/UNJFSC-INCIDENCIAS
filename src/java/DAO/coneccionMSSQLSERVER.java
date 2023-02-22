/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Ivan Basilio
 */
public class coneccionMSSQLSERVER {

    private static final String uri="jdbc:sqlserver://localhost:EDWINFLORES;database=INCIDENCIAS;";
    private static final String usuario="sa";
    private static final String password="12345";
    
    public coneccionMSSQLSERVER() {
    }

    public static Connection returnConnSQLServer() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(coneccionMSSQLSERVER.uri, coneccionMSSQLSERVER.usuario,coneccionMSSQLSERVER.password);
        } catch (ClassNotFoundException ex) {
            System.out.println("error clase no encontrada en el metodo returnConnSQLServer en la clase coneccionMSSQLSERVER"+ex.getMessage());
        } catch (Exception e) {
            System.out.println("ivan error en el metodo returnConnSQLServer() de la clase  coneccionMSSQLSERVER"+e.getMessage());
        }
        return connection;
    }
}
