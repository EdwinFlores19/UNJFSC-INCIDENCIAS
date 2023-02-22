package MODELO;

public class incidenciaMedio {

    private int id_medio;
    private String medio;

    public incidenciaMedio() {
    }

    public incidenciaMedio(int id_medio, String medio) {
        this.id_medio = id_medio;
        this.medio = medio;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public int getId_medio() {
        return id_medio;
    }

    public void setId_medio(int id_medio) {
        this.id_medio = id_medio;
    }

    
}
