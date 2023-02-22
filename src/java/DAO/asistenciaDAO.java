package DAO;

import MODELO.asistencia;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class asistenciaDAO {

    private Connection con;
    private Statement statment;
    private CallableStatement call;
    private ResultSet resultado;

    public asistenciaDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public List<String[]> listaPrincipalAsistencia() {
        String sql = "SELECT* FROM V_ASIST_PRINCIPAL WHERE FECHA=CAST(GETDATE() AS DATE) ORDER BY HORA_DE_INGRESO ASC";
        List<String[]> lista = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                lista.add(new String[]{dateFormat.format(resultado.getDate("FECHA")),
                    resultado.getString("PRACTICANTE"),
                    resultado.getTime("HORA_DE_INGRESO").toString(),
                    resultado.getTime("HORA_DE_SALIDA") == null ? "--:--:--" : resultado.getTime("HORA_DE_SALIDA").toString(),
                    resultado.getString("OBSERVACION") == null ? "" : resultado.getString("OBSERVACION")});
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en clase asistenciaDAO metodo listaPrincipalAsistencia detalles" + e.getMessage());
        }
        return lista;
    }

    public List<String[]> listaPracticanteSinAsistencia() {
        String sql = "SELECT* FROM V_PRACT_ASISTENCIA";
        List<String[]> lista = new ArrayList<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                lista.add(new String[]{String.valueOf(resultado.getInt("CODIGO")), resultado.getString("PRACTICANTES")});
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en clase asistenciaDAO metodo listaPrincipalAsistencia detalles" + e.getMessage());
        }
        return lista;
    }

    public void insert_asistencia(String[] atencion, asistencia asist) {
        String sql = "{call SP_INSERT_ASISTENCIA(?,?,?,?,?)}";
        try {
            for (String A : atencion) {
                call = con.prepareCall(sql);
                if (asist.getObservacion() != null & !asist.getObservacion().equals("")) {
                    call.setString("OBSERVACION", asist.getObservacion());
                } else {
                    call.setNull("OBSERVACION", java.sql.Types.NULL);
                }
                if (asist.getHora_de_salida() != null & !asist.getHora_de_salida().equals("")) {
                    call.setString("H_SALIDA", asist.getHora_de_salida());
                } else {
                    call.setNull("H_SALIDA", java.sql.Types.NULL);
                }
                call.setString("FECHA", asist.getFecha());
                call.setString("H_INGRESO", asist.getHora_de_ingreso());
                call.setInt("PRACTICANTE", Integer.parseInt(A));
                call.execute();
                call.close();
            }

        } catch (Exception e) {
            System.out.println("ivan error en clase asistenciaDAO metodo insert_asistencia detalles" + e.getMessage());
        }
    }

    public String detallesAsistencia(String fecha, String practicante) {
        String sql = String.format("SELECT* FROM V_ASIST_PRINCIPAL WHERE FECHA=CAST('%s' AS DATE) AND PRACTICANTE ='%s'", fecha, practicante);
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            if (resultado.next()) {
                JSON.addProperty("FECHA", resultado.getDate("FECHA").toString());
                JSON.addProperty("H_I", resultado.getTime("HORA_DE_INGRESO").toString());
                JSON.addProperty("H_S", resultado.getTime("HORA_DE_SALIDA") == null ? null : resultado.getTime("HORA_DE_SALIDA").toString());
                JSON.addProperty("ID_PRAC", resultado.getString("id_prac"));
                JSON.addProperty("NAME_PRAC", resultado.getString("PRACTICANTE"));
                JSON.addProperty("OBSERVACION", resultado.getString("OBSERVACION") == null ? null : resultado.getString("OBSERVACION"));
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en clase asistenciaDAO metodo detallesAsistencia detalles" + e.getMessage());
        }
        return JSON.toString();
    }

    //ESTE METODO ES LLAMADO POR EL MISMO PRACTICANTE
    public void insert_asistencia_practicante(String dni) {
        String sql = "{call SP_INSERT_ASISTENCIA_x_PRACTICANTE(?)}";
        try {
            call = con.prepareCall(sql);
            call.setString("DNI", dni);
            call.execute();
            call.close();

        } catch (Exception e) {
            System.out.println("ivan error en clase asistenciaDAO metodo insert_asistencia_practicante detalles" + e.getMessage());
        }
    }

    //ESTE METODO ACTUALIZA LA ASISTENCIA DE UN SOLO PRACTICANTE
    public void update_asistencia(asistencia objAsistencia) {
        String sql = "{call SP_UPDATE_ASISTENCIA(?,?,?,?,?)}";
        try {
            call = con.prepareCall(sql);
            call.setString("FECHA", objAsistencia.getFecha());
            call.setInt("PRACTICANTE", objAsistencia.getId_prac());
            call.setString("H_I", objAsistencia.getHora_de_ingreso());
            if (objAsistencia.getHora_de_salida().equals("")) {
                call.setNull("H_S", java.sql.Types.NULL);
            } else {
                call.setString("H_S", objAsistencia.getHora_de_salida());
            }

            if (!objAsistencia.getObservacion().equals("")) {
                call.setString("OBSERVACION", objAsistencia.getObservacion());
            } else {
                call.setNull("OBSERVACION", java.sql.Types.NULL);
            }
            call.execute();
            call.close();

        } catch (Exception e) {
            System.out.println("ivan error en clase asistenciaDAO metodo update_asistencia detalles" + e.getMessage());
        }
    }

    /*
        *este metodo devuele la historia de asistencias del practicante x
        **/
    public List<asistencia> ListaAsistenciaTotalPracticante(int id_pers) {
        String sql = "{call SP_HISTORIA_ASIST_PRACT(?)}";
        List<asistencia> lista = new ArrayList<>();
        try {
            call = con.prepareCall(sql);
            call.setInt("id_pers", id_pers);
            
            resultado = call.executeQuery();
            while (resultado.next()) {
                lista.add(new asistencia(
                        new java.text.SimpleDateFormat("dd/MM/YYYY").format(resultado.getDate("FECHA")),
                        resultado.getInt("id_prac"),
                        resultado.getTime("HORA_DE_INGRESO").toString(),
                        resultado.getTime("HORA_DE_SALIDA") == null ? "--:--:--" : resultado.getTime("HORA_DE_SALIDA").toString(),
                        resultado.getString("OBSERVACION") == null ? "" : resultado.getString("OBSERVACION")
                ));
            }
            call.close();
        } catch (Exception e) {
            System.out.println("Error en el metodo ListaAsistenciaTotalPracticante de la clase asistenciaDAO detalles :"+e.getMessage());
        }
        return lista;
    }

    //retona HTML de la historia de asistencia de un salumno xxx
    public String ListaAsistenciaPracticanteHTML(int id_practicante,String fecha_start,String fecha_end){
    String sql="{call SP_V_HISTORIA_AISTENCIA_PRACTICANTE(?,?,?)}";
        StringBuilder HTML=new StringBuilder();
        try {
            call=con.prepareCall(sql);
            call.setString("FECHA_START", fecha_start);
            call.setString("FECHA_END", fecha_end);
            call.setInt("PRACTICANTE", id_practicante);
            resultado=call.executeQuery();
            while (resultado.next()) {
            
                HTML.append("<div class=\"row\">");
                
                HTML.append("<div class=\"col col-lg-1\">");
                HTML.append(String.format("<p>%s</p>", new java.text.SimpleDateFormat("dd-MM-YYYY").format(resultado.getDate("FECHA"))));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-3\">");
                HTML.append(String.format("<p>%s</p>",resultado.getString("PRACTICANTE") ));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", new java.text.SimpleDateFormat("hh:mm:ss").format(resultado.getTime("HORA_DE_INGRESO"))));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>",resultado.getTime("HORA_DE_SALIDA")==null?"--:--:--":new java.text.SimpleDateFormat("hh:mm:ss").format(resultado.getTime("HORA_DE_SALIDA"))));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-4\">");
                HTML.append(String.format("<p>%s</p>",resultado.getString("OBSERVACION")==null?"":resultado.getString("OBSERVACION")));
                HTML.append("</div>");
                
                HTML.append("</div>");
            }
            call.close();
        } catch (Exception e) {
              System.out.println("Error en el metodo ListaAsistenciaPracticanteHTML de la clase asistenciaDAO detalles :"+e.getMessage());
        }
        return HTML.toString();
    }
    
    //retsorna un mapa con el id del practicante y sus apellidos y nombres
    public Map<Integer,String> listaPracticante(){
    String sql="select P.id_prac,V.apel_pers+' '+nomb_pers as PRACTICANTES from V_PERSONA AS V inner join PRACTICANTE AS P on V.id_pers=P.id_pers;";
    Map<Integer,String> mapa=new HashMap<>();
        try {
            statment=con.createStatement();
            resultado=statment.executeQuery(sql);
            while(resultado.next()){
                mapa.put(resultado.getInt("id_prac"), resultado.getString("PRACTICANTES"));
            }
            statment.close();
        } catch (Exception e) {
              System.out.println("Error en el metodo listaPracticante de la clase asistenciaDAO detalles :"+e.getMessage());
        }
        return mapa;
    }
}
