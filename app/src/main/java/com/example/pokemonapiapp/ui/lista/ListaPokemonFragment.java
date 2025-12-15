package com.example.pokemonapiapp.ui.lista;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.example.pokemonapiapp.R;
import com.example.pokemonapiapp.databinding.FragmentListaPokemonBinding;
import com.example.pokemonapiapp.datos.local.entities.PokemonEntity;
import com.example.pokemonapiapp.util.Sesion;

import java.util.List;

public class ListaPokemonFragment extends Fragment {

    private FragmentListaPokemonBinding binding;
    private ListaPokemonViewModel viewModel;
    private PokemonAdapter adapter;
    private int userId;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListaPokemonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ListaPokemonViewModel.class);
        navController = Navigation.findNavController(view);

        // recuperar ID del usuario
        userId = Sesion.userId;

        if (userId == -1) {
            // Error de sesión, volver al login
            navController.navigate(R.id.loginFragment);
            return;
        }

        // configurar RecyclerView
        adapter = new PokemonAdapter();

        binding.recyclerView.setAdapter(adapter);


        // observar cambios en la lista
        viewModel.obtenerPokemons(userId).observe(getViewLifecycleOwner(), new Observer<List<PokemonEntity>>() {
            @Override
            public void onChanged(List<PokemonEntity> pokemons) {
                if (pokemons.isEmpty()) {
                    binding.tvEmptyState.setVisibility(View.VISIBLE); // Mostrar "No tienes pokemons"
                    binding.recyclerView.setVisibility(View.GONE);
                } else {
                    binding.tvEmptyState.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    adapter.setLista(pokemons);
                }
                // Recalcular poder cada vez que la lista cambie
                viewModel.actualizarPoderTotal(userId);
            }
        });

        // observar poder total
        viewModel.poderTotal.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer total) {
                binding.tvTotalPower.setText("Poder de Equipo: " + total);
            }
        });

        // Botón Capturar
        binding.fabCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_listaPokemonFragment_to_capturaFragment);
            }
        });


        // boton perfiles
        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_listaPokemonFragment_to_perfilFragment);
            }
        });



        // click en un item
        adapter.setOnItemClickListener(new PokemonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PokemonEntity pokemon) {
                Bundle bundle = new Bundle();
                bundle.putInt("pokemon_api_id", pokemon.apiId); // Pasamos el apiId

                navController.navigate(R.id.action_listaPokemonFragment_to_detalleFragment, bundle);
            }
        });

        // CONTROL DEL BOTÓN ATRÁS
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // En lugar de volver atrás en la pila, cerramos la actividad (la app)
                requireActivity().finish();
            }
        });

    }
}