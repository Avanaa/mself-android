package br.com.avana.mself.model;

import java.io.Serializable;

public class ItemModel implements Serializable {

    public enum Disponivel{ SIM, NAO }

    public enum Categoria {
        EXECUTIVOS,
        PEIXES,
        CARNES,
        SALADAS,
        SANDUICHES,
        PETISCOS,
        SOBREMESAS,
        REFRIGERANTES,
        CERVEJAS,
        VINHOS,
        DOSES
    }

    private String key;
    private String titulo;
    private String descricao;
    private double valor;
    private double desconto;
    private double preco;
    private String image;
    private Disponivel disponivel;
    private Categoria categoria;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getPreco() { return preco; }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imagem) {
        this.image = imagem;
    }

    public String getDisponivel() { return (disponivel != null) ? disponivel.name() : ""; }

    public void setDisponivel(String disponivel) {
        if (disponivel != null && !disponivel.equals("")){
            this.disponivel = Disponivel.valueOf(disponivel);
        }
    }

    public String getCategoria() { return (categoria != null) ? categoria.name() : ""; }

    public void setCategoria(String categoria) {
        if (categoria != null && !categoria.equals("")){
            this.categoria = Categoria.valueOf(categoria);
        }
    }

    @Override
    public boolean equals(Object object) {
        ItemModel o = (ItemModel) object;
        return (getKey() != null) && this.getKey().equals(o.getKey());
    }
}
