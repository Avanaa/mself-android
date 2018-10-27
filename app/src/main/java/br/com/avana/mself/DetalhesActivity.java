package br.com.avana.mself;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.avana.mself.async.LoadImgByURLTask;
import br.com.avana.mself.dialog.ObservacoesDialog;
import br.com.avana.mself.model.PratoModel;

public class DetalhesActivity extends AppCompatActivity {

    private PratoModel pratoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        setPrato();

        Button btnQuantidade = findViewById(R.id.detalhes_quantidade);
        btnQuantidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                QuantidadeDialog newFragment = new QuantidadeDialog();
                newFragment.show(fragmentManager, "dialog");
            }
        });

        Button btnObservacoes = findViewById(R.id.detalhes_btn_observacoes);
        btnObservacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ObservacoesDialog newFragment = new ObservacoesDialog();
                newFragment.show(fragmentManager, "dialog");
            }
        });

        Button btnAdicionar = findViewById(R.id.detalhes_btn_adicionar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent().putExtra("pratoCriado", pratoModel);
                setResult(CardapioActivity.ITEM_CRIADO_RESULT);
                finish();
            }
        });
    }

    private void setPrato() {
        pratoModel = (PratoModel) getIntent().getSerializableExtra("prato");

        ImageView imgView = findViewById(R.id.detalhes_imagem);
        new LoadImgByURLTask(imgView).execute(pratoModel.getImage());

        TextView txtViewTitulo = findViewById(R.id.detalhes_titulo);
        txtViewTitulo.setText(pratoModel.getTitulo());

        TextView txtViewdescricao = findViewById(R.id.detalhes_descricao);
        txtViewdescricao.setText(pratoModel.getDescricao());

        TextView txtViewPreco = findViewById(R.id.detalhes_preco);
        txtViewPreco.setText(String.format(getString(R.string.moeda), pratoModel.getPreco()));
    }
}
