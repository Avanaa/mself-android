package br.com.avana.mself.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoModel {

    public enum Status { CRIADO, ENVIADO, EM_PREPARACAO, PRONTO, ENTREGUE, CONFIRMADO_ENTREGUE, CANCELADO }

    private String mesa;
    private List<ItemPedidoModel> itens;
    private String hora;
    private String valorTotal;
    private Status status;

    public PedidoModel(){ itens = new ArrayList<>(); }

    public void addItem(ItemPedidoModel item){ getItens().add(item); }

    public String getMesa() { return mesa; }

    public void setMesa(String mesa) { this.mesa = mesa; }

    public List<ItemPedidoModel> getItens() { return itens; }

    public void setItens(List<ItemPedidoModel> pratos) {
        this.itens = pratos;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }
}
