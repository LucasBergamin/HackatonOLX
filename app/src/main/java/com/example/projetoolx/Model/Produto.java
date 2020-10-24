package com.example.projetoolx.Model;

import android.graphics.Bitmap;

import com.example.projetoolx.Config.configuracaoFirabase;
import com.google.firebase.database.DatabaseReference;

public class Produto {

    private String titulo;
    private String valor;
    private String descricao;
    private String categoria;
    private Bitmap imagem;
    private String estado;
    private String cidade;

    public Produto() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public void salvar(){
        Categoria categoria = new Categoria();
        String nome = categoria.getNome();
        DatabaseReference reference = configuracaoFirabase.getFirabaseDatasabe();
        reference.child("Produtos")
                .child(this.titulo)
                .setValue(this);
    }
}
