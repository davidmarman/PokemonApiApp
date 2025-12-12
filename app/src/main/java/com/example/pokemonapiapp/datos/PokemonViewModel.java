package com.example.pokemonapiapp.datos;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.pokemonapiapp.datos.network.Pokemon;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonViewModel extends AndroidViewModel {

    // CAMBIO: Ahora observamos una Lista de Contenido directamente
    public MutableLiveData<List<Contenido>> listaPokemonLiveData = new MutableLiveData<>();

    public PokemonViewModel(@NonNull Application application) {
        super(application);
    }

    public void buscar(int id) {
        Pokemon.api.buscar(id).enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(@NonNull Call<Respuesta> call, @NonNull Response<Respuesta> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Respuesta apiData = response.body();

                    // 1. Extraer el nombre
                    String nombre = apiData.getName();

                    // 2. Extraer el tipo (verificando que la lista no esté vacía)
                    String tipo = "Desconocido";
                    if (apiData.getTypes() != null && !apiData.getTypes().isEmpty()) {
                        tipo = apiData.getTypes().get(0).type.name;
                    }

                    // 3. Extraer la imagen SVG
                    String imagen = null;
                    if (apiData.getSprites() != null &&
                            apiData.getSprites().other != null &&
                            apiData.getSprites().other.dreamWorld != null) {
                        imagen = apiData.getSprites().other.dreamWorld.frontDefault;
                    }

                    // 4. Crear tu objeto simple "Contenido"
                    Contenido pokemonEncontrado = new Contenido(nombre, tipo, imagen);

                    // 5. Meterlo en una lista para el RecyclerView
                    List<Contenido> lista = new ArrayList<>();
                    lista.add(pokemonEncontrado);

                    // 6. Publicar la lista
                    listaPokemonLiveData.postValue(lista);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Respuesta> call, @NonNull Throwable t) {
                // Manejar error
            }
        });
    }
}