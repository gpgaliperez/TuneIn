package com.TuneIn.Adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.TuneIn.Entidades.Artista;
import com.TuneIn.Entidades.UsuarioConArtistas;
import com.TuneIn.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.TuneIn.R.*;


public class ArtistaAdapter extends RecyclerView.Adapter<ArtistaAdapter.ViewHolder> {

    private List<Artista> dataList = new ArrayList<>();
    private Artista currentArtista;
    //private OnArtistaClickListener listener;
    //private OnSeguirClickListener listenerSeguir;
    private AdapterListener listener;

    public ArtistaAdapter(AdapterListener Alistener){
        this.listener = Alistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.list_row_artistas, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Inicializar la informacion
        currentArtista = dataList.get(position);
        holder.tv_nombreArtista.setText(currentArtista.getNombre());

    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;}
        else{
            return dataList.size();}
    }

    public void setArtistas(List<Artista> artistas) {
        this.dataList = null;
        this.dataList = artistas;
        notifyDataSetChanged();
    }

    public void setArtistasSeguidos(UsuarioConArtistas usuarioConArtistas) {

        Log.d("ROOM", "usuarioConArtista DENTRO DE ADAPTER " + usuarioConArtistas);

        if(usuarioConArtistas == null){
            this.dataList = null;
        }else{
            this.dataList = usuarioConArtistas.getArtistas();
        }
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_nombreArtista;
        //private ImageView iv_fotoArtista;
        private Button btn_seguirArtista;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nombreArtista = itemView.findViewById(id.tv_nombreArtista);
            // iv_fotoArtista = itemView.findViewById(R.id.iv_fotoArtista);
            btn_seguirArtista = itemView.findViewById(id.btn_seguir);

            btn_seguirArtista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        listener.onSeguirClick(currentArtista);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onArtistaClick(currentArtista);
                }
            });


            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onArtistaClick(dataList.get(position));
                    }
                }
            });

            itemView.findViewById(id.btn_seguir).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listenerSeguir != null && position != RecyclerView.NO_POSITION) {
                        btn_seguirArtista.setText(string.btnSeguido);
                        listenerSeguir.onSeguirClick(dataList.get(position));
                    }
                }
            });*/

        }
    }

    /*public interface OnArtistaClickListener {
        void onArtistaClick(Artista artista);
    }

    public void setOnArtistaClickListener(OnArtistaClickListener listener) {
        this.listener = listener;
    }

    public interface OnSeguirClickListener {
        void onSeguirClick(Artista artista);
    }

    public void setOnSeguirClickListener(OnSeguirClickListener listener) {
        this.listenerSeguir = listener;
    }*/

    public interface AdapterListener {
        // TODO https://www.codeproject.com/Tips/1229751/Handle-Click-Events-of-Multiple-Buttons-Inside-a
        void onSeguirClick(Artista artista) throws ExecutionException, InterruptedException;
        void onArtistaClick(Artista artista);
    }


}
