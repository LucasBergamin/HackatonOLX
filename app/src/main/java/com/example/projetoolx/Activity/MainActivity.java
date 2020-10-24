package com.example.projetoolx.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoolx.Config.configuracaoFirabase;
import com.example.projetoolx.Model.Usuario;
import com.example.projetoolx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import static com.example.projetoolx.Activity.pedirLocActivity.PREFERENCIA;

public class MainActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button btnLogar, btnCadastrar;
    private Usuario usuario;
    private FirebaseAuth autentificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        btnLogar = findViewById(R.id.btnLogin);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if(!textoEmail.isEmpty()){
                    if(!textoSenha.isEmpty()){

                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin();

                    }else{
                        Toast.makeText(getApplicationContext(), "Preencha a senha!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha o Email!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });

    }

    public void validarLogin(){
        autentificacao = configuracaoFirabase.getFirebaseAutentificacao();
        autentificacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), pedirLocActivity.class));
                    finish();
                }else{
                    String exececao = "";
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthInvalidUserException e){
                        exececao = "Usuário não cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        exececao = "Email ou senha não correspondem a um usuário cadastrado";
                    }catch(Exception e){
                        exececao = "Erro ao logar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), exececao, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void verificarUsuarioLogado(){
        autentificacao = configuracaoFirabase.getFirebaseAutentificacao();
        if(autentificacao.getCurrentUser() != null){
            if("Não".equals(recuperarPreferencia())){
                startActivity(new Intent(this, pedirLocActivity.class));
                finish();
            }else{
                startActivity(new Intent(this, TelaPrincipalProdutosActivity.class));
                finish();
            }
        }
    }

    public String recuperarPreferencia(){
        SharedPreferences preferences = getSharedPreferences(PREFERENCIA, 0);
        if(preferences.contains("aceitando")){
            String aceitando = preferences.getString("aceitando", "não");
            return aceitando;
        }
        return "Não";
    }
}