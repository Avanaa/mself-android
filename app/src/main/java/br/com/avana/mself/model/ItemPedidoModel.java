package br.com.avana.mself.model;

import java.io.Serializable;

public class ItemPedidoModel extends ItemModel implements Serializable {

    public enum Status { CRIADO, ENVIADO, EM_PREPARACAO, PRONTO, ENTREGUE, CONFIRMADO_ENTREGUE, CANCELADO }

    private String ItemKey;
    private double precoPedido;
    private String mesa;
    private int quantidade;
    private String observacoes;
    private ItemPedidoModel.Status status;

    public String getItemKey() {
        return ItemKey;
    }

    public void setItemKey(String ItemKey) {
        this.ItemKey = ItemKey;
    }

    public String getMesa() { return mesa; }

    public void setMesa(String mesa) { this.mesa = mesa; }

    public int getQuantidade() {
        if (quantidade == 0){
            quantidade = 1;
        }
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoPedido() {
        precoPedido = getPreco() * getQuantidade();
        return precoPedido;
    }

    public void setPrecoPedido(double precoPedido) {
        this.precoPedido = precoPedido;
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
