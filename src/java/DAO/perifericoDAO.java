package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class perifericoDAO {

    private Connection con;
    private Statement statment;
    private CallableStatement call;
    private ResultSet resultado;

    public perifericoDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public Map<Integer, String> listaPerifericos() {
        String sql = "SELECT* FROM PERIFERICOS";
        Map<Integer, String> lista = new HashMap<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                lista.put(resultado.getInt("ID_PERIFERICO"), resultado.getString("PERIFERICO"));
            }
        } catch (Exception e) {
            System.out.println("Ivan Hubo un error en la clase equipoDAO metodo listaPerifericos() detalles aqui: " + e.getMessage());
        }
        return lista;
    }

    public String nombrePeriferico(int id_periferico) {
        Map<Integer, String> lista = listaPerifericos();
        return lista.get(id_periferico);
    }
}
