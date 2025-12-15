package com.example.pokemonapiapp.datos.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pokemonapiapp.datos.local.entities.PokemonEntity;
import com.example.pokemonapiapp.datos.local.entities.UsuarioEntity;

@Database(entities = {UsuarioEntity.class, PokemonEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();
    public abstract PokemonDao pokemonDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "pokemon_db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}