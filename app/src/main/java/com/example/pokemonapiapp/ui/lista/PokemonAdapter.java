package com.example.pokemonapiapp.ui.lista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokemonapiapp.R;
import com.example.pokemonapiapp.datos.local.entities.PokemonEntity;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<PokemonEntity> lista = new ArrayList<>();

    // Listener para cuando hagamos click en un pokemon (para el Detalle)
    public interface OnItemClickListener {
        void onItemClick(PokemonEntity pokemon);
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setLista(List<PokemonEntity> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokemonEntity pokemon = lista.get(position);
        holder.bind(pokemon, listener);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvType, tvPower;
        ConstraintLayout layoutBackground;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivPokemonImage);
            tvName = itemView.findViewById(R.id.tvPokemonName);
            tvType = itemView.findViewById(R.id.tvPokemonType);
            tvPower = itemView.findViewById(R.id.tvPokemonPower);
            layoutBackground = itemView.findViewById(R.id.layoutBackground);
        }

        public void bind(PokemonEntity pokemon, OnItemClickListener listener) {
            tvName.setText(pokemon.nombre);
            tvType.setText(pokemon.tipo);
            tvPower.setText("Poder: " + pokemon.poder);

            // Cargar imagen con Glide
            Glide.with(itemView.getContext())
                    .load(pokemon.imagenUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(ivImage);


            switch (pokemon.tipo.toLowerCase()) {
                case "normal":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_normal);
                    break;
                case "fire":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_fire);
                    break;
                case "water":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_water);
                    break;
                case "electric":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_electric);
                    break;
                case "grass":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_grass);
                    break;
                case "ice":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_ice);
                    break;
                case "fighting":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_fighting);
                    break;
                case "poison":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_poison);
                    break;
                case "ground":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_ground);
                    break;
                case "flying":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_flying);
                    break;
                case "psychic":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_psychic);
                    break;
                case "bug":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_bug);
                    break;
                case "rock":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_rock);
                    break;
                case "ghost":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_ghost);
                    break;
                case "dragon":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_dragon);
                    break;
                case "steel":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_steel);
                    break;
                case "dark":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_dark);
                    break;
                case "fairy":
                    layoutBackground.setBackgroundResource(R.drawable.gradient_fairy);
                    break;
                default:
                    // Por si sale algún tipo nuevo o "unknown"
                    layoutBackground.setBackgroundResource(R.drawable.gradient_normal);
                    break;
            }


            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(pokemon);
            });
        }
    }
}