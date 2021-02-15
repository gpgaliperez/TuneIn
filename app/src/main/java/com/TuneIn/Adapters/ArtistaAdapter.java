package com.TuneIn.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TuneIn.Entidades.Artista;
import com.TuneIn.R;

import java.util.ArrayList;
import java.util.List;


public class ArtistaAdapter extends RecyclerView.Adapter<ArtistaAdapter.ViewHolder> {

    private List<Artista> dataList = new ArrayList<>();
    private OnArtistaClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_artistas, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Inicializar la informacion
        Artista currrentArtista = dataList.get(position);
        holder.tv_nombreArtista.setText(currrentArtista.getNombre());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setArtistas(List<Artista> artistas) {
        this.dataList = artistas;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_nombreArtista;
        //private ImageView iv_fotoArtista;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nombreArtista = itemView.findViewById(R.id.tv_nombreArtista);
            // iv_fotoArtista = itemView.findViewById(R.id.iv_fotoArtista);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onArtistaClick(dataList.get(position));
                    }
                }
            });

        }
    }

    public interface OnArtistaClickListener {
        void onArtistaClick(Artista artista);
    }

    public void setOnArtistaClickListener(OnArtistaClickListener listener) {
        this.listener = listener;
    }
}
