package com.example.practica7.adapter;


import android.graphics.Color;
import android.hardware.lights.LightsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica7.R;
import com.example.practica7.model.Mensaje;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends FirestoreRecyclerAdapter<Mensaje, ChatAdapter.ChatHolder> {
    List<Mensaje>mensajes;
    public ChatAdapter(@NonNull FirestoreRecyclerOptions<Mensaje> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull ChatAdapter.ChatHolder holder, int position, @NonNull Mensaje model) {
        holder.tvMensajeItem.setText(model.getUsuario() + "=>" + model.getBody());
    /*
    si el mensaje es del usuario lo colocamos a la izquierda
        if(model.getUsuario().equals(auth.getCurrentUser().getEmail())){
            holder.cvItem.setCardBackgroundColor(Color.YELLOW);
            //holder.clItem.setGravity(Gravity.LEFT);
        }else {
            //holder.lytContenedor.setGravity(Gravity.RIGHT);
            holder.cvItem.setCardBackgroundColor(Color.WHITE);
        }
     */

    }

    void setMensajes(List<Mensaje>mensajes){
        this.mensajes = mensajes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatAdapter.ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mensaje, parent, false);

        return new ChatHolder(view);
    }

    public class ChatHolder extends RecyclerView.ViewHolder {
        private TextView tvMensajeItem;
        private CardView cvItem;
        private ConstraintLayout clItem;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            iniciaViews();
        }

        private void iniciaViews() {
            tvMensajeItem = itemView.findViewById(R.id.tvMensajeItem);
            cvItem = itemView.findViewById(R.id.cvItem);
            clItem = itemView.findViewById(R.id.clItem);
        }

    }

}
