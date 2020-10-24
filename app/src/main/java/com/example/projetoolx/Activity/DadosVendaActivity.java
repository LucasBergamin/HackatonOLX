package com.example.projetoolx.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetoolx.R;

public class DadosVendaActivity extends AppCompatActivity {

    private Button btnEncontrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_venda);

        btnEncontrar = findViewById(R.id.btnEncontrarEntregador);
        btnEncontrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EncontrarEntregadorActivity.class);
                startActivity(intent);
            }
        });
    }
}