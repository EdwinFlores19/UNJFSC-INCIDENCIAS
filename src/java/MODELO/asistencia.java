    
package MODELO;


public class asistencia {
   private String fecha;
   private int id_prac;
   private String hora_de_ingreso;
   private String hora_de_salida;
   private String observacion;

    public asistencia() {
    }

    public asistencia(String fecha, int id_prac, String hora_de_ingreso, String hora_de_salida, String observacion) {
        this.fecha = fecha;
        this.id_prac = id_prac;
        this.hora_de_ingreso = hora_de_ingreso;
        this.hora_de_salida = hora_de_salida;
        this.observacion = observacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_prac() {
        return id_prac;
    }

    public void setId_prac(int id_prac) {
        this.id_prac = id_prac;
    }

    public String getHora_de_ingreso() {
        return hora_de_ingreso;
    }

    public void setHora_de_ingreso(String hora_de_ingreso) {
        this.hora_de_ingreso = hora_de_ingreso;
    }

    public String getHora_de_salida() {
        return hora_de_salida;
    }

    public void setHora_de_salida(String hora_de_salida) {
        this.hora_de_salida = hora_de_salida;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
