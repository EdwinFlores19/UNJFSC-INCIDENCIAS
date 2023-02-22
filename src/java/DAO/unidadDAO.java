package DAO;

import MODELO.unidad;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class unidadDAO {

    private Connection con;
    private Statement statment;
    private CallableStatement call;
    private ResultSet resultado;

    public unidadDAO() {
        this.con = coneccionMSSQLSERVER.returnConnSQLServer();
    }

    public void insert_unidad(unidad objunidad) {
        String sql = "{call SP_INSERT_UNIDAD(?,?,?,?)}";
        try {
            call = con.prepareCall(sql);
            call.setNull("ABREVIATURA", java.sql.Types.NULL);
            call.setNull("DETALLES", java.sql.Types.NULL);
            call.setString("UNIDAD", objunidad.getUnidad());
            call.setInt("OFICINA", objunidad.getId_oficina());
            call.execute();
            call.close();

        } catch (Exception e) {
            System.out.println("ivan error en clase unidadDAO() metodo insert_unidad detalles" + e.getMessage());
        }
    }

    public void update_unidad(unidad objunidad) {
        String sql = "{call SP_UPDATE_UNIDAD(?,?,?)}";
        try {
            call = con.prepareCall(sql);
            call.setString("UNIDAD", objunidad.getUnidad());
            call.setInt("OFICINA", objunidad.getId_oficina());
            call.setInt("CODIGO_UNIDAD", objunidad.getId_unidad());
            call.execute();
            call.close();

        } catch (Exception e) {
            System.out.println("ivan error en clase unidadDAO() metodo update_unidad detalles" + e.getMessage());
        }
    }

    public void delete_unidad(int id) {
        String sql = "{call SP_DESACTIVAR_UNIDAD(?)}";
        try {
            call = con.prepareCall(sql);
            call.setInt("UNIDAD", id);
            call.execute();
            call.close();
        } catch (Exception e) {
            System.out.println("ivan error en clase unidadDAO() metodo delete_unidad detalles" + e.getMessage());
        }
    }

    public List<Object[]> lista_unidad_oficina(int id_oficina ) {
        String sql = String.format("SELECT* FROM V_VISTA_OFICINA_UNIDAD WHERE id_ofic=%d ORDER BY UNIDAD DESC", id_oficina);
        List<Object[]> lista = new ArrayList<>();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            while (resultado.next()) {
                lista.add(new Object[]{resultado.getInt("id_unidad"), resultado.getString("nomb_ofic"), resultado.getString("unidad")});
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en clase unidadDAO() metodo lista_unidad_oficina() detalles" + e.getMessage());
        }
        return lista;
    }

    public unidad detallesUnidad(int id) {
        String sql = String.format("SELECT* FROM V_UNIDAD WHERE id_unidad=%d", id);
        unidad objUnidad = new unidad();
        try {
            statment = con.createStatement();
            resultado = statment.executeQuery(sql);
            if (resultado.next()) {
                objUnidad.setId_unidad(resultado.getInt("id_unidad"));
                objUnidad.setUnidad(resultado.getString("unidad"));
                objUnidad.setId_oficina(resultado.getInt("id_ofic"));
            }
            statment.close();
        } catch (Exception e) {
            System.out.println("ivan error en clase unidadDAO() metodo detallesUnidad() detalles" + e.getMessage());
        }
        return objUnidad;
    }
}
