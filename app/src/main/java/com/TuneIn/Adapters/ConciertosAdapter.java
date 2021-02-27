package com.TuneIn.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TuneIn.Entidades.Concierto;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                .inflate(layout.row_concierto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentConcierto = dataList.get(position);

        DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date mdate = null;
        try {
            mdate = m_ISO8601Local.parse(currentConcierto.getDatetimeUtc());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yy ' - ' h:mm");
        String result = sdf.format(mdate);


        holder.tv_Concierto.setText(currentConcierto.getTitle());
        holder.tv_tour.setText(currentConcierto.getPerformers().get(0).getName());
        holder.tv_detalle.setText(result);
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
                        listener.onComprarClick(currentConcierto);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    public interface AdapterListener {
        void onComprarClick(Concierto concierto) throws ExecutionException, InterruptedException;
    }
}
