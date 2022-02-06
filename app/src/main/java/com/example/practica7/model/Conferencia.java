package com.example.practica7.model;

import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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

    /*
    Este toString existe asi porque al ser el adaptador cargado con el arralist de objetos
    conferencia mostrara solo el nombre, No es lo más elegante del mundo pero funciona.
     */
    @Override
    public String toString() {
        return nombre ;
    }

    public String info() {
        return nombre + "\nFecha: "+
                getFechaEstaticaFormatoLocal(fecha.toDate())+"\n Horario: "+
                horario+"\n Sala: "+
                sala;
    }

    public static String getFechaEstaticaFormatoLocal(Date date){
        DateFormat df = DateFormat.getDateInstance(
                DateFormat.MEDIUM, Locale.getDefault()
        );
        return df.format(date);
    }
}
