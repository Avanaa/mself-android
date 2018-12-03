package br.com.avana.mself;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import br.com.avana.mself.dao.PedidoDao;
import br.com.avana.mself.dialog.ObservacoesDialog;
import br.com.avana.mself.dialog.QuantidadeDialog;
import br.com.avana.mself.helper.DetalhesHelper;
import br.com.avana.mself.model.ItemPedidoModel;
import br.com.avana.mself.model.ItemModel;
import br.com.avana.mself.service.MyFireaseMessageService;

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

        ImageButton btnQuantidade = findViewById(R.id.detalhes_btn_quantidade);
        btnQuantidade.setOnClickListener(v -> showQuantidadeDialog());

        ImageButton btnObservacoes = findViewById(R.id.detalhes_btn_observacoes);
        btnObservacoes.setOnClickListener(v -> showObservacoesDialog());

        String pedidoKey = getIntent().getStringExtra("pedidoKey");

        ItemModel itemModel = (ItemModel) getIntent().getSerializableExtra(getString(R.string.itemCardapio));
        if (itemModel != null){
            helper = new DetalhesHelper(this);
            itemPedidoModel = helper.setItem(itemModel);
            itemPedidoModel.setKey(pedidoKey);
            getSupportActionBar().setTitle(itemPedidoModel.getTitulo());
        }
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
        itemPedidoModel.setStatus(ItemPedidoModel.Status.CRIADO.name());
        itemPedidoModel.setUsuario(MyFireaseMessageService.getToken(this));
        new PedidoDao().push(itemPedidoModel);
        Toast.makeText(this, R.string.detalhes_pedido_enviado, Toast.LENGTH_LONG).show();
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
        helper.setPreco(itemPedidoModel);
    }
}
