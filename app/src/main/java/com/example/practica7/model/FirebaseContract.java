package com.example.practica7.model;

public class FirebaseContract {

    //********CLASE**INTERNA**EMPRESA********//
    public static class EmpresaEntry{
        public static final String NODE_NAME = "empresas";
        public static final String ID = "ochoa";
        public static final String NOMBRE = "nombre";
        public static final String DIRECCION = "direccion";
        public static final String TELEFONO = "telefono";
        public static final String LOCALIZACION = "localizacion";
    }

    public static class ConferenciaIniciadaEntry {
        public static final String COLLECTION_NAME = "conferenciaIniciada";
        public static final String ID= "conferenciainiciada";
        public static final String CONFERENCIA="conferencia";
    }


}
