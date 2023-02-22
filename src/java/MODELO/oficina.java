package MODELO;

public class oficina {

    private int id_oficina;
    private String oficina;
    private String encargado;
    private String abreviatura;
    private String detalles;
    private int estado;

    public oficina() {
    }

    public oficina(int id_oficina, String oficina, String encargado, String abreviatura, String detalles,int estado) {
        this.id_oficina = id_oficina;
        this.oficina = oficina;
        this.encargado = encargado;
        this.abreviatura = abreviatura;
        this.detalles = detalles;
        this.estado = estado;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public int getId_oficina() {
        return id_oficina;
    }

    public void setId_oficina(int id_oficima) {
        this.id_oficina = id_oficima;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
}
