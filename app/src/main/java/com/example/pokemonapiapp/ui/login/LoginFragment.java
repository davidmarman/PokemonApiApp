package com.example.pokemonapiapp.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.pokemonapiapp.R;
import com.example.pokemonapiapp.databinding.FragmentLoginBinding;
import com.example.pokemonapiapp.datos.local.entities.UsuarioEntity;
import com.example.pokemonapiapp.util.Sesion;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = FragmentLoginBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        final NavController navController = Navigation.findNavController(view);


        // Boton entrar
        binding.btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = binding.etUsuario.getText().toString().trim();
                String pass = binding.etPass.getText().toString().trim();
                loginViewModel.iniciarSesion(usuario, pass);
            }
        });

        // Botón registrarse
        binding.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = binding.etUsuario.getText().toString().trim();
                String pass = binding.etPass.getText().toString().trim();
                loginViewModel.registrarse(usuario, pass);
            }
        });

        // observar mensajes de error
        loginViewModel.mensajeError.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mensaje) {
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        });

        // observar si el login fue exitoso
        loginViewModel.usuarioLogueado.observe(getViewLifecycleOwner(), new Observer<UsuarioEntity>() {
            @Override
            public void onChanged(UsuarioEntity usuarioEntity) {
                if (usuarioEntity != null) {
                    // Guardar sesión
                    Sesion.userId = usuarioEntity.uid;
                    Sesion.username = usuarioEntity.nombreUsuario;

                    Toast.makeText(requireContext(), "Bienvenido " + usuarioEntity.nombreUsuario, Toast.LENGTH_SHORT).show();

                    // Navegar a la lista
                    navController.navigate(R.id.listaPokemonFragment);
                }
            }
        });



        loginViewModel.registroExitoso.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean exito) {
                if (exito) {
                    binding.etPass.setText(""); // Limpiar campo
                }
            }
        });

        // CONTROL DEL BOTÓN ATRÁS (Obligar a salir de la App)
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Esto cierra la Actividad principal, cerrando la app por completo
                requireActivity().finish();
            }
        });
    }

}