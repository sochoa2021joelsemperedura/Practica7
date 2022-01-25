package com.example.practica7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
/*
 A- Si esta autenticado - App principal
 B- No esta autenticado - Proceso de autenticacion de Firebase UI
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}