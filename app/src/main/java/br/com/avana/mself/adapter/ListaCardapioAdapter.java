package br.com.avana.mself.adapter;

import android.app.Activity;
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

import br.com.avana.mself.R;
import br.com.avana.mself.async.LoadImgByURLTask;
import br.com.avana.mself.model.PratoModel;

public class ListaCardapioAdapter extends RecyclerView.Adapter<ListaCardapioAdapter.ViewItemHolder>  {

    private List<PratoModel> cardapio;

    public ListaCardapioAdapter(List<PratoModel> cardapio){
        this.cardapio = cardapio;
    }

    @NonNull
    @Override
    public ViewItemHolder onCreateViewHolder(@NonNull final ViewGroup parent, int i) {

        PratoModel pratoModel = cardapio.get(i);

        ViewItemHolder viewItemHolder = new ViewItemHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_cardapio, parent, false),
                pratoModel);

        viewItemHolder.getBtnAdicionar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), "Adicionado!", Toast.LENGTH_LONG).show();
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

    public static class ViewItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View itemView;
        private boolean isExpanded;
        PratoModel item;

        private ViewItemHolder(@NonNull View itemView, PratoModel pratoModel) {
            super(itemView);

            this.itemView = itemView;
            this.itemView.setOnClickListener(this);
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

        @Override
        public void onClick(View v) {
            this.isExpanded = !this.isExpanded;
            setLayout();
        }

        private void setLayout(){
            if (isExpanded){
                getDescricao().setVisibility(View.VISIBLE);
                getBtnAdicionar().setVisibility(View.VISIBLE);
            } else {
                getDescricao().setVisibility(View.GONE);
                getBtnAdicionar().setVisibility(View.GONE);
            }
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

        private Button getBtnAdicionar(){
            return (Button) itemView.findViewById(R.id.item_cardapio_adicionar);
        }
    }
}
