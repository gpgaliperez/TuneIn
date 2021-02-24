package com.TuneIn.fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuneIn.Adapters.ConciertosAdapter;
import com.TuneIn.BDDUsuario.RepositorioU;
import com.TuneIn.Entidades.Concierto;
import com.TuneIn.Entidades.Usuario;
import com.TuneIn.Extra.JSONResponseConcerts;
import com.TuneIn.Interfaces.ArtistaAPI;
import com.TuneIn.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConciertoFragment extends Fragment implements RepositorioU.OnResultCallback{
    String nombreUsuario, idUsuario;
    private RecyclerView mRecyclerView;
    TextView tv_sinResultados;
    ConciertosAdapter adapter;
    static List<Concierto> conciertosList, currentConciertosList;
    RepositorioU repositorio;
    Usuario usuarioActual;
    List<String> artistasUSUARIO = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.concierto_fragment, container, false);

        mRecyclerView = v.findViewById(R.id.recyclerview);
        tv_sinResultados = v.findViewById(R.id.tv_sinResultados);
        nombreUsuario = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Crear Repositorio
        Log.d("ROOM", "onCreateView: " + idUsuario);
        repositorio = new RepositorioU( getActivity().getApplication(),this);
        repositorio.getUsuarioById(idUsuario);


        return v;
    }

    public static ConciertoFragment newInstance(String text) {

        ConciertoFragment f = new ConciertoFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onResultBusquedaUsuario(Usuario usuario) {
        usuarioActual = usuario;
        Log.d("ROOM", "onResultBusquedaUsuario: " + usuario.getUsuarioId());
        artistasUSUARIO = usuario.getArtistasSeguidosList();
        ejecutar();

    }

    @Override
    public void onResultBusquedaArtistas(List<String> artistas) {

    }


    public void ejecutar(){
        adapter = new ConciertosAdapter(new ConciertosAdapter.AdapterListener() {
            @Override
            public void onComprarClick(Concierto concierto) throws ExecutionException, InterruptedException {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(concierto.getUrl()));
                startActivity(intent);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setAdapter(adapter);
        conciertosList = new ArrayList<>();
        currentConciertosList = new ArrayList<>();

        Log.d("ROOM", " ARTISTAS SEGUIDOS: " + artistasUSUARIO);

        if (artistasUSUARIO.size() < 1) {
            tv_sinResultados.setVisibility(View.VISIBLE);
            adapter.setConciertos(null);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            tv_sinResultados.setVisibility(View.GONE);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.seatgeek.com/2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ArtistaAPI artistaAPI = retrofit.create(ArtistaAPI.class);
            for (String idArtista : artistasUSUARIO) {
                Call<JSONResponseConcerts> callArtistaConcierto = artistaAPI.getConciertos(idArtista, 5);

                callArtistaConcierto.enqueue(new Callback<JSONResponseConcerts>() {
                    @Override
                    public void onResponse(Call<JSONResponseConcerts> call, Response<JSONResponseConcerts> response) {
                        JSONResponseConcerts jsonResponseConcerts = response.body();
                        currentConciertosList = new ArrayList<>(jsonResponseConcerts.getConciertosArray());
                        conciertosList.addAll(currentConciertosList);
                        adapter.setConciertos(conciertosList);
                    }

                    @Override
                    public void onFailure(Call<JSONResponseConcerts> call, Throwable t) {
                        Log.d("ROOM", "ERRRRRRRRRRRRRRRRROR " + artistasUSUARIO.get(0));
                    }
                });
            }

        }
        mRecyclerView.setVisibility(View.VISIBLE);

    }
}