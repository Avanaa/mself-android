package br.com.avana.mself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.avana.mself.async.LoadImgByURLTask;
import br.com.avana.mself.model.PratoModel;

public class DetalhesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        setPrato();
    }

    private void setPrato() {
        PratoModel pratoModel = (PratoModel) getIntent().getSerializableExtra("prato");

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
