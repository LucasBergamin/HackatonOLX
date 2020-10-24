package com.example.projetoolx.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoolx.Model.Categoria;
import com.example.projetoolx.R;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyViewHolder> {

    private List<Categoria> listaCategorias;

    public CategoriaAdapter(List<Categoria> c) {
        this.listaCategorias = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categoria, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Categoria categoria = listaCategorias.get(position);
        holder.texto.setText(categoria.getNome());
        holder.img.setImageResource(categoria.getImagem());

    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView texto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            texto = itemView.findViewById(R.id.TextCategoria);
            img = itemView.findViewById(R.id.iconeImg);
        }
    }
}
