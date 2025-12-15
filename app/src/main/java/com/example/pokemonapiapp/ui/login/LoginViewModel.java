package com.example.pokemonapiapp.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pokemonapiapp.datos.local.entities.UsuarioEntity;
import com.example.pokemonapiapp.datos.repository.UsuarioRepository;

public class LoginViewModel extends AndroidViewModel {

    private final UsuarioRepository repositorio;

    // LiveData para comunicar resultados a la vista
    public MutableLiveData<UsuarioEntity> usuarioLogueado = new MutableLiveData<>();
    public MutableLiveData<String> mensajeError = new MutableLiveData<>();
    public MutableLiveData<Boolean> registroExitoso = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repositorio = new UsuarioRepository(application);
    }

    // Lógica de Login
    public void iniciarSesion(String nombre, String pass) {
        if (nombre.isEmpty() || pass.isEmpty()) {
            mensajeError.setValue("Por favor, rellena todos los campos");
            return;
        }

        UsuarioEntity usuario = repositorio.login(nombre, pass);

        if (usuario != null) {
            usuarioLogueado.setValue(usuario);
        } else {
            mensajeError.setValue("Usuario o contraseña incorrectos");
        }
    }

    // Lógica de Registro
    public void registrarse(String nombre, String pass) {
        if (nombre.isEmpty() || pass.isEmpty()) {
            mensajeError.setValue("Rellena los campos para registrarte");
            return;
        }

        boolean exito = repositorio.registrarUsuario(nombre, pass);
        if (exito) {
            registroExitoso.setValue(true);
            mensajeError.setValue("Registro completado. Ahora inicia sesión.");
        } else {
            registroExitoso.setValue(false);
            mensajeError.setValue("Ese nombre de usuario ya existe.");
        }
    }
}