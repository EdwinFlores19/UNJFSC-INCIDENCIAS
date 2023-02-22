
package MODELO;


public class unidad {
    
    private int id_unidad;
    private String unidad;
    private String abreviatura;
    private String detalles;
    private int id_oficina;
    private int estado;

    public unidad() {
    }

    public unidad(int id_unidad, String unidad, String abreviatura, String detalles, int id_oficina,int estado) {
        this.id_unidad = id_unidad;
        this.unidad = unidad;
        this.abreviatura = abreviatura;
        this.detalles = detalles;
        this.id_oficina = id_oficina;
        this.estado = estado;
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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
}
