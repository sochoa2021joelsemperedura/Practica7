package com.example.practica7;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.practica7.adapter.ChatAdapter;
import com.example.practica7.model.Conferencia;
import com.example.practica7.model.FirebaseContract;
import com.example.practica7.model.Mensaje;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.firestore.ChangeEventListener;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class InicioAppActivity extends AppCompatActivity {
    private FirebaseAuth auth; //Instancia de autenticacion
    private TextView tvUser;
    private Intent intent;
    //***conferencias***//
    private TextView tvConferenciaIniciada;
    private TextView tvConferencias;
    private Spinner spConferencias;
    //Punto 17 p2 Array de conferencias
    private ArrayList<Conferencia> listaConferencias;
    //Boton Info de la conferencia seleccionada
    private ImageButton ibInfo;
    //Los atributos del chat
    private RecyclerView rvChat;
    private EditText etMensaje;
    private ImageButton ibEnviar;

    private Conferencia conferenciaActual; //guardo el objeto para extraer sus datos si los necesito
    //Adaptador
    private ChatAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);
        iniciaViews();
        //Al iniciar la actividad mostramos los datos del usuario
        getDatosConexion(tvUser);
        leerConferencias(); //extrae las conferencias de fb y las carga en el spinner
        onItemSelectedSpinner(); //Cuando seleccionas un item del spinner, controla lo que ocurre
        //*****CLICK EN EL IMAGEBUTTON INFO*****//
        ibInfo.setOnClickListener(e -> {
            // Toast.makeText(this,conferencia.toString(),Toast.LENGTH_LONG).show();
            infoDialogo(conferenciaActual.info());
        });
        iniciarConferenciasIniciadas();
        //*****CLICK EN EL IMAGEBUTON ENVIAR*****//
        ibEnviar.setOnClickListener(e -> {
            enviarMensaje();
        });

        defineAdaptador();


    }

    //Recuperar datos de la conexion que se asignan a un text view
    private void getDatosConexion(TextView textView) {
        auth = FirebaseAuth.getInstance();
        FirebaseUser usrFB = auth.getCurrentUser();
        textView.setText(usrFB.getEmail());
    }

    //cerrar sesion
    private void fbCerrarSesion() {
        auth.signOut();
        startActivity(new Intent(InicioAppActivity.this, MainActivity.class));
        finish();
    }


    //*************************MENU****************************//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                intent = new Intent(this, EmpresaActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //****LEER LAS CONFERENCIAS DESDE FIRESTORE****//
    private void leerConferencias() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        listaConferencias = new ArrayList<Conferencia>();
        db.collection(FirebaseContract.ConferenciaEntry.NODE_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " +
                                        document.getData());
                                listaConferencias.add(document.toObject(Conferencia.class));
                            }
                            cargaSpinner();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
    //****ENVIAR MENSAJES****//

    private void enviarMensaje() {
        String body = etMensaje.getText().toString();
        if (!body.isEmpty()) {
            //usuario y mensaje
            Mensaje mensaje = new Mensaje(tvUser.getText().toString(), body);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(FirebaseContract.ConferenciaEntry.NODE_NAME)
                    //documento conferencia actual
                    .document(conferenciaActual.getId())
                    //subcolección de la conferencia
                    .collection(FirebaseContract.ChatEntry.COLLECTION_NAME)
                    //añadimos el mensaje nuevo
                    .add(mensaje);
            etMensaje.setText("");
            ocultarTeclado();
        }
    }

    /**
     * Permite ocultar el teclado
     */
    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(etMensaje.getWindowToken(), 0);
        }
    }


    //***CARGA LAS CONFERENCIAS EN UN SPINER***//
    public void cargaSpinner() {
        //Cargamos el arrayList en un arrayAdapter
        ArrayAdapter<Conferencia> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaConferencias);
        //Insertamos en el Spinner
        spConferencias.setAdapter(adapter);
    }

    //****Que ocurre cuando se selecciona un item dentro del spinner****//
    private void onItemSelectedSpinner() {
        spConferencias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //****Cambio de conferencia en el Text View****//Hecho como el profesor en la practica
                tvConferenciaIniciada.setText(getString(R.string.stConferenciaIniciada) + " " + spConferencias.getSelectedItem().toString());
                conferenciaActual = (Conferencia) spConferencias.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void infoDialogo(String mensaje) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.stInfoConferencia)
                .setMessage(mensaje)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no hace nada.
                    }
                }).show();
    }

    /*
    No me ha hecho falta el uso de este metodo porque hice otra cosa antes de llegar a este punto.
    Me lo guardo por si necesito cambiarlo más adelante.
    Cambio : Lo adapto a la practica mejor por algo del log
    */
    private void iniciarConferenciasIniciadas() {
        //https://firebase.google.com/docs/firestore/query-data/listen
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef =
                db.collection(FirebaseContract.ConferenciaIniciadaEntry.COLLECTION_NAME).document(FirebaseContract.ConferenciaIniciadaEntry.ID);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String
                            conferenciaIniciada = snapshot.getString(FirebaseContract.ConferenciaIniciadaEntry.CONFERENCIA);
                    tvConferenciaIniciada.setText(getString(R.string.stConferenciaIniciada) + " " + conferenciaIniciada);
                    Log.d(TAG, "Conferencia iniciada: " + snapshot.getData());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    //****DEFINIR EL ADAPTER****//
    private void defineAdaptador() {
        //consulta en Firebase
        Query query = FirebaseFirestore.getInstance()
                //coleccion conferencias
                .collection(FirebaseContract.ConferenciaEntry.NODE_NAME)
                //documento: conferencia actual
                .document(FirebaseContract.ConferenciaEntry.ID)
                //colección chat de la conferencia
                .collection(FirebaseContract.ChatEntry.COLLECTION_NAME)
                //obtenemos la lista ordenada por fecha
                .orderBy(FirebaseContract.ChatEntry.FECHA_CREACION,
                        Query.Direction.DESCENDING);


        //Creamos la opciones del FirebaseAdapter
        FirestoreRecyclerOptions<Mensaje> options = new FirestoreRecyclerOptions.Builder<Mensaje>()
                //consulta y clase en la que se guarda los datos
                .setQuery(query, Mensaje.class)
                .setLifecycleOwner(this)
                .build();
        //si el usuario ya habia seleccionado otra conferencia, paramos las escucha
        if (adapter != null) {
            adapter.stopListening();
        }
        //Creamos el adaptador
        adapter = new ChatAdapter(options);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        //asignamos el adaptador
        rvChat.setAdapter(adapter);
        //comenzamos a escuchar. Normalmente solo tenemos un adaptador, esto tenemos que
        //hacerlo en el evento onStar, como indica la documentación
        adapter.startListening();
        /*Podemos reaccionar ante cambios en la query( se añade un mesaje).
        Nosotros o que necesitamos es mover el scroll del
        recyclerView al inicio para ver el mensaje nuevo
         */
                adapter.getSnapshots().addChangeEventListener(new ChangeEventListener() {
                    @Override
                    public void onChildChanged(@NonNull ChangeEventType type, @NonNull
                            DocumentSnapshot snapshot, int newIndex, int oldIndex) {
                        rvChat.smoothScrollToPosition(0);
                    }
                    @Override
                    public void onDataChanged() {
                    }
                    @Override
                    public void onError(@NonNull FirebaseFirestoreException e) {
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    //es necesario parar la escucha
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }



    //*****INICIA VIEWS*****//
    private void iniciaViews(){
        tvUser = findViewById(R.id.tvUser);
        tvConferenciaIniciada = findViewById(R.id.tvConferenciaIniciada);
        tvConferencias = findViewById(R.id.tvConferencias);
        spConferencias = findViewById(R.id.spConferencias);
        ibInfo = findViewById(R.id.ibInfo);
        rvChat = findViewById(R.id.rvChat);
        etMensaje = findViewById(R.id.etMensaje);
        ibEnviar = findViewById(R.id.ibEnviar);
    }
}