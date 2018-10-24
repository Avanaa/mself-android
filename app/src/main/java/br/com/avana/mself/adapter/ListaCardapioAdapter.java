package br.com.avana.mself.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import br.com.avana.mself.R;
import br.com.avana.mself.async.LoadImgByURLTask;
import br.com.avana.mself.model.PratoModel;

public class ListaCardapioAdapter extends BaseAdapter {

    private final List<PratoModel> cardapio;
    private Activity activity;
    private StorageReference mStorageRef;

    public ListaCardapioAdapter(List<PratoModel> cardapio, Activity activity){
        this.cardapio = cardapio;
        this.activity = activity;
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public int getCount() {
        return cardapio.size();
    }

    @Override
    public PratoModel getItem(int position) {
        return cardapio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View itemView
                = activity.getLayoutInflater().inflate(R.layout.item_cardapio, parent, false);

        PratoModel pratoModel = cardapio.get(position);

        TextView titulo = itemView.findViewById(R.id.item_cardapio_titulo);
        titulo.setText(pratoModel.getTitulo());

        TextView descricao = itemView.findViewById(R.id.item_cardapio_descricao);
        descricao.setText(pratoModel.getDescricao());

        TextView preco = itemView.findViewById(R.id.item_cardapio_preco);
        preco.setText("R$ " + pratoModel.getPreco());

        ImageView imagem = itemView.findViewById(R.id.item_cardapio_image);
        new LoadImgByURLTask(imagem).execute(pratoModel.getImage());

        return  itemView;
    }
}
