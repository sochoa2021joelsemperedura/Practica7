package com.example.practica7;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;

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
}