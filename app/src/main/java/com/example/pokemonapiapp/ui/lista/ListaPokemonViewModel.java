package com.example.pokemonapiapp.ui.lista;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokemonapiapp.datos.local.entities.PokemonEntity;
import com.example.pokemonapiapp.datos.repository.PokemonRepository;

import java.util.List;

public class ListaPokemonViewModel extends AndroidViewModel {

    private final PokemonRepository repository;
    private LiveData<List<PokemonEntity>> misPokemons;
    public MutableLiveData<Integer> poderTotal = new MutableLiveData<>(0);

    public ListaPokemonViewModel(@NonNull Application application) {
        super(application);
        repository = new PokemonRepository(application);
    }

    // llamamos a este metodo al cargar el fragmento pasando el ID del usuario
    public LiveData<List<PokemonEntity>> obtenerPokemons(int userId) {
        if (misPokemons == null) {
            misPokemons = repository.obtenerMisPokemons(userId);
        }
        actualizarPoderTotal(userId);
        return misPokemons;
    }

    public void actualizarPoderTotal(int userId) {
        int total = repository.obtenerPoderTotal(userId);
        poderTotal.setValue(total);
    }
}