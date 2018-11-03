package br.com.avana.mself;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import br.com.avana.mself.async.LoadImgByURLTask;
import br.com.avana.mself.dao.PedidoDao;
import br.com.avana.mself.dialog.ObservacoesDialog;
import br.com.avana.mself.dialog.QuantidadeDialog;
import br.com.avana.mself.helper.DetalhesHelper;
import br.com.avana.mself.model.ItemPedidoModel;
import br.com.avana.mself.model.ItemModel;
import br.com.avana.mself.model.PedidoModel;

public class DetalhesActivity extends AppCompatActivity implements
        QuantidadeDialog.QuantidadeDialogListener,
        ObservacoesDialog.ObservacoesDialogListener {

    private ItemPedidoModel itemPedidoModel;
    private DetalhesHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ItemModel itemModel = (ItemModel) getIntent().getSerializableExtra(getString(R.string.itemCardapio));
        getSupportActionBar().setTitle(itemModel.getTitulo());

        itemPedidoModel = new ItemPedidoModel();
        itemPedidoModel.setItem(itemModel);

        helper = new DetalhesHelper(this);
        helper.setItem(itemPedidoModel);

        Button btnQuantidade = findViewById(R.id.detalhes_btn_quantidade);
        btnQuantidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuantidadeDialog();
            }
        });

        Button btnObservacoes = findViewById(R.id.detalhes_btn_observacoes);
        btnObservacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showObservacoesDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_item_save:
                pedidoRealizado();
                break;

            default:
                finish();
        }
        return true;
    }

    private void pedidoRealizado() {
        PedidoDao dao = new PedidoDao();
        dao.push(itemPedidoModel);
        Toast.makeText(this, R.string.detalhes_item_salvo, Toast.LENGTH_LONG).show();
        finish();
    }

    private void showObservacoesDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ObservacoesDialog newFragment = new ObservacoesDialog();
        newFragment.show(fragmentManager, "observacoes");
    }

    private void showQuantidadeDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        QuantidadeDialog newFragment = new QuantidadeDialog();
        newFragment.show(fragmentManager, "quantidade");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText observacoes = dialog.getDialog().findViewById(R.id.observacoes_edit_txt);
        String observacoesContent = observacoes.getText().toString();
        itemPedidoModel.setObservacoes(observacoesContent);
        Toast.makeText(this, R.string.detalhes_observacoes_ok, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) { }

    @Override
    public void onItemDialogClick(String quantidadeItem) {
        itemPedidoModel.setQuantidade(Integer.parseInt(quantidadeItem));
        itemPedidoModel.setPreco(itemPedidoModel.getItem().getPreco() * itemPedidoModel.getQuantidade());
        helper.setItem(itemPedidoModel);
    }
}
