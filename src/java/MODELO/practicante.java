
package MODELO;


public class practicante {
    
    private int id_prac;
    private int id_pers;
    private int horas_x_dia;
    private int total_dias_laborables;

    public practicante() {
    }

    public practicante(int id_prac, int id_pers, int horas_x_dia, int total_dias_laborables) {
        this.id_prac = id_prac;
        this.id_pers = id_pers;
        this.horas_x_dia = horas_x_dia;
        this.total_dias_laborables = total_dias_laborables;
    }

    public int getTotal_dias_laborables() {
        return total_dias_laborables;
    }

    public void setTotal_dias_laborables(int total_dias_laborables) {
        this.total_dias_laborables = total_dias_laborables;
    }

    public int getId_prac() {
        return id_prac;
    }

    public void setId_prac(int id_prac) {
        this.id_prac = id_prac;
    }

    public int getId_pers() {
        return id_pers;
    }

    public void setId_pers(int id_pers) {
        this.id_pers = id_pers;
    }

    public int getHoras_x_dia() {
        return horas_x_dia;
    }

    public void setHoras_x_dia(int horas_x_dia) {
        this.horas_x_dia = horas_x_dia;
    }
    
    
}
