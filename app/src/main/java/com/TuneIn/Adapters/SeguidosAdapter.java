package com.TuneIn.Adapters;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static com.TuneIn.R.*;


public class SeguidosAdapter extends RecyclerView.Adapter<SeguidosAdapter.ViewHolder> {
    private List<Artista> dataList = new ArrayList<>();
    private Context mContext;
    private Artista currentArtista;
    private AdapterListener listener;

    public SeguidosAdapter(Context mContext, AdapterListener Alistener){
        this.mContext = mContext;
        this.listener = Alistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.row_artistas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentArtista = dataList.get(position);

        holder.tv_nombreArtista.setText(currentArtista.getNombre());
        holder.btn_seguirArtista.setText(R.string.btnSeguido);
        Glide.with(mContext).load(currentArtista.getImage()).into(holder.iv_fotoArtista);

        //holder.iv_fotoArtista.setImageResource();
       /*holder.title.setText(artistsList.get(position).getName());
        holder.duration.setText(artistsList.get(position).getId());
        holder.category.setText(artistsList.get(position).getUrlTickets());
        holder.release.setText(artistsList.get(position).getName());

        Link[] links = artistsList.get(position).getLinks();
        for(Link link : links){
            holder.spotifyUrl.setText(link.getUrl()); //Esto está mal en realidad
        }*/
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;}
        else{
            return dataList.size();}
    }

    public void setArtistasSeguidos(List<Artista> listaSeguidos) {
        if(listaSeguidos == null){
            this.dataList = null;
        }else{
            this.dataList = listaSeguidos;
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nombreArtista;
        private ImageView iv_fotoArtista;
        private Button btn_seguirArtista;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nombreArtista = itemView.findViewById(id.tv_nombreArtistas);
            iv_fotoArtista = itemView.findViewById(R.id.iv_fotoArtistas);
            btn_seguirArtista = itemView.findViewById(id.btn_seguirArtistas);

            btn_seguirArtista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        try {
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


    public interface AdapterListener {
        void onSeguirClick(Artista artista) throws ExecutionException, InterruptedException;
        void onArtistaClick(Artista artista);
    }
}
