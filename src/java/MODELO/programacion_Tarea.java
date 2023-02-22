
package MODELO;


public class programacion_Tarea {
    
    private int id_progra_tarea;
    private String tarea;

    public programacion_Tarea() {
    }

    public programacion_Tarea(int id_progra_tarea, String tarea) {
        this.id_progra_tarea = id_progra_tarea;
        this.tarea = tarea;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public int getId_progra_tarea() {
        return id_progra_tarea;
    }

    public void setId_progra_tarea(int id_progra_tarea) {
        this.id_progra_tarea = id_progra_tarea;
    }
    
    
}
