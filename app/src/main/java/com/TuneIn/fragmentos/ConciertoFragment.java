package com.TuneIn.fragmentos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuneIn.Adapters.ConciertosAdapter;
import com.TuneIn.BDDUsuario.UsuarioViewModel;
import com.TuneIn.BDDUsuario.VMFactory;
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

public class ConciertoFragment extends Fragment {
    String nombreUsuario, idUsuario;
    private RecyclerView mRecyclerView;
    ConciertosAdapter adapter;
    List<Concierto> conciertosList, currentConciertosList;
    public static UsuarioViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);

        mRecyclerView = v.findViewById(R.id.recyclerview);
        nombreUsuario = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Crear Factory para pasarle parametros al ViewModel y poder utilizarlos en la Query
        VMFactory vmFactory = new VMFactory(idUsuario, getActivity().getApplication());
        viewModel = new ViewModelProvider(this, vmFactory).get(UsuarioViewModel.class);

        adapter = new ConciertosAdapter(new ConciertosAdapter.AdapterListener() {
            @Override
            public void onComprarClick(String conciertoId) throws ExecutionException, InterruptedException {
                ///REDIRECCIONAR A CHROME AL LINK (?)
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setAdapter(adapter);
        conciertosList = new ArrayList<>();
        currentConciertosList = new ArrayList<>();

        viewModel.getListaArtistasSeguidos().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> listaIdArtistas) {
                Usuario usuario = null;
                try {
                    usuario = viewModel.getUsuarioById(idUsuario);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<String> seguidos = usuario.getArtistasSeguidosList();
                Log.d("ROOM", " ARTISTAS SEGUIDOS: " + seguidos);

                if (seguidos.size() < 1) {
                    adapter.setConciertos(null);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.seatgeek.com/2/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ArtistaAPI artistaAPI = retrofit.create(ArtistaAPI.class);
                    for (String idArtista : seguidos) {
                        Call<JSONResponseConcerts> callArtistaConcierto = artistaAPI.getConciertos(idArtista, 5);

                        callArtistaConcierto.enqueue(new Callback<JSONResponseConcerts>() {
                            @Override
                            public void onResponse(Call<JSONResponseConcerts> call, Response<JSONResponseConcerts> response) {
                                JSONResponseConcerts jsonResponseConcerts = response.body();
                                currentConciertosList = new ArrayList<>(jsonResponseConcerts.getConciertosArray());
                                conciertosList.addAll(currentConciertosList);
                                Log.d("ROOM", "-------Se obtuvo del aritsta: " + idArtista);
                                Log.d("ROOM", "----------------------------------------------------------------------");
                                for(Concierto concierto : conciertosList){
                                    Log.d("ROOM", "" + concierto.getTitle());
                                }
                                Log.d("ROOM", "----------------------------------------------------------------------");
                                adapter.setConciertos(conciertosList);
                            }

                            @Override
                            public void onFailure(Call<JSONResponseConcerts> call, Throwable t) {
                                Log.d("ROOM", "ERRRRRRRRRRRRRRRRROR " + seguidos.get(0));
                            }
                        });
                    }

                }
                Log.d("ROOM", "--------------------------ESTÁ ACÁ--------------------------------------------");
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        return v;
    }

    public static ConciertoFragment newInstance(String text) {

        ConciertoFragment f = new ConciertoFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}