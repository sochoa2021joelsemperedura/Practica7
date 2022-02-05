package com.example.practica7;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
/*
 A- Si esta autenticado - App principal
 B- No esta autenticado - Proceso de autenticacion de Firebase UI
 TODO : punto 3 // https://console.firebase.google.com/project/p72022joelsempere/firestore/data/~2F?hl=es-419
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comprobarAutenticacion();
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            });
    //Comprobar si FireBase ha autenticado al usuario y accion en los dos casos.
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result){
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK){
            //si estamos autenticados abrimos la actividad principal
            startActivity(new Intent(this,InicioAppActivity.class));
        }else{
            //No puede autenticar
            //TODO : creo que es mejor capturar los errores, pero bueno si nos acordamos hacer refactor. minimo un switch case
            String msg_error = "";
            if (response == null){
                msg_error = getString(R.string.stNecesarioAuth);
            }else if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK){
                msg_error = getString(R.string.stNoRedDisponible);
            }else{
                msg_error = getString(R.string.stUknownError);
            }
            Toast.makeText(this,msg_error,Toast.LENGTH_LONG).show();
        }
        finish();
    }
    //Metodo que lanzara FirebaseUI
    public void createSignInIntent(){
        //crea y lanza singin intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
        // Si quisieramos varios proveedores de autenticación. Mirar la documentación oficial, ya que cambia de una versión a otra
        //https://firebase.google.com/docs/auth/android/firebaseui?hl=es-419#sign_in
                //icono que mostrará, a mi no me funciona
                //.setLogo(R.drawable.logo)
                .setIsSmartLockEnabled(false)//para guardar contraseñas y usuario: true
                .build();
        signInLauncher.launch(signInIntent);
    }
    //Comprueba si estamos autenticados
    private void comprobarAutenticacion(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //verificar la existencia de una sesion
        if (auth.getCurrentUser() != null){
            finish(); //cerramos la actividad
            startActivity(new Intent(this,InicioAppActivity.class));
        }else{
            createSignInIntent(); //si no existe autenticacion, lanza la UI de firebase a traves del metodo
        }
    }

}