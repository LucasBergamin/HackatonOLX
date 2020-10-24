package com.example.projetoolx.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoolx.Config.configuracaoFirabase;
import com.example.projetoolx.Helper.Base64Custom;
import com.example.projetoolx.Model.Usuario;
import com.example.projetoolx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText editNome, editAniversario, editEmail, editSenha, editCpf, editTelefone;
    private Button btnCadastrar;
    private FirebaseAuth autentificacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editNome);
        editAniversario = findViewById(R.id.editData);
        editEmail = findViewById(R.id.editEmailCadastro);
        editSenha = findViewById(R.id.editSenha);
        editCpf = findViewById(R.id.editCpf);
        editTelefone = findViewById(R.id.editTelefone);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = editNome.getText().toString();
                String aniversario = editAniversario.getText().toString();
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                String cpf = editCpf.getText().toString();
                String telefone = editTelefone.getText().toString();

                if(!nome.isEmpty()){
                    if(!aniversario.isEmpty()){
                        if(!email.isEmpty()){
                            if(!senha.isEmpty()){
                                if(!cpf.isEmpty()){
                                    if((!telefone.isEmpty())){

                                        usuario = new Usuario();

                                        usuario.setNome(nome);
                                        usuario.setAniversario(aniversario);
                                        usuario.setEmail(email);
                                        usuario.setSenha(senha);
                                        usuario.setCpf(cpf);
                                        usuario.setTelefone(telefone);

                                        cadastrarUsuario();

                                    }else{
                                        Toast.makeText(getApplicationContext(), "Preencha o telefone", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "preencha o CPF!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Preencha a senha!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Preencha o email!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Preencha a data de aniversario!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha o nome!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cadastrarUsuario(){
        autentificacao = configuracaoFirabase.getFirebaseAutentificacao();
        autentificacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();
                    finish();
                }else{
                    String exececao = "";
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        exececao = "Digite uma senha mais forte!";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        exececao = "Digite um email válido";
                    }catch(FirebaseAuthUserCollisionException e){
                        exececao = "Esta conta já foi cadastrada";
                    }catch(Exception e){
                        exececao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), exececao , Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}