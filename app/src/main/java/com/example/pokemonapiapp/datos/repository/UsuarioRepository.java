package com.example.pokemonapiapp.datos.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.pokemonapiapp.datos.local.AppDatabase;
import com.example.pokemonapiapp.datos.local.UsuarioDao;
import com.example.pokemonapiapp.datos.local.entities.UsuarioEntity;

import java.util.List;

public class UsuarioRepository {

    private final UsuarioDao usuarioDao;

    public UsuarioRepository(Context context) {
        // Obtenemos la instancia de la base de datos
        AppDatabase db = AppDatabase.getDatabase(context);
        this.usuarioDao = db.usuarioDao();
    }

    // metodo para registrar un usuario
    public boolean registrarUsuario(String nombre, String pass) {
        // Verificamos si ya existe alguien con ese nombre
        UsuarioEntity existente = usuarioDao.buscarPorNombre(nombre);
        if (existente != null) {
            return false; // Ya existe, error
        }

        // si no existe lo creamos
        UsuarioEntity nuevoUsuario = new UsuarioEntity(nombre, pass);
        usuarioDao.insert(nuevoUsuario);
        return true;
    }

    // metodo para login
    public UsuarioEntity login(String nombre, String pass) {
        return usuarioDao.login(nombre, pass);
    }

    // modificar contraseña
    public void cambiarContrasena(int uid, String nuevaPass) {
        // recuperamos el objeto entero
        UsuarioEntity usuario = usuarioDao.buscarPorId(uid);
        if (usuario != null) {
            usuario.password = nuevaPass;
            usuarioDao.update(usuario);
        }
    }

    // borrar cuenta
    public void borrarUsuario(int uid) {
        UsuarioEntity usuario = usuarioDao.buscarPorId(uid);
        if (usuario != null) {
            usuarioDao.delete(usuario);
        }
    }


    public LiveData<List<UsuarioEntity>> obtenerTodos() {
        return usuarioDao.obtenerTodosLosUsuarios();
    }


}