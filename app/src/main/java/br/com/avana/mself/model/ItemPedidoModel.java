package br.com.avana.mself.model;

import java.io.Serializable;

public class ItemPedidoModel implements Serializable {

    public enum Status { CRIADO, ENVIADO, EM_PREPARACAO, PRONTO, ENTREGUE, CONFIRMADO_ENTREGUE, CANCELADO }

    private String key;
    private String mesa;
    private ItemModel item;
    private int quantidade;
    private double preco;
    private String observacoes;
    private ItemPedidoModel.Status status;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMesa() { return mesa; }

    public void setMesa(String mesa) { this.mesa = mesa; }

    public ItemModel getItem() {
        return item;
    }

    public void setItem(ItemModel item) {
        this.item = item;
    }

    public int getQuantidade() {
        if (quantidade == 0){
            quantidade = 1;
        }
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        preco = getItem().getPreco() * getQuantidade();
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getObservacoes() { return observacoes; }

    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getStatus() { return status.name(); }

    public void setStatus(String status) { this.status = ItemPedidoModel.Status.valueOf(status); }

    @Override
    public boolean equals(Object obj) {
        ItemPedidoModel o = (ItemPedidoModel) obj;
        return this.getKey().equals(o.getKey());
    }

}
