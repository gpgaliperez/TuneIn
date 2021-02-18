package com.TuneIn.Interfaces;

import com.TuneIn.Entidades.Artista;
import com.TuneIn.Extra.JSONResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArtistaAPI {

    @GET("performers?client_id=MjE1Mzk3MDJ8MTYxMzAwMTQ3OC4zNDE5MzU0")
    Call<JSONResponse> getArtistas(
            @Query("taxonomies.name") String taxonomy,
            @Query("sort") String sort,
            @Query("per_page") int per_page,
            @Query("page") int page
    );

    @GET("performers/{artistaId}?client_id=MjE1Mzk3MDJ8MTYxMzAwMTQ3OC4zNDE5MzU0")
    Call<Artista> getArtista(
            @Path("artistaId") int artistaId
    );
}
