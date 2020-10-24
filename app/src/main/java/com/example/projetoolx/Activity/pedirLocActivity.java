package com.example.projetoolx.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetoolx.R;

public class pedirLocActivity extends AppCompatActivity {

    public Button btnPedLoc;
    public static final String PREFERENCIA = "Preferencia";
    public String confirmar = "não";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_loc);
        btnPedLoc = findViewById(R.id.btnDefLoc);

        btnPedLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettingsAlert();
            }
        });
    }

    public void showSettingsAlert(){
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(pedirLocActivity.this);
        // Titulo do dialogo
        alertDialog.setTitle("GPS");

        // Mensagem do dialogo
        alertDialog.setMessage("Dejesa compartilhar informação de localização?");

        // botao ajustar configuracao
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                salvarPreferencia("Sim");
                Intent intent = new Intent(getApplicationContext(), TelaPrincipalProdutosActivity.class);
                startActivity(intent);
            }
        });

        // botao cancelar
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                salvarPreferencia("Não");
                dialog.cancel();
                finishAffinity();

            }
        });

        // visualizacao do dialogo
        alertDialog.show();
    }

    public void salvarPreferencia(String aceitando){
        SharedPreferences preferences = getSharedPreferences(PREFERENCIA, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("aceitando", aceitando);
        editor.commit();
    }
}