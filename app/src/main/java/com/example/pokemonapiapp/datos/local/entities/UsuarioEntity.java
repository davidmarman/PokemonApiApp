package com.example.pokemonapiapp.datos.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class UsuarioEntity {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "nombre_usuario")
    public String nombreUsuario;

    @ColumnInfo(name = "password")
    public String password;

    // Constructor vacío requerido por Room
    public UsuarioEntity() {}

    // Constructor para crear usuarios nuevos fácilmente
    public UsuarioEntity(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }
}