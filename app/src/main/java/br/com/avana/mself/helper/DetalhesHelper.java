package br.com.avana.mself.helper;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.avana.mself.DetalhesActivity;
import br.com.avana.mself.R;
import br.com.avana.mself.async.LoadImgByURLTask;
import br.com.avana.mself.model.ItemModel;
import br.com.avana.mself.model.ItemPedidoModel;

public class DetalhesHelper {

    private DetalhesActivity activity;

    public DetalhesHelper(DetalhesActivity activity){
        this.activity = activity;
    }

    public ItemPedidoModel setItem(ItemModel itemModel, String itemKey){

        ItemPedidoModel itemPedido = createItemPedidoByItemModel(itemModel, itemKey);

        ImageView imgView = activity.findViewById(R.id.detalhes_imagem);
        new LoadImgByURLTask(imgView).execute(itemPedido.getImage());

        TextView txtViewdescricao = activity.findViewById(R.id.detalhes_descricao);
        txtViewdescricao.setText(itemPedido.getDescricao());

        Button btnQuantidade = activity.findViewById(R.id.detalhes_btn_quantidade);
        btnQuantidade.setText(String.valueOf(itemPedido.getQuantidade()));

        setPreco(itemPedido);

        return itemPedido;
    }

    public void setPreco(ItemPedidoModel itemPedido) {
        TextView txtViewPreco = activity.findViewById(R.id.detalhes_preco);
        txtViewPreco.setText(String.format(activity.getString(R.string.moeda),
                String.valueOf(itemPedido.getPreco())));

        TextView precoTotal = activity.findViewById(R.id.detalhes_total_preco);
        precoTotal.setText(String.valueOf(itemPedido.getPrecoPedido()));
    }

    private ItemPedidoModel createItemPedidoByItemModel(ItemModel itemModel, String itemKey) {
        ItemPedidoModel itemPedidoModel = new ItemPedidoModel();
        itemPedidoModel.setTitulo(itemModel.getTitulo());
        itemPedidoModel.setDescricao(itemModel.getDescricao());
        itemPedidoModel.setPreco(itemModel.getPreco());
        itemPedidoModel.setImage(itemModel.getImage());
        itemPedidoModel.setCategoria(itemModel.getCategoria());

        if (itemKey != null){
            itemPedidoModel.setItemKey(itemKey);
        } else {
            itemPedidoModel.setItemKey(itemModel.getKey());
        }
        return itemPedidoModel;
    }
}
