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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.TuneIn.Entidades.Artista;
import com.TuneIn.Entidades.UsuarioConArtistas;
import com.TuneIn.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static com.TuneIn.R.*;


public class ArtistaAdapter extends RecyclerView.Adapter<ArtistaAdapter.ViewHolder> {
    private List<Artista> dataList = new ArrayList<>();
    private Context mContext;
    private Artista currentArtista;
    private AdapterListener listener;
    //private OnArtistaClickListener listener;
    //private OnSeguirClickListener listenerSeguir;

    public ArtistaAdapter(AdapterListener Alistener){
        this.listener = Alistener;
    }
    public ArtistaAdapter(Context mContext, List<Artista> artistasList){
        this.mContext = mContext;
        this.dataList = artistasList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        v = layoutInflater.inflate(R.layout.list_row_artistas, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentArtista = dataList.get(position);

        holder.tv_nombreArtista.setText(currentArtista.getNombre());
        Glide.with(mContext).load(currentArtista.getImage()).into(holder.iv_fotoArtista);

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
        private ImageView iv_fotoArtista;
        private Button btn_seguirArtista;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nombreArtista = itemView.findViewById(id.tv_nombreArtista);
            iv_fotoArtista = itemView.findViewById(R.id.iv_fotoArtista);
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
