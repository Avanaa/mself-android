package br.com.avana.mself.helper;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.avana.mself.DetalhesActivity;
import br.com.avana.mself.R;
import br.com.avana.mself.async.LoadImgByURLTask;
import br.com.avana.mself.model.ItemPedidoModel;

public class DetalhesHelper {

    private DetalhesActivity activity;

    public DetalhesHelper(DetalhesActivity activity){
        this.activity = activity;
    }

    public void setItem(ItemPedidoModel itemPedido){

        ImageView imgView = activity.findViewById(R.id.detalhes_imagem);
        new LoadImgByURLTask(imgView).execute(itemPedido.getItem().getImage());

        TextView txtViewdescricao = activity.findViewById(R.id.detalhes_descricao);
        txtViewdescricao.setText(itemPedido.getItem().getDescricao());

        TextView txtViewPreco = activity.findViewById(R.id.detalhes_preco);
        txtViewPreco.setText(String.format(activity.getString(R.string.moeda),
                String.valueOf(itemPedido.getItem().getPreco())));

        Button btnQuantidade = activity.findViewById(R.id.detalhes_btn_quantidade);
        btnQuantidade.setText(String.valueOf(itemPedido.getQuantidade()));

        TextView precoTotal = activity.findViewById(R.id.detalhes_total_preco);
        precoTotal.setText(String.valueOf(itemPedido.getPreco()));
    }
}
