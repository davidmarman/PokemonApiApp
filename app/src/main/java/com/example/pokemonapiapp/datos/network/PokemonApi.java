package com.example.pokemonapiapp.datos.network;

import com.example.pokemonapiapp.datos.network.respuesta.Respuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {
    @GET("pokemon/{id}")
    Call<Respuesta> getPokemon(@Path("id") int id);
}