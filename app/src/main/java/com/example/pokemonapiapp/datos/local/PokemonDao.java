package com.example.pokemonapiapp.datos.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemonapiapp.datos.local.entities.PokemonEntity;

import java.util.List;

@Dao
public interface PokemonDao {

    @Insert
    void insert(PokemonEntity pokemon);

    // Obtener lista para el RecyclerView
    @Query("SELECT * FROM pokemons WHERE usuario_id = :userId")
    LiveData<List<PokemonEntity>> obtenerPokemonsDeUsuario(int userId);

    // Para la regla del "Primer Pokemon gratis"
    @Query("SELECT COUNT(*) FROM pokemons WHERE usuario_id = :userId")
    int contarPokemons(int userId);

    // Suma de poder
    @Query("SELECT COALESCE(SUM(poder), 0) FROM pokemons WHERE usuario_id = :userId")
    int obtenerPoderTotal(int userId);

    // Detección de duplicados
    @Query("SELECT COUNT(*) FROM pokemons WHERE usuario_id = :userId AND api_id = :apiId")
    int verificarDuplicado(int userId, int apiId);
}