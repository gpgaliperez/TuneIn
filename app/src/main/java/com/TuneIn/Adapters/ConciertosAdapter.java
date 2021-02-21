package com.TuneIn.Adapters;

import android.content.Context;
import java.util.Calendar;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.TuneIn.Entidades.Artista;
import com.TuneIn.Entidades.Concierto;
import com.TuneIn.Entidades.UsuarioConArtistas;
import com.TuneIn.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import static com.TuneIn.R.*;


public class ConciertosAdapter extends RecyclerView.Adapter<ConciertosAdapter.ViewHolder> {
    private List<Concierto> dataList = new ArrayList<>();
    private Context mContext;
    private Concierto currentConcierto;
    private AdapterListener listener;
    private FirebaseUser user;

    public ConciertosAdapter(AdapterListener Alistener){
        this.listener = Alistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.cardview_prueba, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentConcierto = dataList.get(position);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(currentConcierto.getDatetimeUtc()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("d/M/yy 'a las' h:mm a");


        holder.tv_Concierto.setText(currentConcierto.getTitle());
        holder.tv_tour.setText(currentConcierto.getPerformers().get(0).getName());
        holder.tv_detalle.setText(format.format(cal.getTime()));
        // holder.tv_detalle.setText(currentConcierto.getDatetimeUtc());
        holder.tv_estadio.setText(currentConcierto.getVenue().getCity() + ", " +currentConcierto.getVenue().getCountry());
    }

    @Override
    public int getItemCount() {
        if (dataList == null) return 0;
        else return dataList.size();
    }
    public void setConciertos(List<Concierto> conciertos) {
        if(conciertos == null){
            this.dataList = null;
        }else{
            this.dataList = conciertos;
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_Concierto, tv_tour, tv_detalle, tv_estadio;
        private Button btn_comprar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Concierto = itemView.findViewById(id.tv_concierto);
            tv_tour = itemView.findViewById(id.tv_tour);
            tv_detalle = itemView.findViewById(id.tv_detalle);
            tv_estadio = itemView.findViewById(id.tv_estadio);
            btn_comprar = itemView.findViewById(id.btn_comprar);

            btn_comprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        listener.onComprarClick(String.valueOf(currentConcierto.getId()));
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    public interface AdapterListener {
        void onComprarClick(String conciertoId) throws ExecutionException, InterruptedException;
    }
}
