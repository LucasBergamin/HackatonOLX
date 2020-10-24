package com.example.projetoolx.ui.Anuncio;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.projetoolx.Activity.DadosVendaActivity;
import com.example.projetoolx.Adapter.CategoriaAdapter;
import com.example.projetoolx.Model.Categoria;
import com.example.projetoolx.R;

import java.util.ArrayList;
import java.util.List;

public class AnunciosFragment extends Fragment {

    private ImageView imageView;


    public RecyclerView recyclerCategoria;
    private List<Categoria> categorias = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_anuncios, container, false);

        imageView = v.findViewById(R.id.imgA10);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DadosVendaActivity.class);
                startActivity(intent);
            }
        });

        recyclerCategoria = v.findViewById(R.id.recyclerCategoria);

        recyclerCategoria.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        this.prepararCategoria();
        CategoriaAdapter categoriaAdapter = new CategoriaAdapter(categorias);
        recyclerCategoria.setAdapter( categoriaAdapter);

        return v;
    }

    public void prepararCategoria(){
        Categoria categoria = new Categoria("celulares", R.drawable.eletronicos);
        this.categorias.add(categoria);

        categoria = new Categoria("Eletronicos", R.drawable.pc);
        this.categorias.add(categoria);

        categoria = new Categoria("Casas", R.drawable.casa);
        this.categorias.add(categoria);

        categoria = new Categoria("Automoveis", R.drawable.automovel);
        this.categorias.add(categoria);

        categoria = new Categoria("Para a sua casa", R.drawable.paracasa);
        this.categorias.add(categoria);

        categoria = new Categoria("Esportes e lazer", R.drawable.esporteslazer);
        this.categorias.add(categoria);

        categoria = new Categoria("Moda e beleza", R.drawable.modabeleza);
        this.categorias.add(categoria);
    }
}