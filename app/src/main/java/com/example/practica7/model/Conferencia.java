package com.example.practica7.model;

import com.google.firebase.Timestamp;


public class Conferencia {
    private Boolean enCurso;
    private Timestamp fecha; //Todo , revisar que timestamp necesito
    private String horario;
    private String id;
    private String nombre;
    private int plazas;
    private String ponente;
    private String sala;

    //****CONSTRUCTOR****//
    public Conferencia() { }
    public Conferencia(Boolean enCurso, Timestamp fecha, String horario, String id, String nombre, int plazas, String ponente, String sala) {
        this.enCurso = enCurso;
        this.fecha = fecha;
        this.horario = horario;
        this.id = id;
        this.nombre = nombre;
        this.plazas = plazas;
        this.ponente = ponente;
        this.sala = sala;
    }
    //****GETTER&SETTER****//

    public Boolean getEnCurso() {
        return enCurso;
    }
    public void setEnCurso(Boolean enCurso) {
        this.enCurso = enCurso;
    }
    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getPlazas() {
        return plazas;
    }
    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }
    public String getPonente() {
        return ponente;
    }
    public void setPonente(String ponente) {
        this.ponente = ponente;
    }
    public String getSala() {
        return sala;
    }
    public void setSala(String sala) {
        this.sala = sala;
    }
}
