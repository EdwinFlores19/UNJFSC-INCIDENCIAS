package MODELO;

public class incidencia {

    private String id_inci;
    private String fecha;
    private int id_pers; //Persona que registra la Incidencias
    private int id_inci_detalle;
    private String inci_detalle;
    private int id_oficina;
    private int id_unidad;
    private int id_inci_estado;
    private String encargado;
    private int id_inci_medio;
    private String comentario;

    public incidencia() {
    }

    public incidencia(String id_inci, String fecha, int id_pers, int id_inci_detalle, String inci_detalle, int id_oficina, int id_unidad, String encargado, int id_inci_medio, String comentario, int id_inci_estado) {
        this.id_inci = id_inci;
        this.fecha = fecha;
        this.id_pers = id_pers;
        this.id_inci_detalle = id_inci_detalle;
        this.inci_detalle = inci_detalle;
        this.id_oficina = id_oficina;
        this.id_unidad = id_unidad;
        this.encargado = encargado;
        this.id_inci_medio = id_inci_medio;
        this.comentario = comentario;
        this.id_inci_estado = id_inci_estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getId_inci() {
        return id_inci;
    }

    public void setId_inci(String id_inci) {
        this.id_inci = id_inci;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_pers() {
        return id_pers;
    }

    public void setId_pers(int id_pers) {
        this.id_pers = id_pers;
    }

    public int getId_inci_detalle() {
        return id_inci_detalle;
    }

    public void setId_inci_detalle(int id_inci_detalle) {
        this.id_inci_detalle = id_inci_detalle;
    }

    public String getInci_detalle() {
        return inci_detalle;
    }

    public void setInci_detalle(String inci_detalle) {
        this.inci_detalle = inci_detalle;
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

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public int getId_inci_medio() {
        return id_inci_medio;
    }

    public void setId_inci_medio(int id_inci_medio) {
        this.id_inci_medio = id_inci_medio;
    }

    public int getId_inci_estado() {
        return id_inci_estado;
    }

    public void setId_inci_estado(int id_inci_estado) {
        this.id_inci_estado = id_inci_estado;
    }

}
