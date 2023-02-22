package DAO;

import MODELO.persona;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Service;

@Service
public class personaDAO {

    private Connection con;
    private Statement statment;
    private CallableStatement call;
    private ResultSet resultado;

    public personaDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public List<persona> listaPrincipalPersona() {
        String sql = "SELECT* FROM V_PERSONA_CARGO";
        List<persona> lista = new ArrayList<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                lista.add(new persona(resultado.getInt("id_pers"), resultado.getString("apel_pers"), resultado.getString("nomb_pers"),
                        resultado.getString("dni_pers"), null, resultado.getString("tele_pers"), null, null, null, resultado.getString("cargo"), null));
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en el metodo  listaPrincipalPersona() de la clase personaDAO");
        }
        return lista;
    }

    public boolean insertarPersona(persona p) {
        String sql = "{call SP_INSERT_PERSONA(?,?,?,?,?,?,?,?,?)}";
        boolean bool = false;
        try {
            call = con.prepareCall(sql);
            call.setString("apellidos", p.getApellidos());
            call.setString("nombres", p.getNombres());
            call.setString("dni", p.getDni());
            call.setString("sexo", p.getSexo());
            if (p.getTelefono() != null & !p.getTelefono().equals("")) {
                call.setString("telefono", p.getTelefono());
            } else {
                call.setNull("telefono", java.sql.Types.NULL);
            }
            if (p.getHorario() != null & !p.getHorario().equals("")) {
                call.setString("horario", p.getHorario());
            } else {
                call.setString("horario", "1,2,3,4,5");
            }
            if (p.getDireccion() != null & !p.getDireccion().equals("")) {
                call.setString("direccion", p.getDireccion());
            } else {
                call.setNull("direccion", java.sql.Types.NULL);
            }
            if (p.getCorreo() != null & !p.getCorreo().equals("")) {
                call.setString("correo", p.getCorreo());
            } else {
                call.setNull("correo", java.sql.Types.NULL);
            }
            call.setInt("cargo", Integer.parseInt(p.getCargo()));
            call.execute();
            bool = true;
            call.close();
        } catch (Exception e) {
            System.out.println("ivan error em insertPewrsona detalles:" + e.getMessage());
        }
        return bool;
    }

    public boolean actualizarPersona(persona p) {
        String sql = "{call SP_UPDATE_PERSONA(?,?,?,?,?,?,?,?,?)}";
        boolean bool = false;
        try {
            call = con.prepareCall(sql);
            call.setInt("id_persona", p.getId_persona());
            call.setString("apellidos", p.getApellidos());
            call.setString("nombres", p.getNombres());
            call.setString("dni", p.getDni());
            call.setString("sexo", p.getSexo());
            if (p.getTelefono() != null || !p.getTelefono().equals("")) {
                call.setString("telefono", p.getTelefono());
            } else {
                call.setNull("telefono", java.sql.Types.NULL);
            }
            if (p.getHorario() != null || !p.getHorario().equals("")) {
                call.setString("horario", p.getHorario());
            } else {
                call.setString("horario", "1,2,3,4,5");
            }
            if (p.getDireccion() != null || !p.getDireccion().equals("")) {
                call.setString("direccion", p.getDireccion());
            } else {
                call.setNull("direccion", java.sql.Types.NULL);
            }
            if (p.getCorreo() != null || !p.getCorreo().equals("")) {
                call.setString("correo", p.getCorreo());
            } else {
                call.setNull("correo", java.sql.Types.NULL);
            }
            call.execute();
            bool = true;
            call.close();
        } catch (Exception e) {
            System.out.println("ivan error em insertPewrsona detalles:" + e.getMessage());
        }
        return bool;
    }

    public persona detallesPersona(int id) {
        persona p = new persona();
        String sql = String.format("SELECT* FROM V_PERSONA_CARGO_DETALLADO WHERE id_pers=%d", id);
        resultado = null;
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            if (resultado.next()) {
                p.setId_persona(resultado.getInt("id_pers"));
                p.setApellidos(resultado.getString("apel_pers"));
                p.setNombres(resultado.getString("nomb_pers"));
                p.setDni(resultado.getString("dni_pers"));
                p.setSexo(resultado.getString("sexo_pers"));
                p.setTelefono(resultado.getString("tele_pers"));
                p.setHorario(resultado.getString("horar_pers"));
                p.setDireccion(resultado.getString("direc_pers"));
                p.setCorreo(resultado.getString("correo"));
                p.setCargo(resultado.getString("cargo"));
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("Ivan Error en el metodo detallesPersona() de la clace personaDAO");
        }
        return p;
    }

    public boolean eliminarPersona(int codigo) {
        String sql = "{call SP_DELETE_PERSONA(?)}";
        boolean bool = false;
        try {
            call = con.prepareCall(sql);
            call.setInt("id_persona", codigo);
            call.execute();
            bool = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return bool;
    }

    public String horario(String horario) {
        StringBuilder cadena = new StringBuilder();
        cadena.append("<ul class='list-group'>");
        cadena.append("<li class='list-group-item disabled'>Disponibilidad</li>");
        if (horario.contains("1")) {
            cadena.append("<li class='list-group-item'>");
            cadena.append("Lunes");
            cadena.append("</li>");
        }
        if (horario.contains("2")) {
            cadena.append("<li class='list-group-item'>");
            cadena.append("Martes");
            cadena.append("</li>");
        }
        if (horario.contains("3")) {
            cadena.append("<li class='list-group-item'>");
            cadena.append("Miercoles");
            cadena.append("</li>");
        }
        if (horario.contains("4")) {
            cadena.append("<li class='list-group-item'>");
            cadena.append("Jueves");
            cadena.append("</li>");
        }
        if (horario.contains("5")) {
            cadena.append("<li class='list-group-item'>");
            cadena.append("Viernes");
            cadena.append("</li>");
        }
        cadena.append("</ul>");

        return cadena.toString();
    }

    public String vPrincipalPersonaHTML() {
        //ESTE METODO ES IMPORTANTE YA QUE CUANDO REALIZAMOS SOLICITUDES AJAX RETORNAMOS HTML PARA MOSTRAR LA LISTA ACTUALIZADA DE PERSONAS  
        List<persona> lista = listaPrincipalPersona();
        StringBuilder html = new StringBuilder();
        for (persona p : lista) {
            html.append("<div class='row'>");
            html.append("<div class='col-lg-1'><p>");
            html.append(p.getId_persona());
            html.append("</p></div>");
            html.append("<div class='col-lg-2'><p>");
            html.append(p.getApellidos());
            html.append("</p></div>");
            html.append("<div class='col-lg-3'><p>");
            html.append(p.getNombres());
            html.append("</p></div>");
            html.append("<div class='col-lg-2'><p>");
            html.append(p.getDni());
            html.append("</p></div>");
            html.append("<div class='col-lg-2'><p>");
            html.append(p.getTelefono());
            html.append("</p></div>");
            html.append("<div class='col-lg-2'><p>");
            html.append(p.getCargo());
            html.append("</p></div>");
            html.append("</div>");
        }
        return html.toString();
    }

    public String listaPersonalPorCargoHTML(int cargo) {
        StringBuilder HTML = new StringBuilder();
        List<persona> lista = listaPrincipalPersona();

        switch (cargo) {
            case 1:
                for (persona p : lista) {
                    if (p.getCargo().equalsIgnoreCase("PRACTICANTE")) {
                        HTML.append("<div class='row'>");
                        HTML.append("<div class='col-lg-1'><p>");
                        HTML.append(p.getId_persona());
                        HTML.append("</p></div>");
                        HTML.append("<div class='col-lg-2'><p>");
                        HTML.append(p.getApellidos());
                        HTML.append("</p></div>");
                        HTML.append("<div class='col-lg-3'><p>");
                        HTML.append(p.getNombres());
                        HTML.append("</p></div>");
                        HTML.append("<div class='col-lg-2'><p>");
                        HTML.append(p.getDni());
                        HTML.append("</p></div>");
                        HTML.append("<div class='col-lg-2'><p>");
                        HTML.append(p.getTelefono()==null?"":p.getTelefono());
                        HTML.append("</p></div>");
                        HTML.append("<div class='col-lg-2'><p>");
                        HTML.append(p.getCargo());
                        HTML.append("</p></div>");
                        HTML.append("</div>");
                    }
                }
                break;
            case 2:
                for (persona p : lista) {
                    if (p.getCargo().equalsIgnoreCase("CONTRATADO")) {
                        HTML.append("<div class='row'>");
                        HTML.append("<div class='col-lg-1'><p>");
                        HTML.append(p.getId_persona());
                        HTML.append("</p></div>");
                        HTML.append("<div class='col-lg-2'><p>");
                        HTML.append(p.getApellidos());
                        HTML.append("</p></div>");
                        HTML.append("<div class='col-lg-3'><p>");
                        HTML.append(p.getNombres());
                        HTML.append("</p></div>");
                        HTML.append("<div class='col-lg-2'><p>");
                        HTML.append(p.getDni());
                        HTML.append("</p></div>");
                        HTML.append("<div class='col-lg-2'><p>");
                        HTML.append(p.getTelefono()==null?"":p.getTelefono());
                        HTML.append("</p></div>");
                        HTML.append("<div class='col-lg-2'><p>");
                        HTML.append(p.getCargo());
                        HTML.append("</p></div>");
                        HTML.append("</div>");
                    }
                }
                break;
            case 0:
                for (persona p : lista) {

                    HTML.append("<div class='row'>");
                    HTML.append("<div class='col-lg-1'><p>");
                    HTML.append(p.getId_persona());
                    HTML.append("</p></div>");
                    HTML.append("<div class='col-lg-2'><p>");
                    HTML.append(p.getApellidos());
                    HTML.append("</p></div>");
                    HTML.append("<div class='col-lg-3'><p>");
                    HTML.append(p.getNombres());
                    HTML.append("</p></div>");
                    HTML.append("<div class='col-lg-2'><p>");
                    HTML.append(p.getDni());
                    HTML.append("</p></div>");
                    HTML.append("<div class='col-lg-2'><p>");
                    HTML.append(p.getTelefono()==null?"":p.getTelefono());
                    HTML.append("</p></div>");
                    HTML.append("<div class='col-lg-2'><p>");
                    HTML.append(p.getCargo());
                    HTML.append("</p></div>");
                    HTML.append("</div>");
                }
                break;

        }

        return HTML.toString();
    }
}
