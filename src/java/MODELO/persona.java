package MODELO;

import com.google.gson.JsonArray;
import java.util.StringTokenizer;

public class persona {

    private int id_persona;
    private String apellidos;
    private String nombres;
    private String dni;
    private String sexo;
    private String telefono;
    private String horario;
    private String direccion;
    private String estado;
    private String cargo;
    private String correo;

    public persona() {
    }

    public persona(int id_persona, String apellidos, String nombres, String dni, String sexo, String telefono, String horario, String direccion, String estado, String cargo, String correo) {
        this.id_persona = id_persona;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.dni = dni;
        this.sexo = sexo;
        this.telefono = telefono;
        this.horario = horario;
        this.direccion = direccion;
        this.estado = estado;
        this.cargo = cargo;
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void horarioJSon() {
        StringTokenizer tokenizer=new StringTokenizer(this.horario,",");
        com.google.gson.JsonArray objArray=new JsonArray();
        while(tokenizer.hasMoreElements()){
        objArray.add(tokenizer.nextToken());
        }
        
        this.horario=objArray.toString();
    }
}
