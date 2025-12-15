package com.example.pokemonapiapp.datos.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokemonapiapp.datos.local.AppDatabase;
import com.example.pokemonapiapp.datos.local.PokemonDao;
import com.example.pokemonapiapp.datos.local.entities.PokemonEntity;
import com.example.pokemonapiapp.datos.network.PokemonApi;
import com.example.pokemonapiapp.datos.network.RetrofitClient;
import com.example.pokemonapiapp.datos.network.respuesta.Respuesta;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonRepository {

    private final PokemonDao pokemonDao;

    public PokemonRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        this.pokemonDao = db.pokemonDao();
    }

    // Obtener la lista observable (se actualiza sola si capturas uno nuevo)
    public LiveData<List<PokemonEntity>> obtenerMisPokemons(int userId) {
        return pokemonDao.obtenerPokemonsDeUsuario(userId);
    }

    // Obtener la suma de poder (para mostrar en la cabecera)
    public int obtenerPoderTotal(int userId) {
        return pokemonDao.obtenerPoderTotal(userId);
    }



    public void buscarPokemonAleatorio(int id, MutableLiveData<Respuesta> liveData, MutableLiveData<String> error) {
        PokemonApi api = RetrofitClient.getApi();
        api.getPokemon(id).enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    error.postValue("Error API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                error.postValue("Fallo de red: " + t.getMessage());
            }
        });
    }


    public int contarPokemons(int userId) {
        return pokemonDao.contarPokemons(userId);
    }
    public boolean existePokemon(int userId, int apiId) {
        return pokemonDao.verificarDuplicado(userId, apiId) > 0;
    }
    public void capturar(PokemonEntity pokemon) {
        pokemonDao.insert(pokemon);
    }
}