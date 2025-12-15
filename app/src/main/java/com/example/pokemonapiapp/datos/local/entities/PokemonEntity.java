package com.example.pokemonapiapp.datos.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokemons",
        foreignKeys = @ForeignKey(
                entity = UsuarioEntity.class,
                parentColumns = "uid",
                childColumns = "usuario_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("usuario_id")}
)
public class PokemonEntity {

    @PrimaryKey(autoGenerate = true)
    public int id; // ID interno de la BD

    @ColumnInfo(name = "api_id")
    public int apiId; // ID oficial

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "tipo")
    public String tipo;

    @ColumnInfo(name = "imagen_url")
    public String imagenUrl;

    @ColumnInfo(name = "poder")
    public int poder; // base_experience

    @ColumnInfo(name = "usuario_id")
    public int usuarioId; // Dueño del Pokémon

    public PokemonEntity(int apiId, String nombre, String tipo, String imagenUrl, int poder, int usuarioId) {
        this.apiId = apiId;
        this.nombre = nombre;
        this.tipo = tipo;
        this.imagenUrl = imagenUrl;
        this.poder = poder;
        this.usuarioId = usuarioId;
    }
}