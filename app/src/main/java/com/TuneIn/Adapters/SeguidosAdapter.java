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

    public SeguidosAdapter(AdapterListener Alistener){
        this.listener = Alistener;
    }
    public SeguidosAdapter(Context mContext, List<Artista> artistasList){
        this.mContext = mContext;
        this.dataList = artistasList;
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
        //Glide.with(mContext).load(currentArtista.getImage()).into(holder.iv_fotoArtista);

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
    public void setArtistas(List<Artista> artistas) {
        this.dataList = null;
        this.dataList = artistas;
        notifyDataSetChanged();
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
                    try {
                        listener.onSeguirClick(currentArtista.getArtistaId());
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


        }
    }



    public interface AdapterListener {
        void onSeguirClick(String artistaId) throws ExecutionException, InterruptedException;
        void onArtistaClick(Artista artista);
    }
}
