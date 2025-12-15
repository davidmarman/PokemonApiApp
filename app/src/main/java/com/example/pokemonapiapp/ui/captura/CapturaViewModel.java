package com.example.pokemonapiapp.ui.captura;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pokemonapiapp.datos.local.entities.PokemonEntity;
import com.example.pokemonapiapp.datos.network.respuesta.Respuesta;
import com.example.pokemonapiapp.datos.repository.PokemonRepository;

import java.util.Random;

public class CapturaViewModel extends AndroidViewModel {

    private final PokemonRepository repository;

    // LiveData para comunicar con la vista
    public MutableLiveData<Respuesta> pokemonSalvaje = new MutableLiveData<>();
    public MutableLiveData<String> estadoCaptura = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();
    public MutableLiveData<Boolean> capturaExitosa = new MutableLiveData<>();

    public CapturaViewModel(@NonNull Application application) {
        super(application);
        repository = new PokemonRepository(application);
    }

    public void buscarAleatorio() {
        // Generar ID random (1 a 1025)
        int randomId = new Random().nextInt(1025) + 1;
        repository.buscarPokemonAleatorio(randomId, pokemonSalvaje, error);
    }

    public void verificarPosibilidadCaptura(int userId, Respuesta pokemon) {

        // verificar duplicado
        if (repository.existePokemon(userId, pokemon.getId())) {
            estadoCaptura.setValue("DUPLICADO");
            return;
        }

        // verificar si es el primero (Gratis)
        int cantidad = repository.contarPokemons(userId);
        if (cantidad == 0) {
            estadoCaptura.setValue("CAPTURABLE");
            return;
        }

        // comparar poder
        int miPoder = repository.obtenerPoderTotal(userId);
        int poderSalvaje = pokemon.getStats().get(0).baseStat;

        if (miPoder > poderSalvaje) {
            estadoCaptura.setValue("CAPTURABLE");
        } else {
            estadoCaptura.setValue("IMPOSIBLE");
        }
    }

    // insertamos
    public void realizarCaptura(int userId) {
        Respuesta p = pokemonSalvaje.getValue();

        if (p != null) {
            String imagen = p.getSprites().other.officialArtwork.frontDefault;
            String tipo = p.getTypes().get(0).type.name;
            int poderReal = p.getStats().get(0).baseStat;

            PokemonEntity nuevo = new PokemonEntity(
                    p.getId(),
                    p.getName(),
                    tipo,
                    imagen,
                    poderReal,
                    userId
            );

            repository.capturar(nuevo);

            capturaExitosa.setValue(true);
        }
    }
}