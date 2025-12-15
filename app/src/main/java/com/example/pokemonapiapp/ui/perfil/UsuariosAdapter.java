package com.example.pokemonapiapp.ui.perfil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapiapp.R;
import com.example.pokemonapiapp.datos.local.entities.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuarioViewHolder> {

    private List<UsuarioEntity> usuarios = new ArrayList<>();
    private final OnUsuarioActionListener listener;

    public interface OnUsuarioActionListener {
        void onEditPassword(UsuarioEntity usuario);
        void onDeleteUser(UsuarioEntity usuario);
    }

    public UsuariosAdapter(OnUsuarioActionListener listener) {
        this.listener = listener;
    }

    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        UsuarioEntity usuario = usuarios.get(position);
        holder.tvName.setText(usuario.nombreUsuario);

        // Botón Editar
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditPassword(usuario);
            }
        });

        // Botón Borrar
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteUser(usuario);
            }
        });
    }

    @Override
    public int getItemCount() { return usuarios.size(); }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton btnEdit, btnDelete;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvUserName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}