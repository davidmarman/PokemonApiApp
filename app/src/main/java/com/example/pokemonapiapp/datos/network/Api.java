package com.example.pokemonapiapp.datos.network;

import com.example.pokemonapiapp.datos.Respuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    // CAMBIO: Añadir llaves { } rodeando "id" para que Retrofit sepa que es una variable
    @GET("pokemon/{id}")
    Call<Respuesta> buscar(@Path("id") int id);
}
