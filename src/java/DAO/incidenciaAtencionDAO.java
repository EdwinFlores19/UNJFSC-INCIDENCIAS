package DAO;

import MODELO.incidenciaAtencion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class incidenciaAtencionDAO {

    private Connection con;
    private Statement statment;
    private ResultSet resultado;

    public incidenciaAtencionDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public List<incidenciaAtencion> ListaincidenciaAtencion(String id_incidencia) {
        String cadena = "SELECT * FROM V_INCIDENCIA_ATENCION where id_inci='%s'";
        String sql = String.format(cadena, id_incidencia);
        List<incidenciaAtencion> lista = new ArrayList<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                lista.add(new incidenciaAtencion(resultado.getString("id_inci"), resultado.getInt("id_pers"), resultado.getString("nombre")));
            }
            statment.close();
        } catch (Exception e) {
        }
        return lista;
    }

}
