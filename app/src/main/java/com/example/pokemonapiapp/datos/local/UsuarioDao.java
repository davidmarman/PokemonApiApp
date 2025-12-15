package com.example.pokemonapiapp.datos.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pokemonapiapp.datos.local.entities.UsuarioEntity;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    void insert(UsuarioEntity usuario); // Devuelve el ID del nuevo usuario

    @Update
    void update(UsuarioEntity usuario);

    @Delete
    void delete(UsuarioEntity usuario);

    // Para el Login
    @Query("SELECT * FROM usuarios WHERE nombre_usuario = :nombre AND password = :pass LIMIT 1")
    UsuarioEntity login(String nombre, String pass);

    // Para evitar que dos personas se llamen igual al registrarse
    @Query("SELECT * FROM usuarios WHERE nombre_usuario = :nombre LIMIT 1")
    UsuarioEntity buscarPorNombre(String nombre);

    @Query("SELECT * FROM usuarios WHERE uid = :uid")
    UsuarioEntity buscarPorId(int uid);

    @Query("SELECT * FROM usuarios")
    LiveData<List<UsuarioEntity>> obtenerTodosLosUsuarios();
}