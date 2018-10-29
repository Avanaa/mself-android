package br.com.avana.mself.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.avana.mself.CarrinhoActivity;
import br.com.avana.mself.R;
import br.com.avana.mself.async.LoadImgByURLTask;
import br.com.avana.mself.model.ItemPedidoModel;

public class ListaItensCarrinhoAdapter extends RecyclerView.Adapter<ListaItensCarrinhoAdapter.ViewItemHolder> {

    private List<ItemPedidoModel> itensCarrinho;
    private CarrinhoActivity activity;

    public ListaItensCarrinhoAdapter(List<ItemPedidoModel> itensCarrinho, CarrinhoActivity activity) {
        this.itensCarrinho = itensCarrinho;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        ItemPedidoModel itemPedido = itensCarrinho.get(position);

        final ViewItemHolder viewItemHolder = new ViewItemHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrinho, parent, false),
                itemPedido);

        viewItemHolder.getButtonRemover().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.removeItemCarrinho(itensCarrinho.get(viewItemHolder.getAdapterPosition()));
            }
        });

        return viewItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewItemHolder viewItemHolder, int position) {
        ItemPedidoModel itemPedido = itensCarrinho.get(position);
        viewItemHolder.setValuesByItem(itemPedido);
    }

    @Override
    public int getItemCount() {
        return itensCarrinho.size();
    }

    public class ViewItemHolder extends RecyclerView.ViewHolder {

        private View itemCarrinho;
        private ItemPedidoModel itemPedido;

        public ViewItemHolder(View itemView, ItemPedidoModel itemPedido) {
            super(itemView);

            this.itemCarrinho = itemView;
            this.itemPedido = itemPedido;
            setValues();
        }

        @SuppressLint("DefaultLocale")
        private void setValues(){

            new LoadImgByURLTask(getImageView()).execute(itemPedido.getItem().getImage());
            getTextViewTitulo().setText(itemPedido.getItem().getTitulo());
            getTextViewQuantidade().setText(String.format("Quantidade %d", itemPedido.getQuantidade()));
            getTextViewTotal().setText(String.format("Total: R$ %3.2f", itemPedido.getPreco()));
        }

        private void setValuesByItem(ItemPedidoModel item){
            itemPedido = item;
            setValues();
        }

        private ImageView getImageView(){
            return (ImageView) itemCarrinho.findViewById(R.id.item_carrinho_imagem);
        }

        private TextView getTextViewTitulo(){ return (TextView) itemCarrinho.findViewById(R.id.carrinho_item_titulo); }

        private TextView getTextViewQuantidade(){ return (TextView) itemCarrinho.findViewById(R.id.carrinho_item_quantidade); }

        private TextView getTextViewTotal(){ return (TextView) itemCarrinho.findViewById(R.id.carrinho_item_total); }

        private ImageButton getButtonRemover(){
            return (ImageButton) itemCarrinho.findViewById(R.id.item_carrinho_remove);
        }

    }
}
