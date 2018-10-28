package br.com.avana.mself;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhes, menu);
        return true;
    }

    private void setPrato() {
        pratoModel = (PratoModel) getIntent().getSerializableExtra("prato");

        ImageView imgView = findViewById(R.id.detalhes_imagem);
        new LoadImgByURLTask(imgView).execute(pratoModel.getImage());

        TextView txtViewdescricao = findViewById(R.id.detalhes_descricao);
        txtViewdescricao.setText(pratoModel.getDescricao());

        TextView txtViewPreco = findViewById(R.id.detalhes_preco);
        txtViewPreco.setText(String.format(getString(R.string.moeda), pratoModel.getPreco()));
    }
}
