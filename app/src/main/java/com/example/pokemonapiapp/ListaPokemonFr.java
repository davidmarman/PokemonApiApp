package com.example.pokemonapiapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.example.pokemonapiapp.databinding.FragmentListaPokemonBinding;
import com.example.pokemonapiapp.databinding.PokemonViewholderBinding;
import com.example.pokemonapiapp.datos.Contenido;
import com.example.pokemonapiapp.datos.PokemonViewModel;
import com.example.pokemonapiapp.datos.Respuesta;
import com.example.pokemonapiapp.datos.network.Pokemon;

import java.util.List;


public class ListaPokemonFr extends Fragment {

    private FragmentListaPokemonBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = FragmentListaPokemonBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        PokemonViewModel pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        //Establecemos el Adaptador al RecyclerView:
        ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
        binding.recyclerviewContenidos.setAdapter(contenidosAdapter);

        // CAMBIO: Observamos "listaPokemonLiveData" que ahora trae List<Contenido>
        pokemonViewModel.listaPokemonLiveData.observe(getViewLifecycleOwner(), new Observer<List<Contenido>>() {
            @Override
            public void onChanged(List<Contenido> contenidos) {
                // Actualizo el adaptador con la lista ya procesada
                contenidosAdapter.establecerListaContenido(contenidos);
            }
        });



        binding.texto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    // CAMBIO: Usar parseInt
                    pokemonViewModel.buscar(Integer.parseInt(s));
                } catch (NumberFormatException e) {
                    // Manejar el caso si el usuario escribe letras
                }
                return false;
            }
        });



    }

    /*******************************************************************+
     HOLDER
     ************************************************************************/

    static class PokemonViewHolder extends RecyclerView.ViewHolder {
        PokemonViewholderBinding binding;

        public PokemonViewHolder(@NonNull PokemonViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    /*******************************************************************+
     **  ADAPTADOR
     ************************************************************************/

    class ContenidosAdapter extends RecyclerView.Adapter<PokemonViewHolder>{
        List<Contenido> contenidoList;

        @NonNull
        @Override
        public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PokemonViewHolder(PokemonViewholderBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
            Contenido contenido = contenidoList.get(position);

            holder.binding.title.setText(contenido.getNombrePokemon());
            holder.binding.artist.setText(contenido.getTipoPokemon());
            Glide.with(requireActivity()).load(contenido.getArtworkUrl100()).into(holder.binding.artwork);
        }

        @Override
        public int getItemCount() {
            return contenidoList == null ? 0 : contenidoList.size();
        }

        void establecerListaContenido(List<Contenido> contenidoList){
            this.contenidoList = contenidoList;
            notifyDataSetChanged();
        }
    }

}