
package MODELO;

import java.util.Date;


public class FichaServicioTecnico {
    private String id_fst;
    private int id_periferico;
    private String periferico_detalle;
    private String cod_equipo;
    private String H_inicio;
    private String H_termino;
    private Date fecha;
    private String traslado;
    private String S_correctivo;
    private String Recomendacion;
    private String id_incidencia;

    public FichaServicioTecnico() {
    }

    public FichaServicioTecnico(String id_fst, int id_periferico, String periferico_detalle, String cod_equipo, String H_inicio, String H_termino, Date fecha, String traslado, String S_correctivo, String Recomendacion, String id_incidencia) {
        this.id_fst = id_fst;
        this.id_periferico = id_periferico;
        this.periferico_detalle = periferico_detalle;
        this.cod_equipo = cod_equipo;
        this.H_inicio = H_inicio;
        this.H_termino = H_termino;
        this.fecha = fecha;
        this.traslado = traslado;
        this.S_correctivo = S_correctivo;
        this.Recomendacion = Recomendacion;
        this.id_incidencia = id_incidencia;
    }

    public String getId_fst() {
        return id_fst;
    }

    public void setId_fst(String id_fst) {
        this.id_fst = id_fst;
    }

    public int getId_periferico() {
        return id_periferico;
    }

    public void setId_periferico(int id_periferico) {
        this.id_periferico = id_periferico;
    }

    public String getPeriferico_detalle() {
        return periferico_detalle;
    }

    public void setPeriferico_detalle(String periferico_detalle) {
        this.periferico_detalle = periferico_detalle;
    }

    public String getCod_equipo() {
        return cod_equipo;
    }

    public void setCod_equipo(String cod_equipo) {
        this.cod_equipo = cod_equipo;
    }

    public String getH_inicio() {
        return H_inicio;
    }

    public void setH_inicio(String H_inicio) {
        this.H_inicio = H_inicio;
    }

    public String getH_termino() {
        return H_termino;
    }

    public void setH_termino(String H_termino) {
        this.H_termino = H_termino;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTraslado() {
        return traslado;
    }

    public void setTraslado(String traslado) {
        this.traslado = traslado;
    }

    public String getS_correctivo() {
        return S_correctivo;
    }

    public void setS_correctivo(String S_correctivo) {
        this.S_correctivo = S_correctivo;
    }

    public String getRecomendacion() {
        return Recomendacion;
    }

    public void setRecomendacion(String Recomendacion) {
        this.Recomendacion = Recomendacion;
    }

    public String getId_incidencia() {
        return id_incidencia;
    }

    public void setId_incidencia(String id_incidencia) {
        this.id_incidencia = id_incidencia;
    }

    

  
}
