
package MODELO;


public class programacion {
    
    private String id_progra;
    private String fecha_progra;
    private int id_persona;
    private int id_progra_tarea;
    private String progra_tarea_deta;
    private int id_oficina;
    private int id_unidad;
    private int id_progra_estado;
    private String encargado;
    private String comentario;

    public programacion() {
    }

    public programacion(String id_progra, String fecha_progra, int id_persona, int id_progra_tarea, String progra_tarea_deta, int id_oficina, int id_unidad, int id_progra_estado, String encargado, String comentario) {
        this.id_progra = id_progra;
        this.fecha_progra = fecha_progra;
        this.id_persona = id_persona;
        this.id_progra_tarea = id_progra_tarea;
        this.progra_tarea_deta = progra_tarea_deta;
        this.id_oficina = id_oficina;
        this.id_unidad = id_unidad;
        this.id_progra_estado = id_progra_estado;
        this.encargado = encargado;
        this.comentario = comentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getId_progra() {
        return id_progra;
    }

    public void setId_progra(String id_progra) {
        this.id_progra = id_progra;
    }

    public String getFecha_progra() {
        return fecha_progra;
    }

    public void setFecha_progra(String fecha_progra) {
        this.fecha_progra = fecha_progra;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public int getId_progra_tarea() {
        return id_progra_tarea;
    }

    public void setId_progra_tarea(int id_progra_tarea) {
        this.id_progra_tarea = id_progra_tarea;
    }

    public String getProgra_tarea_deta() {
        return progra_tarea_deta;
    }

    public void setProgra_tarea_deta(String progra_tarea_deta) {
        this.progra_tarea_deta = progra_tarea_deta;
    }

    public int getId_oficina() {
        return id_oficina;
    }

    public void setId_oficina(int id_oficina) {
        this.id_oficina = id_oficina;
    }

    public int getId_unidad() {
        return id_unidad;
    }

    public void setId_unidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }

    public int getId_progra_estado() {
        return id_progra_estado;
    }

    public void setId_progra_estado(int id_progra_estado) {
        this.id_progra_estado = id_progra_estado;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }
    
    
}
