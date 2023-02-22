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
public class V_PROGRAMACION_ATENCION {

    private String id_progra;
    private int id_pers;
    private String nombres;

    public V_PROGRAMACION_ATENCION() {
    }

    public V_PROGRAMACION_ATENCION(String id_progra, int id_pers, String nombres) {
        this.id_progra = id_progra;
        this.id_pers = id_pers;
        this.nombres = nombres;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getId_progra() {
        return id_progra;
    }

    public void setId_progra(String id_progra) {
        this.id_progra = id_progra;
    }

    public int getId_pers() {
        return id_pers;
    }

    public void setId_pers(int id_pers) {
        this.id_pers = id_pers;
    }

}
