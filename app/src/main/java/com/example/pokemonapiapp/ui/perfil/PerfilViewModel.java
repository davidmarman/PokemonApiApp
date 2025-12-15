package com.example.pokemonapiapp.ui.perfil;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokemonapiapp.datos.local.entities.UsuarioEntity;
import com.example.pokemonapiapp.datos.repository.UsuarioRepository;

import java.util.List;

public class PerfilViewModel extends AndroidViewModel {

    private final UsuarioRepository repository;
    public MutableLiveData<String> mensaje = new MutableLiveData<>();
    public MutableLiveData<Boolean> cuentaBorrada = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        repository = new UsuarioRepository(application);
    }

    public void actualizarPass(int uid, String nuevaPass) {
        if (nuevaPass.isEmpty()) {
            mensaje.setValue("La contraseña no puede estar vacía");
            return;
        }
        repository.cambiarContrasena(uid, nuevaPass);
        mensaje.setValue("Contraseña actualizada correctamente");
    }

    public void eliminarCuenta(int uid) {
        repository.borrarUsuario(uid);
        cuentaBorrada.setValue(true);
    }


    public LiveData<List<UsuarioEntity>> obtenerTodosLosUsuarios() {
        return repository.obtenerTodos();
    }
}