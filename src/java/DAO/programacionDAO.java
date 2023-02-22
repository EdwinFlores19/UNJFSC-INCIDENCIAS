package DAO;

import MODELO.programacion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class programacionDAO {

    private Connection con;
    private Statement statment;
    private CallableStatement call;
    private ResultSet resultado;

    public programacionDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();

    }

    public List<Object[]> listaProgramacionFechaHoy() {
        String sql = "SELECT* FROM V_PROGRAMACION_FECHA_HOY ORDER BY id_progra DESC";
        List<Object[]> lista = new ArrayList<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                lista.add(new Object[]{resultado.getString("id_progra"), new java.text.SimpleDateFormat("dd/MM/YYYY").format(resultado.getDate("fecha_progra")), resultado.getString("programado por"), resultado.getString("tarea"), resultado.getString("nomb_ofic"),
                    resultado.getString("unidad"), resultado.getInt("id_progra_esta")});
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("Error en elñ metodo istaProgramacionFechaHoy() de clase programacionDAO detalles: " + e.getMessage());
        }
        return lista;
    }

    public Map<Integer, String> listaTarea() {
        String sql = "SELECT* FROM PROGRAMACION_TAREA";
        Map<Integer, String> mapa = new HashMap<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                mapa.put(resultado.getInt("id_progra_tarea"), resultado.getString("tarea"));
            }
            statment.close();
        } catch (Exception e) {
        }
        return mapa;
    }

    public void insert_programacion(programacion objProgramacion, int id_pers) {
        String sql = "{call SP_INSERT_PROGRAMACION(?,?,?,?,?,?,?,?)}";
        try {
            call = con.prepareCall(sql);

            call.setString("FECHA", objProgramacion.getFecha_progra());
            call.setInt("PERSONA", id_pers);
            call.setInt("PROGRA_TAREA", objProgramacion.getId_progra_tarea());

            if (objProgramacion.getProgra_tarea_deta() != null && !objProgramacion.getProgra_tarea_deta().equals("")) {
                call.setString("PROGRA_TAREA_DETA", objProgramacion.getProgra_tarea_deta());
            } else {
                call.setNull("PROGRA_TAREA_DETA", java.sql.Types.NULL);
            }

            call.setInt("OFICINA", objProgramacion.getId_oficina());
            call.setInt("UNIDAD", objProgramacion.getId_unidad());
            call.setString("ENCARGADO", objProgramacion.getEncargado());
            if (objProgramacion.getComentario() != null && !objProgramacion.getComentario().equals("")) {
                call.setString("COMENTARIO", objProgramacion.getComentario());
            } else {
                call.setNull("COMENTARIO", java.sql.Types.NULL);
            }

            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("Error en elñ metodo insert_programacion() de clase programacionDAO detalles: " + e.getMessage());
        }
    }

    public void update_programacion(programacion objProgramacion) {
        String sql = "{call SP_UPDATE_PROGRAMACION(?,?,?,?,?,?,?)}";
        try {
            call = con.prepareCall(sql);
            call.setString("ID_PROGRAMACION", objProgramacion.getId_progra());
            call.setInt("ID_TAREA", objProgramacion.getId_progra_tarea());

            if (objProgramacion.getProgra_tarea_deta() != null && !objProgramacion.getProgra_tarea_deta().equals("")) {
                call.setString("TAREA_DETALLE", objProgramacion.getProgra_tarea_deta());
            } else {
                call.setNull("TAREA_DETALLE", java.sql.Types.NULL);
            }

            call.setInt("OFICINA", objProgramacion.getId_oficina());
            call.setInt("UNIDAD", objProgramacion.getId_unidad());
            call.setString("ENCARGADO", objProgramacion.getEncargado());
            if (objProgramacion.getComentario() != null && !objProgramacion.getComentario().equals("")) {
                call.setString("COMENTARIO", objProgramacion.getComentario());
            } else {
                call.setNull("COMENTARIO", java.sql.Types.NULL);
            }

            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("Error en elñ metodo update_programacion() de clase programacionDAO detalles: " + e.getMessage());
        }
    }

    public void insertarProgramacionAtencion(String id_programacion, int id_persona) {
        String sql = "{call SP_INSERT_PROGRAMACION_ATENCION(?,?)}";
        try {
            call = con.prepareCall(sql);

            call.setString("PROGRAMACION", id_programacion);
            call.setInt("PERSONA", id_persona);

            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("error en el metodo insertarProgramacionAtencion() de la calse programacionDAO detralles: " + e.getMessage());
        }
    }

    public void cambiarEstadoProgramacion(String id_programacion, int id_estado) {
        String sql = "{call SP_CHANGE_ESTADO_PROGRAMACION(?,?)}";
        try {
            call = con.prepareCall(sql);

            call.setString("PROGRAMACION", id_programacion);
            call.setInt("ESTADO", id_estado);

            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("error en el metodo cambiarEstadoProgramacion() de la calse programacionDAO detralles: " + e.getMessage());
        }
    }

    public ResultSet ejecutarConsulta(String sql) {
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
        } catch (Exception e) {
            System.out.println("error en el metodo ejecutarConsulta() de la calse programacionDAO detralles: " + e.getMessage());
        }
        return resultado;
    }

    public Properties listaCantidadTareasxEstado(String fecha) {
        String sql = "{call SP_V_COUNT_PROGRAMACION_X_ESTADO(?)}";
        Properties prop = new Properties();
        try {
            call = con.prepareCall(sql);
            call.setString("FECHA", fecha);
            resultado = call.executeQuery();
            if (resultado.next()) {
                prop.put("PROCESO", resultado.getInt("PROCESO"));
                prop.put("PENDIENTE", resultado.getInt("PENDIENTE"));
                prop.put("FINALIZADO", resultado.getInt("FINALIZADO"));
                prop.put("CANCELADO", resultado.getInt("CANCELADO"));
            }
            call.close();
        } catch (Exception e) {
            System.out.println("Error en el metodo listaCantidadTareasxEstado() de la clase ProgramacionDAO detalles: " + e.getMessage());
        }
        return prop;
    }

    //RETORNA UN JSON que contiene la fecha el las etiquetas HTML <html>
    public String listaProgramacionHTML(int estado, String fecha) {
        String sql = "{call SP_V_PROGRAMACION_POR_FECHA(?,?)}";
        StringBuilder HTML = new StringBuilder();
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        Properties prop = listaCantidadTareasxEstado(fecha);
        try {
            if (con.isClosed()) {
                con = coneccionMSSQLSERVER.returnConnSQLServer();
            }
            call = con.prepareCall(sql);
            call.setInt("ESTADO", estado);
            call.setString("FECHA", fecha);
            resultado = call.executeQuery();
            while (resultado.next()) {
                HTML.append("<div class=\"row\">");

                HTML.append("<div class=\"col col-lg-1\">");
                HTML.append(String.format("<p>%s</p>", resultado.getString("id_progra")));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", new SimpleDateFormat("dd-MM-YYYY").format(resultado.getDate("fecha_progra"))));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", resultado.getString("REGISTRADOR")));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", resultado.getString("tarea")));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", resultado.getString("nomb_ofic")));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", resultado.getString("unidad")));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-1\">");
                HTML.append("<p>");
                switch (resultado.getInt("id_progra_esta")) {
                    case 1:
                        HTML.append("<button type=\"button\" class=\"btn btn-success\" title=\"Proceso. Pulsar Para Finalizar\">");
                        HTML.append("<span class=\"fa fa-refresh\"></span>");
                        HTML.append("</button>");
                        break;
                    case 2:
                        HTML.append("<button type=\"button\" class=\"btn btn-warning\" title=\"Pendiente. Pulsar para poner en proceso\">");
                        HTML.append("<span class=\"fa fa-warning\"></span>");
                        HTML.append("</button>");
                        break;
                    case 3:
                        HTML.append("<button type=\"button\" class=\"btn btn-danger\" title=\"Tarea Finalizada\">");
                        HTML.append("<span class=\"fa fa-check\"></span>");
                        HTML.append("</button>");
                        break;
                    case 4:
                        HTML.append("<button type=\"button\" class=\"btn btn-info\" title=\"Cancelado\">");
                        HTML.append("<span class=\"fa fa-times-circle-o\" style=\"font-size: 1.3em;\"></span>");
                        HTML.append("</button>");
                        break;
                }
                HTML.append("</p>");
                HTML.append("</div>");

                HTML.append("<div class='pop-atencion'>");
                HTML.append("<h6 class='pop-atencion-titulo'>Personal Enviado:</h6>");
                HTML.append("<ul class='pop-atencion-lista'></ul>");
                HTML.append("</div>");
                
                HTML.append("</div>");
            }
            call.close();
        } catch (Exception e) {
            System.out.println("Error en el metodo listaProgramacionHTML() de la clase programacionDAO detalles: " + e.getMessage());
        }

        JSON.addProperty("FECHA", fecha.substring(8) + "-" + fecha.substring(5, 7) + "-" + fecha.substring(0, 4));
        JSON.addProperty("PROGRAMACIONROW", HTML.toString());
        for (Enumeration e = prop.propertyNames(); e.hasMoreElements();) {
            Object llave = e.nextElement();
            JSON.addProperty(llave.toString(), prop.get(llave).toString());
        }
        return JSON.toString();
    }

    public programacion returnProgramacion(String id_progra) {
        String sql = String.format("SELECT* FROM V_PROGRAMACION WHERE id_progra='%s'", id_progra);
        programacion objProgramacion = new programacion();
        try {
            if (con.isClosed()) {
                con = coneccionMSSQLSERVER.returnConnSQLServer();
            }
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                objProgramacion.setId_progra(resultado.getString("id_progra"));
                objProgramacion.setFecha_progra(resultado.getString("fecha_progra"));
                objProgramacion.setId_persona(resultado.getInt("id_pers"));
                objProgramacion.setId_progra_tarea(resultado.getInt("id_progra_tarea"));
                objProgramacion.setProgra_tarea_deta(resultado.getString("progra_tarea_deta"));
                objProgramacion.setId_oficina(resultado.getInt("id_ofic"));
                objProgramacion.setId_unidad(resultado.getInt("id_unidad"));
                objProgramacion.setId_progra_estado(resultado.getInt("id_progra_esta"));
                objProgramacion.setEncargado(resultado.getString("encargado"));
                objProgramacion.setComentario(resultado.getString("comentario"));
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en el metodo returnProgramacion() en la clase programacionDAO mas detalles: " + e.getMessage());
        }
        return objProgramacion;
    }

    public String listaUnidadxOficinaHTML(int id_oficina) {
        String cadena = "SELECT id_unidad,unidad FROM V_UNIDAD WHERE id_ofic=%d";
        String sql = String.format(cadena, id_oficina);
        StringBuilder html = new StringBuilder();
        try {
            if (con.isClosed()) {
                con = coneccionMSSQLSERVER.returnConnSQLServer();
            }
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                html.append("<option value='" + resultado.getInt("id_unidad") + "'>");
                html.append(resultado.getString("unidad"));
                html.append("</option>");
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en el metodo listaUnidadxOficinaHTML() de la clase programacionDAO detalles: " + e.getMessage());
        }
        return html.toString();
    }
}
