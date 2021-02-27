package com.TuneIn.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.TuneIn.Entidades.Artista;
import com.TuneIn.R;
import com.bumptech.glide.Glide;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AllArtistasAdapter extends RecyclerView.Adapter<AllArtistasAdapter.ViewHolder> {
    private List<Artista> dataList;
    private  Context context;
    private Artista currentArtista;
    private SeguidosAdapter.AdapterListener listener;
    private List<String> listaPositionsId = new ArrayList<>();

    public AllArtistasAdapter(Context context, List<Artista> data, SeguidosAdapter.AdapterListener Alistener) {
        this.context = context;
        this.dataList = data;
        this.listener = Alistener;
    }

    public void updateList(List<Artista> artistasResultado){
        dataList = artistasResultado;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_artistas, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentArtista = dataList.get(position);

        if (listaPositionsId.contains(currentArtista.getArtistaId())) {
             holder.btn_seguirArtista.setText(R.string.btnSeguido);
             holder.btn_seguirArtista.setBackgroundColor(context.getResources().getColor(R.color.colorHint));
        }else {
            holder.btn_seguirArtista.setText(R.string.btnSeguir);
            holder.btn_seguirArtista.setBackgroundColor(context.getResources().getColor(R.color.principal));
        }
        holder.tv_nombreArtista.setText(currentArtista.getNombre());
        Glide.with(context).load(currentArtista.getImage()).into(holder.iv_fotoArtista);

    }

    @Override
    public int getItemCount(){
        if (dataList == null) return 0;
        else return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nombreArtista;
        private ImageView iv_fotoArtista;
        private Button btn_seguirArtista;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nombreArtista = itemView.findViewById(R.id.tv_nombreArtistas);
            iv_fotoArtista = itemView.findViewById(R.id.iv_fotoArtistas);
            btn_seguirArtista = itemView.findViewById(R.id.btn_seguirArtistas);

          btn_seguirArtista.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  int position = getAdapterPosition();
                  if (listener != null && position != RecyclerView.NO_POSITION) {
                      try {

                          listaPositionsId.add(dataList.get(position).getArtistaId());
                          notifyItemChanged(position);
                          listener.onSeguirClick(dataList.get(position));
                      } catch (ExecutionException | InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
              }
          });

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

}
