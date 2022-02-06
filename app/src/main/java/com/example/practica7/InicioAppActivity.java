package com.example.practica7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioAppActivity extends AppCompatActivity {
    private FirebaseAuth auth; //Instancia de autenticacion
    private TextView tvUser;
    private Intent intent;
    //***conferencias***//
    private TextView tvConferenciaIniciada;
    private TextView tvConferencias;
    private Spinner spConferencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);
        iniciaViews();
        //Al iniciar la actividad mostramos los datos del usuario
        getDatosConexion(tvUser);
    }
    //Recuperar datos de la conexion que se asignan a un text view
    private void getDatosConexion(TextView textView){
        auth = FirebaseAuth.getInstance();
        FirebaseUser usrFB = auth.getCurrentUser();
        textView.setText(usrFB.getEmail());
    }
    //cerrar sesion
    private void fbCerrarSesion(){
        auth.signOut();
        startActivity(new Intent(InicioAppActivity.this,MainActivity.class));
        finish();
    }


    //*************************MENU****************************//
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    //*************************Opciones de los items del menu****************************//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Ordenar
            case R.id.action_cerrar:
                fbCerrarSesion();
                return true;
            case R.id.itDatos:
                //Iniciamos la activity donde se visualizaran los datos de la empresa
                //en este caso es el instituto y solo existe una empresa...
                intent = new Intent(this,EmpresaActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //*****INICIA VIEWS*****//
    private void iniciaViews(){
        tvUser = findViewById(R.id.tvUser);
        tvConferenciaIniciada = findViewById(R.id.tvConferenciaIniciada);
        tvConferencias = findViewById(R.id.tvConferencias);
        spConferencias = findViewById(R.id.spConferencias);
    }
}