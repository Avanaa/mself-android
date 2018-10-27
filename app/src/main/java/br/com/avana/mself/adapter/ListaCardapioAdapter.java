package br.com.avana.mself.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.avana.mself.CardapioActivity;
import br.com.avana.mself.DetalhesActivity;
import br.com.avana.mself.R;
import br.com.avana.mself.async.LoadImgByURLTask;
import br.com.avana.mself.model.PratoModel;

public class ListaCardapioAdapter extends RecyclerView.Adapter<ListaCardapioAdapter.ViewItemHolder>  {

    private List<PratoModel> cardapio;
    private Activity activity;

    public ListaCardapioAdapter(List<PratoModel> cardapio, Activity activity){
        this.cardapio = cardapio;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewItemHolder onCreateViewHolder(@NonNull final ViewGroup parent, int i) {

        final PratoModel pratoModel = cardapio.get(i);

        final ViewItemHolder viewItemHolder = new ViewItemHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_cardapio, parent, false),
                pratoModel);

        viewItemHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetalhesActivity.class);
                intent.putExtra("prato", cardapio.get(viewItemHolder.getAdapterPosition()));
                activity.startActivityForResult(intent, CardapioActivity.DETALHES_REQUEST);
            }
        });
        return viewItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewItemHolder viewItemHolder, int i) {

        PratoModel pratoModel = cardapio.get(i);
        viewItemHolder.setValuesByItem(pratoModel);
    }

    @Override
    public int getItemCount() {
        return cardapio.size();
    }

    public static class ViewItemHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private boolean isExpanded;
        PratoModel item;

        private ViewItemHolder(@NonNull View itemView, PratoModel pratoModel) {
            super(itemView);

            this.itemView = itemView;
            this.item = pratoModel;
            setValues();
        }

        private void setValues(){
            assert item != null;
            new LoadImgByURLTask(getImagem()).execute(item.getImage());
            getTitulo().setText(item.getTitulo());
            getDescricao().setText(item.getDescricao());
            getPreco().setText(String.format("R$ %s", item.getPreco()));
        }

        private void setValuesByItem(PratoModel item){
            this.item = item;
            setValues();
        }

        private View getView(){
            return this.itemView;
        }

        private ImageView getImagem() {
            return (ImageView) itemView.findViewById(R.id.item_cardapio_image);
        }

        private TextView getTitulo() {
            return (TextView) itemView.findViewById(R.id.item_cardapio_titulo);
        }

        private TextView getPreco() {
            return (TextView) itemView.findViewById(R.id.item_cardapio_preco);
        }

        private TextView getDescricao() {
            return (TextView) itemView.findViewById(R.id.item_cardapio_descricao);
        }
    }
}
