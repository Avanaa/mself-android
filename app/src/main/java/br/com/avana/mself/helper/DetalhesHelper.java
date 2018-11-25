package br.com.avana.mself.helper;

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

    public ItemPedidoModel setItem(ItemModel itemModel){

        ItemPedidoModel itemPedido = createItemPedidoByItemModel(itemModel);

        ImageView imgView = activity.findViewById(R.id.detalhes_imagem);
        new LoadImgByURLTask(imgView).execute(itemPedido.getImage());

        TextView txtViewdescricao = activity.findViewById(R.id.detalhes_descricao);
        txtViewdescricao.setText(itemPedido.getDescricao());

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

    private ItemPedidoModel createItemPedidoByItemModel(ItemModel itemModel) {
        ItemPedidoModel itemPedidoModel = new ItemPedidoModel();
        itemPedidoModel.setTitulo(itemModel.getTitulo());
        itemPedidoModel.setDescricao(itemModel.getDescricao());
        itemPedidoModel.setPreco(itemModel.getPreco());
        itemPedidoModel.setImage(itemModel.getImage());
        itemPedidoModel.setCategoria(itemModel.getCategoria());
        itemPedidoModel.setItemKey(itemModel.getKey());
        return itemPedidoModel;
    }
}
