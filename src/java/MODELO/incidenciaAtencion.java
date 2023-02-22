/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELO;

/**
 *
 * @author P3017OSI
 */
public class incidenciaAtencion {

    private String id_inci;
    private int id_pers;
    private String persona;//esta atributo no es una columna de la tabla incidencia atencion en la base de datos

    public incidenciaAtencion() {
    }

    public incidenciaAtencion(String id_inci, int id_pers, String persona) {
        this.id_inci = id_inci;
        this.id_pers = id_pers;
        this.persona = persona;
    }

    public int getId_pers() {
        return id_pers;
    }

    public void setId_pers(int id_pers) {
        this.id_pers = id_pers;
    }

    public String getId_inci() {
        return id_inci;
    }

    public void setId_inci(String id_inci) {
        this.id_inci = id_inci;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

}
