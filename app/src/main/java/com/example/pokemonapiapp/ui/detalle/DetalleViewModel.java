package com.example.pokemonapiapp.ui.detalle;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pokemonapiapp.datos.network.respuesta.Respuesta;
import com.example.pokemonapiapp.datos.repository.PokemonRepository;

public class DetalleViewModel extends AndroidViewModel {

    private final PokemonRepository repository;
    public MutableLiveData<Respuesta> pokemonDetalle = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    public DetalleViewModel(@NonNull Application application) {
        super(application);
        repository = new PokemonRepository(application);
    }

    // Aquí pedimos datos a la api
    public void cargarDetalle(int apiId) {
        repository.buscarPokemonAleatorio(apiId, pokemonDetalle, error);
    }
}