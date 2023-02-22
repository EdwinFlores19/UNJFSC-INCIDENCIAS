package DAO;

import MODELO.incidencia;
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

public class incidenciaDAO {

    private Connection con;
    private Statement statment;
    private CallableStatement call;
    private ResultSet resultado;

    public incidenciaDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public List<String[]> listaPrincipalIncidencias() {
        String sql = "SELECT* FROM V_INCIDENCIA_PRINCIPAL_FECHA_ACTUAL ORDER BY fecha_regi_inci DESC";
        List<String[]> lista = new ArrayList<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                lista.add(new String[]{String.valueOf(resultado.getString("id_inci")),
                    new SimpleDateFormat("dd-MM-YYYY").format(resultado.getDate("fecha_regi_inci")) + ' ' + new SimpleDateFormat("hh:mm:ss").format(resultado.getTime("fecha_regi_inci")),
                    resultado.getString("REGISTRADOR"),
                    resultado.getString("inci_deta") != null ? resultado.getString("inci_deta") : resultado.getString("inicidencia"),
                    resultado.getString("nomb_ofic"),
                    resultado.getString("unidad"),
                    String.valueOf(resultado.getInt("id_inci_esta"))}
                );
            }
            statment.close();
        } catch (Exception e) {
        }
        return lista;
    }

    public Properties listaCantidadIncidenciaxEstado(String fecha) {

        String sql = "{call SP_V_COUNT_INCIDENCIA_X_ESTADO(?)}";
        Properties prop = new Properties();
        try {
            call = con.prepareCall(sql);
            call.setString("FECHA", fecha);
            resultado = call.executeQuery();
            if (resultado.next()) {
                prop.put("PROCESO", resultado.getInt("PROCESO"));
                prop.put("ESPERA", resultado.getInt("ESPERA"));
                prop.put("FINALIZADO", resultado.getInt("FINALIZADO"));
                prop.put("CANCELADO", resultado.getInt("CANCELADO"));
            }
            call.close();
        } catch (Exception e) {
            System.out.println("Error en el metodo listaCantidadIncidenciaxEstado() de la clase incidenciaDAO detalles: " + e.getMessage());
        }
        return prop;
    }

    //retorna codigo HTML de opciones en una cadena String 
    public String listaUnidadxOficinaHTML(int id_oficina) {
        String cadena = "SELECT id_unidad,unidad FROM V_UNIDAD WHERE id_ofic=%d";
        String sql = String.format(cadena, id_oficina);
        StringBuilder html = new StringBuilder();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                html.append("<option value='" + resultado.getInt("id_unidad") + "'>");
                html.append(resultado.getString("unidad"));
                html.append("</option>");
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en el metodo listaUnidadxOficinaHTML() de la clase incidenciaDAO detalles: " + e.getMessage());
        }
        return html.toString();
    }

    public void insertarIncidencia(incidencia I, int id_pers) {
        String sql = "{call SP_INSERT_INCIDENCIA(?,?,?,?,?,?,?,?,?)}";
        try {
            call = con.prepareCall(sql);

            call.setInt("id_persona", id_pers);
            call.setInt("id_inci_deta", I.getId_inci_detalle());

            if (I.getInci_detalle() != null && !I.getInci_detalle().equals("")) {
                call.setString("inci_deta", I.getInci_detalle());
            } else {
                call.setNull("inci_deta", java.sql.Types.NULL);
            }

            call.setInt("id_oficina", I.getId_oficina());
            call.setInt("id_unidad", I.getId_unidad());
            call.setInt("id_inci_esta", 2);
            call.setString("encargado", I.getEncargado());
            call.setInt("id_inci_medio", I.getId_inci_medio());

            if (I.getComentario() != null && !I.getComentario().equals("")) {
                call.setString("comentario", I.getComentario());
            } else {
                call.setNull("comentario", java.sql.Types.NULL);
            }

            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("error en el metodo insertarIncidencia() de la calse incidenciaDAO detralles: " + e.getMessage());
        }
    }

    public void updateIncidencia(incidencia I) {
        String sql = "{call SP_UPDATE_INCIDENCIA(?,?,?,?,?,?,?,?)}";
        try {
            call = con.prepareCall(sql);

            call.setString("id_inci", I.getId_inci());
            call.setInt("id_inci_deta", I.getId_inci_detalle());

            if (I.getInci_detalle() != null && !I.getInci_detalle().equals("")) {
                call.setString("inci_deta", I.getInci_detalle());
            } else {
                call.setNull("inci_deta", java.sql.Types.NULL);
            }

            call.setInt("id_oficina", I.getId_oficina());
            call.setInt("id_unidad", I.getId_unidad());
            call.setString("encargado", I.getEncargado());
            call.setInt("id_inci_medio", I.getId_inci_medio());

            if (I.getComentario() != null && !I.getComentario().equals("")) {
                call.setString("comentario", I.getComentario());
            } else {
                call.setNull("comentario", java.sql.Types.NULL);
            }

            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("error en el metodo  updateIncidencia() de la calse incidenciaDAO detralles: " + e.getMessage());
        }
    }

    public incidencia detallesIncidencia(String id) {
        incidencia I = new incidencia();
        String sql = String.format("SELECT * FROM INCIDENCIA WHERE id_inci='%s'", id);
        resultado = null;
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            if (resultado.next()) {
                I.setId_inci(resultado.getString("id_inci"));
                I.setFecha(new SimpleDateFormat("YYYY-MM-dd").format(resultado.getDate("fecha_regi_inci")) + " " + new SimpleDateFormat("hh:mm:ss").format(resultado.getTime("fecha_regi_inci")));
                I.setId_pers(resultado.getInt("id_pers"));
                I.setId_inci_detalle(resultado.getInt("id_inci_deta"));
                I.setInci_detalle(resultado.getString("inci_deta"));
                I.setId_oficina(resultado.getInt("id_ofic"));
                I.setId_unidad(resultado.getInt("id_unidad"));
                I.setId_inci_estado(resultado.getInt("id_inci_esta"));
                I.setEncargado(resultado.getString("encargado"));
                I.setId_inci_medio(resultado.getInt("id_inci_medio"));
                I.setComentario(resultado.getString("comentario"));
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("error en el metodo  detallesIncidencia() de la calse incidenciaDAO detralles: " + e.getMessage());
        }
        return I;
    }

    public Map<Integer, String> listaPersonaDisponibleHoy() {
        String sql = "SELECT* FROM V_PERSONAL_DISPONIBLE_HOY";
        Map<Integer, String> mapa = new HashMap<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                mapa.put(resultado.getInt("id_pers"), resultado.getString("PERSONA"));
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("error en el metodo listapPracticanteAndContratados() de la calse incidenciaDAO detralles: " + e.getMessage());
        }
        return mapa;
    }

    public void insertarIncidenciaAtencion(String id_incidencia, int id_persona) {
        String sql = "{call SP_INSERT_INCIDENCIA_ATENCION(?,?)}";
        try {
            call = con.prepareCall(sql);

            call.setString("INCIDENCIA", id_incidencia);
            call.setInt("PERSONA", id_persona);

            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("error en el metodo insertarIncidenciaAtencion() de la calse incidenciaDAO detralles: " + e.getMessage());
        }
    }

    public void cambiarEstadoIncidencia(String id_incidencia, int id_estado) {
        String sql = "{call SP_UPDATE_INCIDENCIA_ESTADO(?,?)}";
        try {
            call = con.prepareCall(sql);

            call.setString("INCIDENCIA", id_incidencia);
            call.setInt("estado", id_estado);

            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("error en el metodo cambiarEstadoIncidencia() de la calse incidenciaDAO detralles: " + e.getMessage());
        }
    }

    public boolean cancelarIncidencia(String id_incidencia) {
        String cadena = "select * from dbo.V_INCIDENCIA_PRINCIPAL_FECHA_ACTUAL"
                + " where id_inci='%s' and id_inci_esta not between 3 and 4";
        String sql = String.format(cadena, id_incidencia);
        boolean bol = false;
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            if (resultado.next()) {
                cambiarEstadoIncidencia(id_incidencia, 4);
                bol = true;
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("error en el metodo cancelarIncidencia() de la calse incidenciaDAO detralles: " + e.getMessage());
        }
        return bol;
    }

    //RETORNA UN JSON que contiene la fecha el las etiquetas HTML <html>
    public String listaIncidenciasHTML(int estado, String fecha) {
        String sql = "{call SP_V_INCIDENCIA_POR_FECHA(?,?)}";
        StringBuilder HTML = new StringBuilder();
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        Properties prop = listaCantidadIncidenciaxEstado(fecha);
        try {
            call = con.prepareCall(sql);
            call.setInt("ESTADO", estado);
            call.setString("FECHA", fecha);
            resultado = call.executeQuery();
            while (resultado.next()) {
                HTML.append("<div class=\"row\">");

                HTML.append("<div class=\"col col-lg-1\">");
                HTML.append(String.format("<p>%s</p>", resultado.getString("id_inci")));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", new SimpleDateFormat("dd-MM-YYYY").format(resultado.getDate("fecha_regi_inci")) + ' ' + new SimpleDateFormat("hh:mm:ss").format(resultado.getTime("fecha_regi_inci"))));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", resultado.getString("REGISTRADOR")));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", resultado.getString("inci_deta") != null ? resultado.getString("inci_deta") : resultado.getString("inicidencia")));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", resultado.getString("nomb_ofic")));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-2\">");
                HTML.append(String.format("<p>%s</p>", resultado.getString("unidad")));
                HTML.append("</div>");
                HTML.append("<div class=\"col col-lg-1\">");
                HTML.append("<p>");
                switch (resultado.getInt("id_inci_esta")) {
                    case 1:
                        HTML.append("<button type=\"button\" class=\"btn btn-success\" title=\"Proceso. Pulsar Para Finalizar\">");
                        HTML.append("<span class=\"fa fa-refresh\"></span>");
                        HTML.append("</button>");
                        break;
                    case 2:
                        HTML.append("<button type=\"button\" class=\"btn btn-warning\" title=\"En Espera. Pulsar para poner en proceso\">");
                        HTML.append("<span class=\"fa fa-warning\"></span>");
                        HTML.append("</button>");
                        break;
                    case 3:
                        HTML.append("<button type=\"button\" class=\"btn btn-danger\" title=\"Finalizado\">");
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
            System.out.println("Error en el metodo listaIncidenciasHTML() de la clase incidenciaDAO detalles: " + e.getMessage());
        }

        JSON.addProperty("FECHA", fecha.substring(8) + "-" + fecha.substring(5, 7) + "-" + fecha.substring(0, 4));
        JSON.addProperty("INCIDENCIASROW", HTML.toString());
        for (Enumeration e = prop.propertyNames(); e.hasMoreElements();) {
            Object llave = e.nextElement();
            JSON.addProperty(llave.toString(), prop.get(llave).toString());
        }
        return JSON.toString();
    }

    public void reconciderar_incidencia_finalizada(String id_incidencia) {
        String sql = "{call SP_RECONCIDERAR_INCIDENCIA_FINALIZADA(?)}";
        try {
            call = con.prepareCall(sql);
            call.setString("INCIDENCIA", id_incidencia);
            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("Ivan error en el metodo reconciderar_incidencia_finalizada() de la clase incidenciaDAO detalles: " + e.getMessage());
        }

    }

    //RETORNA EL NOMBRE DE LA PERSONA QUE SOLICITO EL SERVICIO(USUARIO FINAL)
    public String name_usuario_solicitante(String id_incidencia) {
        String sql = String.format("SELECT encargado FROM INCIDENCIA WHERE id_inci= '%s'", id_incidencia);
        String name = "";
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            if (resultado.next()) {
                name = resultado.getString("encargado");
            }
        } catch (Exception e) {
            System.out.println("ivan error en el metodo name_usuario_solicitante() de la clase incidenciaDAO detalles: " + e.getMessage());
        }
        return name;
    }
}
