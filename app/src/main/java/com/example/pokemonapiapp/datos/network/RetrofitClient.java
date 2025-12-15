package com.example.pokemonapiapp.datos.network;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static PokemonApi getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(PokemonApi.class);
    }
}