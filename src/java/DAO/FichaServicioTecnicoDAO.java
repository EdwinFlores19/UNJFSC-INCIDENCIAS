package DAO;

import MODELO.FichaServicioTecnico;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FichaServicioTecnicoDAO {
    
    private Connection con;
    private Statement statment;
    private CallableStatement call;
    private ResultSet resultado;
    
    public FichaServicioTecnicoDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }
    
    public void insert_FichaServicioTecnico(MODELO.FichaServicioTecnico objFST) {
        //este procedimiento actualiza si no existe regsitro e inserta de ser contrario
        String sql = "{call SP_INSERT_FICHA_SERVICIO_TECNICO(?,?,?,?,?,?,?)}";
        try {
            call = con.prepareCall(sql);
            
            if (!objFST.getRecomendacion().equals("")) {
                call.setString("RECOMENDACION", objFST.getRecomendacion());
            } else {
                call.setNull("RECOMENDACION", java.sql.Types.NULL);
            }
            if (!objFST.getPeriferico_detalle().equals("")) {
                call.setString("PERIFERICO_DETALLE", objFST.getPeriferico_detalle());
            } else {
                call.setNull("PERIFERICO_DETALLE", java.sql.Types.NULL);
            }
            call.setInt("ID_PERIFERICO", objFST.getId_periferico());
            call.setString("COD_EQUIPO", objFST.getCod_equipo());
            call.setString("TRASLADO", objFST.getTraslado());
            call.setString("S_CORRECTIVO", objFST.getS_correctivo());
            call.setString("INCIDENCIA", objFST.getId_incidencia());
            call.setString("RECOMENDACION", objFST.getRecomendacion());
            call.execute();
            call.close();
            
        } catch (Exception e) {
            System.out.println("ivan error en clase FichaServicioTecnicoDAO metodo insert_FichaServicioTecnico detalles" + e.getMessage());
        }
    }

    //devuele un archivo json
    public String detalles_FichaServicioTecnicoJSON(String id_incidencia) {
        String sql = String.format("SELECT* FROM FICHA_SERVICIO_TECNICO WHERE id_inci='%s'", id_incidencia);
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            if (resultado.next()) {
                JSON.addProperty("ID_PERIFERICO", resultado.getString("ID_PERIFERICO"));
                JSON.addProperty("PERIFERICO_DETALLE", resultado.getObject("PERIFERICO_DETALLE") == null ? "" : resultado.getString("PERIFERICO_DETALLE"));
                JSON.addProperty("COD_EQUIPO", resultado.getString("COD_EQUIPO"));
                JSON.addProperty("TRASLADO", resultado.getString("TRASLADO"));
                JSON.addProperty("S_CORRECTICO", resultado.getString("S_CORRECTIVO"));
                JSON.addProperty("RECOMENDACION", resultado.getString("RECOMENDACION"));
            } else {
                JSON.addProperty("notExisteFicha", Boolean.FALSE);
            }
        } catch (Exception e) {
            System.out.println("ivan error en clase FichaServicioTecnicoDAO metododetalles_FichaServicioTecnicoJSON detalles" + e.getMessage());
        }
        return JSON.toString();
    }

    //devuelve un objeto
    public FichaServicioTecnico detalles_FichaServicioTecnico(String id_incidencia) {
        String sql = String.format("SELECT* FROM FICHA_SERVICIO_TECNICO WHERE id_inci='%s'", id_incidencia);
        FichaServicioTecnico ficha = new FichaServicioTecnico();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            if (resultado.next()) {
                ficha.setId_fst(resultado.getString("ID_ST"));
                ficha.setId_periferico(resultado.getInt("ID_PERIFERICO"));
                ficha.setPeriferico_detalle(resultado.getString("PERIFERICO_DETALLE"));
                ficha.setCod_equipo(resultado.getString("COD_EQUIPO"));
                ficha.setTraslado(resultado.getString("TRASLADO"));
                ficha.setS_correctivo(resultado.getString("S_CORRECTIVO"));
                ficha.setRecomendacion(resultado.getString("RECOMENDACION"));
            }            
        } catch (Exception e) {
            System.out.println("ivan error en clase FichaServicioTecnicoDAO metododetalles_FichaServicioTecnico detalles" + e.getMessage());
        }
        return ficha;
    }
}
