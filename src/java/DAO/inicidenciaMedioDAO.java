package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class inicidenciaMedioDAO {

    private Connection con;
    private Statement statment;
    private ResultSet resultado;

    public inicidenciaMedioDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public Map<Integer, String> listaMedio() {
        String sql = "SELECT* FROM INCIDENCIA_MEDIO";
        Map<Integer, String> mapa = new HashMap<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                mapa.put(resultado.getInt("id_inci_medio"), resultado.getString("medio"));
            }
            statment.close();
        } catch (Exception e) {
        }
        return mapa;
    }
}
