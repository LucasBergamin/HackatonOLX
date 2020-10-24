package com.example.projetoolx.ui.CriarAnuncio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetoolx.Model.Categoria;
import com.example.projetoolx.Model.Produto;
import com.example.projetoolx.Model.Usuario;
import com.example.projetoolx.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class CriarAnunciosFragment extends Fragment {

    private Spinner spinnerCategoria;
    private ImageView escolherImg;
    private Uri imageUri;
    private Button btnCriarAnuncio;
    private EditText textTitulo, textValor, textDescricao, textEstado, textCidade;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_criar_anuncios, container, false);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        textTitulo = v.findViewById(R.id.txtTitulo);
        textValor = v.findViewById(R.id.txtValor);
        textDescricao = v.findViewById(R.id.txtdescricao);
        textEstado = v.findViewById(R.id.textEstado);
        textCidade = v.findViewById(R.id.textCidade);

        spinnerCategoria = v.findViewById(R.id.spinnerCategoria);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categoria, android.R.layout.simple_spinner_item);
        spinnerCategoria.setAdapter(adapter);

        escolherImg = v.findViewById(R.id.escolherImg);
        escolherImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegarFoto();
            }
        });

        btnCriarAnuncio = v.findViewById(R.id.btnCriarAnuncio);
        btnCriarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String titulo = textTitulo.getText().toString();
                String valor = textValor.getText().toString();
                String descricao = textDescricao.getText().toString();
                String categoria = spinnerCategoria.getSelectedItem().toString();
                String estado = textEstado.getText().toString();
                String cidade = textCidade.getText().toString();


                if (!titulo.isEmpty()) {
                    if (!valor.isEmpty()) {
                        if (!descricao.isEmpty()) {
                            if (!estado.isEmpty()) {
                                if ((!cidade.isEmpty())) {

                                    Produto produto = new Produto();

                                    produto.setTitulo(titulo);
                                    produto.setValor(valor);
                                    produto.setDescricao(descricao);
                                    produto.setCategoria(categoria);
                                    produto.setEstado(estado);
                                    produto.setCidade(cidade);

                                    produto.salvar();
                                    Toast.makeText(getContext(), "Produto cadastrado", Toast.LENGTH_SHORT).show();
                                    textTitulo.setText("");
                                    textValor.setText("");
                                    textDescricao.setText("");
                                    textEstado.setText("");
                                    textCidade.setText("");
                                } else {
                                    Toast.makeText(getContext(), "Preencha o estado!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Preencha a descricao", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Preencha o valor!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Preencha o titulo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return v;
    }

    public void pegarFoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && requestCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            uploadPicture();
        }
    }

    public void uploadPicture(){
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Pegando imagem...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/rivers.jpg");

        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(textValor.findViewById(android.R.id.content), "Image Uplpaded", Snackbar.LENGTH_LONG).show();
            }
        })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(getContext(), "Falha na imagem", Toast.LENGTH_LONG).show();
                }
            });
    }

}