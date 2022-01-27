package com.example.practica7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioAppActivity extends AppCompatActivity {
    private FirebaseAuth auth; //Instancia de autenticacion
    private TextView tvUser;
    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);
        iniciaViews();
        //Al iniciar la actividad mostramos los datos del usuario
        getDatosConexion(tvUser);
        //Accion del boton Cerrar sesion
        btnCerrarSesion.setOnClickListener(e->{
            fbCerrarSesion();
        });

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

    /* TODO: Esto va asociado a un menu item que aun no existe, se hara en la parte 2
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_logout:
                fbCerrarSesion();
                return true;
        }
    }
     */

    private void iniciaViews(){
        tvUser = findViewById(R.id.tvUser);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
    }
}