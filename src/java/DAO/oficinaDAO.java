package DAO;

import MODELO.oficina;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class oficinaDAO {

    private Connection con;
    private Statement statment;
    private CallableStatement call;
    private ResultSet resultado;

    public oficinaDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public Map<Integer, String> listaOficina() {
        String sql = "SELECT id_ofic,nomb_ofic FROM V_OFICINA";
        Map<Integer, String> mapa = new HashMap<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                mapa.put(resultado.getInt("id_ofic"), resultado.getString("nomb_ofic"));
            }
            statment.close();
        } catch (Exception e) {
        }
        return mapa;
    }

    public oficina detallesOficina(int id, String nombre) {
        String sql;
        if (nombre != null) {
            sql = String.format("select* from V_OFICINA where nomb_ofic='%s'", nombre);
        } else {
            sql = String.format("select* from V_OFICINA where id_ofic=%d", id);
        }
        oficina ofi = new oficina();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            if (resultado.next()) {
                ofi.setId_oficina(resultado.getInt("id_ofic"));
                ofi.setOficina(resultado.getString("nomb_ofic"));
                ofi.setEncargado(resultado.getString("enca_ofic"));
                ofi.setAbreviatura(resultado.getString("abreviatura_ofic"));
                ofi.setDetalles(resultado.getString("detalles_ofic"));
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en clase oficinaDAO metodo detallesOficina detalles :" + e.getMessage());
        }
        return ofi;
    }

    public ArrayList<oficina> listaPrincipalOficina() {
        String sql = "SELECT* FROM V_OFICINA";
        ArrayList<oficina> lista = new ArrayList<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                lista.add(new oficina(resultado.getInt("id_ofic"), resultado.getString("nomb_ofic"), resultado.getString("enca_ofic"), resultado.getString("abreviatura_ofic"), resultado.getString("detalles_ofic"), 1));
            }
            statment.close();
        } catch (Exception e) {
        }
        return lista;
    }

    public void insert_oficina(oficina ofi) {
        String sql = "{call SP_INSERT_OFICINA(?,?,?,?)}";
        try {
            call = con.prepareCall(sql);
            if (ofi.getEncargado() != null & !ofi.getEncargado().equals("")) {
                call.setString("encargado", ofi.getEncargado());
            } else {
                call.setNull("encargado", java.sql.Types.NULL);
            }
            if (ofi.getAbreviatura() != null & !ofi.getAbreviatura().equals("")) {
                call.setString("abreviatura", ofi.getAbreviatura());
            } else {
                call.setNull("abreviatura", java.sql.Types.NULL);
            }
            if (ofi.getDetalles() != null & !ofi.getDetalles().equals("")) {
                call.setString("detalles", ofi.getAbreviatura());
            } else {
                call.setNull("detalles", java.sql.Types.NULL);
            }
            call.setString("oficina", ofi.getOficina());
            call.execute();
            call.close();

        } catch (Exception e) {
            System.out.println("ivan error en clase oficinaDAO metodo insert_oficina detalles" + e.getMessage());
        }
    }

    public void update_oficina(oficina ofi) {
        String sql = "{call SP_UPDATE_OFICINA(?,?,?,?,?)}";
        try {
            call = con.prepareCall(sql);
            if (ofi.getEncargado() != null & !ofi.getEncargado().equals("")) {
                call.setString("encargado", ofi.getEncargado());
            } else {
                call.setNull("encargado", java.sql.Types.NULL);
            }
            if (ofi.getAbreviatura() != null & !ofi.getAbreviatura().equals("")) {
                call.setString("abreviatura", ofi.getAbreviatura());
            } else {
                call.setNull("abreviatura", java.sql.Types.NULL);
            }
            if (ofi.getDetalles() != null & !ofi.getDetalles().equals("")) {
                call.setString("detalles", ofi.getDetalles());
            } else {
                call.setNull("detalles", java.sql.Types.NULL);
            }
            call.setString("oficina", ofi.getOficina());
            call.setInt("id_oficina", ofi.getId_oficina());
            call.execute();
            call.close();

        } catch (Exception e) {
            System.out.println("ivan error en clase oficinaDAO metodo update_oficina detalles" + e.getMessage());
        }
    }

    //retorna contenido html sobre las oficinas cuyo nombre coincida o se asemeje en parte al nombre de oficina que pasamos como parametro al metodo
    public String OficinasHTML(String oficina) {
        ArrayList<oficina> lista = this.listaPrincipalOficina();
        StringBuilder HTML = new StringBuilder();
        for (oficina o : lista) {
            if (o.getOficina().contains(oficina)) {

                HTML.append("<div class=\"row\">");

                HTML.append("<div class=\"col col-lg-1\">");
                HTML.append(String.format("<p>%d</p>", o.getId_oficina()));
                HTML.append("</div>");

                HTML.append("<div class=\"col col-lg-4\">");
                HTML.append(String.format("<p>%s</p>", o.getOficina()));
                HTML.append("</div>");

                HTML.append("<div class=\"col col-lg-4\">");
                HTML.append(String.format("<p>%s</p>", o.getEncargado() == null ? "Sin definir" : o.getEncargado()));
                HTML.append("</div>");

                HTML.append("<div class=\"col col-lg-3\">");
                HTML.append(String.format("<p>%s</p>", o.getAbreviatura() == null ? "" : o.getAbreviatura()));
                HTML.append("</div>");

                HTML.append("</div>");
            }
        }
        return HTML.toString();
    }

    public void delete_oficina(int id) {
        String sql = "{call SP_DELETE_OFICINA(?)}";
        try {
            call = con.prepareCall(sql);
            call.setInt("id_oficina", id);
            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("ivan error en clase oficinaDAO() metodo delete_oficina detalles" + e.getMessage());
        }
    }
}
