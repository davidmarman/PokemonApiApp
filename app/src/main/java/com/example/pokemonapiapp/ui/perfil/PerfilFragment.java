package com.example.pokemonapiapp.ui.perfil;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pokemonapiapp.R;
import com.example.pokemonapiapp.datos.local.entities.UsuarioEntity;
import com.example.pokemonapiapp.databinding.FragmentPerfilBinding;
import com.example.pokemonapiapp.util.Sesion;

import java.util.List;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel viewModel;
    private int currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        currentUserId = Sesion.userId;


        // Configurar recyclerView
        binding.rvUsuarios.setLayoutManager(new LinearLayoutManager(requireContext()));
        UsuariosAdapter adapter = new UsuariosAdapter(new UsuariosAdapter.OnUsuarioActionListener() {
            @Override
            public void onEditPassword(UsuarioEntity usuario) {
                mostrarDialogoCambiarPass(usuario);
            }

            @Override
            public void onDeleteUser(UsuarioEntity usuario) {
                mostrarDialogoBorrar(usuario);
            }
        });
        binding.rvUsuarios.setAdapter(adapter);

        // OBSERVAR LISTA
        viewModel.obtenerTodosLosUsuarios().observe(getViewLifecycleOwner(), new Observer<List<UsuarioEntity>>() {
            @Override
            public void onChanged(List<UsuarioEntity> lista) {
                adapter.setUsuarios(lista);
            }
        });

        // BOTÓN CERRAR SESIÓN
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar datos de la clase estática
                Sesion.cerrarSesion();

                // Volver al Login
                Navigation.findNavController(view).navigate(R.id.action_global_loginFragment);
            }
        });
    }



    private void mostrarDialogoCambiarPass(UsuarioEntity usuario) {
        EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setHint("Nueva contraseña");

        new AlertDialog.Builder(requireContext())
                .setTitle("Cambiar contraseña de " + usuario.nombreUsuario)
                .setView(input)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String newPass = input.getText().toString();
                    if (!newPass.isEmpty()) {
                        viewModel.actualizarPass(usuario.uid, newPass); // Usamos el ID del usuario clickado
                        Toast.makeText(requireContext(), "Contraseña cambiada", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void mostrarDialogoBorrar(UsuarioEntity usuario) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Borrar usuario")
                .setMessage("¿Eliminar a " + usuario.nombreUsuario + " y todos sus Pokémons?")
                .setPositiveButton("Sí, borrar", (dialog, which) -> {
                    viewModel.eliminarCuenta(usuario.uid);

                    // Si me borro a mí mismo, cerrar sesión
                    if (usuario.uid == currentUserId) {
                        Sesion.cerrarSesion();
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_loginFragment);
                    } else {
                        Toast.makeText(requireContext(), "Usuario eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}