package br.com.avana.mself.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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
    public ViewItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        PratoModel pratoModel = cardapio.get(i);

        ViewItemHolder viewItemHolder = new ViewItemHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_cardapio, parent, false));

        new LoadImgByURLTask(viewItemHolder.getImagem()).execute(pratoModel.getImage());
        viewItemHolder.getTitulo().setText(pratoModel.getTitulo());
        viewItemHolder.getDescricao().setText(pratoModel.getDescricao());
        viewItemHolder.getPreco().setText(String.format(activity.getString(R.string.moeda), pratoModel.getPreco()));

        return viewItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewItemHolder viewItemHolder, int i) {

        PratoModel pratoModel = cardapio.get(i);

        new LoadImgByURLTask(viewItemHolder.getImagem()).execute(pratoModel.getImage());
        viewItemHolder.getTitulo().setText(pratoModel.getTitulo());
        viewItemHolder.getDescricao().setText(pratoModel.getDescricao());
        viewItemHolder.getPreco().setText(String.format(activity.getString(R.string.moeda), pratoModel.getPreco()));
    }

    @Override
    public int getItemCount() {
        return cardapio.size();
    }

    public static class ViewItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View itemView;
        private boolean isExpanded;

        public ViewItemHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.isExpanded = !this.isExpanded;
            setLayout();
        }

        private void setLayout(){
            if (isExpanded){
                getDescricao().setVisibility(View.VISIBLE);
            } else {
                getDescricao().setVisibility(View.GONE);
            }
        }

        public ImageView getImagem() {
            return (ImageView) itemView.findViewById(R.id.item_cardapio_image);
        }

        public TextView getTitulo() {
            return (TextView) itemView.findViewById(R.id.item_cardapio_titulo);
        }

        public TextView getDescricao() {
            return (TextView) itemView.findViewById(R.id.item_cardapio_descricao);
        }

        public TextView getPreco() {
            return (TextView) itemView.findViewById(R.id.item_cardapio_preco);
        }
    }
}
