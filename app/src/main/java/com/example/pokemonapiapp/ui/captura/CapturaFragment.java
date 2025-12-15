package com.example.pokemonapiapp.ui.captura;

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

import com.bumptech.glide.Glide;
import com.example.pokemonapiapp.datos.network.respuesta.Respuesta;
import com.example.pokemonapiapp.databinding.FragmentCapturaBinding;
import com.example.pokemonapiapp.util.Sesion;

public class CapturaFragment extends Fragment {

    private FragmentCapturaBinding binding;
    private CapturaViewModel viewModel;
    private NavController navController;
    private int userId;

    // Variable para guardar el Pokémon mientras esperamos el click del usuario
    private Respuesta pokemonPendiente = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCapturaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CapturaViewModel.class);
        navController = Navigation.findNavController(view);

        userId = Sesion.userId;

        // iniciar Búsqueda
        viewModel.buscarAleatorio();
        binding.tvInstruction.setText("Buscando en la hierba alta...");

        // configuramos el click de animacion
        binding.lottieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pokemonPendiente != null) {
                    revelarPokemon();
                } else {
                    Toast.makeText(requireContext(), "¡Espera! Aún no ha picado nada...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Observamos los datos que nos llegan de la api
        viewModel.pokemonSalvaje.observe(getViewLifecycleOwner(), new Observer<Respuesta>() {
            @Override
            public void onChanged(Respuesta pokemon) {
                pokemonPendiente = pokemon; // Lo guardamos en la variable temporal

                // Cambiamos el texto para avisar al usuario
                binding.tvInstruction.setText("¡Algo se mueve!\nTOCA PARA ABRIR");
                binding.tvInstruction.setTextColor(requireContext().getColor(android.R.color.holo_red_dark));

                // Aumentar velocidad de animación
                binding.lottieView.setSpeed(1.5f);
            }
        });

        // Miramos si podemos capturar el pokemon, miramos el estado que nos pasa el view model
        // Depende lo que ponga, cambiamos la funcion del boton Action
        viewModel.estadoCaptura.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String estado) {
                binding.btnAction.setEnabled(true);

                switch (estado) {
                    case "CAPTURABLE":
                        binding.btnAction.setText("¡LANZAR POKÉBALL!");
                        binding.tvStatusMessage.setText("¡Es tu oportunidad!");

                        // Listener botón Capturar
                        binding.btnAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewModel.realizarCaptura(userId);
                            }
                        });
                        break;

                    case "IMPOSIBLE":
                        binding.btnAction.setText("HUIR");
                        binding.tvStatusMessage.setText("Es demasiado fuerte para ti.");

                        // Listener botón Huir
                        binding.btnAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navController.popBackStack();
                            }
                        });
                        break;

                    case "DUPLICADO":
                        binding.btnAction.setText("YA LO TIENES (SALIR)");
                        binding.tvStatusMessage.setText("Ya tienes a " + pokemonPendiente.getName());

                        // Listener botón Salir
                        binding.btnAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navController.popBackStack();
                            }
                        });
                        break;
                }
            }
        });

        // observamos si la captura fue bien
        viewModel.capturaExitosa.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean exito) {
                if (exito) {
                    Toast.makeText(requireContext(), "¡Capturado!", Toast.LENGTH_LONG).show();
                    navController.popBackStack();
                }
            }
        });
    }


    private void revelarPokemon() {
        if (pokemonPendiente == null) return;

        // Ocultar animación e instrucción
        binding.lottieView.setVisibility(View.GONE);
        binding.tvInstruction.setVisibility(View.GONE);

        // Mostrar tarjeta
        binding.cardPokemon.setVisibility(View.VISIBLE);
        binding.cardPokemon.setAlpha(0f);
        binding.cardPokemon.animate().alpha(1f).setDuration(500).start(); // Pequeño efecto fade-in

        // Pintar datos
        binding.tvWildName.setText(pokemonPendiente.getName());
        binding.tvWildPower.setText("Poder: " + pokemonPendiente.getBaseExperience());
        Glide.with(this).load(pokemonPendiente.getSprites().other.officialArtwork.frontDefault).into(binding.ivWildImage);


        viewModel.verificarPosibilidadCaptura(userId, pokemonPendiente);
    }
}