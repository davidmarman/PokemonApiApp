package com.example.pokemonapiapp.ui.detalle;

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

import com.bumptech.glide.Glide;
import com.example.pokemonapiapp.R;
import com.example.pokemonapiapp.databinding.FragmentDetalleBinding;
import com.example.pokemonapiapp.datos.network.respuesta.Respuesta;

public class DetalleFragment extends Fragment {

    private FragmentDetalleBinding binding;
    private DetalleViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DetalleViewModel.class);

        // recibimos el id del pokemon pasado en el bundle
        if (getArguments() != null) {
            int apiId = getArguments().getInt("pokemon_api_id");

            // Pedir datos frescos a la API
            viewModel.cargarDetalle(apiId);
        }

        // observamos la respuesta
        viewModel.pokemonDetalle.observe(getViewLifecycleOwner(), new Observer<Respuesta>() {
            @Override
            public void onChanged(Respuesta pokemon) {
                // Ocultar carga y mostrar contenido
                binding.progressBar.setVisibility(View.GONE);
                binding.groupContent.setVisibility(View.VISIBLE);

                // Llenar datos
                binding.tvDetailName.setText(pokemon.getName());
                String tipoStr = pokemon.getTypes().get(0).type.name; // Guardamos el tipo
                binding.tvDetailType.setText("Tipo: " + tipoStr.toUpperCase());
                binding.tvDetailPower.setText("Poder Base: " + pokemon.getBaseExperience());
                binding.tvDetailId.setText("ID Pokedex: #" + pokemon.getId());

                // Cargar imagen
                Glide.with(DetalleFragment.this)
                        .load(pokemon.getSprites().other.officialArtwork.frontDefault)
                        .into(binding.ivDetailImage);

                // Switch para el fondo de color
                switch (tipoStr.toLowerCase()) {
                    case "normal":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_normal);
                        break;
                    case "fire":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_fire);
                        break;
                    case "water":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_water);
                        break;
                    case "electric":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_electric);
                        break;
                    case "grass":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_grass);
                        break;
                    case "ice":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_ice);
                        break;
                    case "fighting":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_fighting);
                        break;
                    case "poison":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_poison);
                        break;
                    case "ground":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_ground);
                        break;
                    case "flying":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_flying);
                        break;
                    case "psychic":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_psychic);
                        break;
                    case "bug":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_bug);
                        break;
                    case "rock":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_rock);
                        break;
                    case "ghost":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_ghost);
                        break;
                    case "dragon":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_dragon);
                        break;
                    case "steel":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_steel);
                        break;
                    case "dark":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_dark);
                        break;
                    case "fairy":
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_fairy);
                        break;
                    default:
                        binding.layoutTopBackground.setBackgroundResource(R.drawable.gradient_normal);
                        break;
                }
            }
        });

        // manejo de errores
        viewModel.error.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String msg) {

                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Error: " + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}