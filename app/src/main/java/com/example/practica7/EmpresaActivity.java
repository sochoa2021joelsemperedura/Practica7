package com.example.practica7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.practica7.model.Empresa;
import com.example.practica7.model.FirebaseContract;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmpresaActivity extends AppCompatActivity {
    private TextView tvNombre;
    private TextView tvDireccion;
    private  TextView tvTelefono;
    private Empresa empresa;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        iniciaViews();
        obtenDatosEmpresa(); //obten los datos de la empresa que rellenaran el activity

        //*********CLICKS DE LOS TEXT VIEWS*********//todo
        tvTelefono.setOnClickListener(e->{
            intent = new Intent(Intent.ACTION_DIAL,empresa.getUriTelefono()); //DIAL no requiere permiso
            startActivity(intent);
        });
        tvDireccion.setOnClickListener(e->{
            intent = new Intent(Intent.ACTION_VIEW,empresa.getUriLocalizacion());
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        });
    }

    public void iniciaViews(){
        tvNombre = findViewById(R.id.tvNombre);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvTelefono = findViewById(R.id.tvTelefono);
    }

    //*****Leer los datos una vez llamando a firebase*****//
    void obtenDatosEmpresa(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef=db.collection(FirebaseContract.EmpresaEntry.NODE_NAME).document(FirebaseContract.EmpresaEntry.ID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //leemos los datos
                empresa = documentSnapshot.toObject(Empresa.class);
//mostramos los datos y asignamos eventos
                asignaValoresEmpresa();
            }
            //los seters de los textviews
            private void asignaValoresEmpresa() {
                tvNombre.setText(empresa.getNombre());
                tvDireccion.setText(getString(R.string.stDireccion)+": "+empresa.getDireccion());
                tvTelefono.setText(getString(R.string.stTelefono)+": "+empresa.getTelefono());
            }
        });
    }

}
