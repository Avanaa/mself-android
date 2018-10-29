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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.avana.mself.async.LoadImgByURLTask;
import br.com.avana.mself.dialog.ObservacoesDialog;
import br.com.avana.mself.dialog.QuantidadeDialog;
import br.com.avana.mself.model.ItemPedidoModel;
import br.com.avana.mself.model.ItemModel;
import br.com.avana.mself.model.PedidoModel;

public class DetalhesActivity extends AppCompatActivity implements
        QuantidadeDialog.QuantidadeDialogListener,
        ObservacoesDialog.ObservacoesDialogListener {

    private DatabaseReference mRef;
    private ItemModel itemModel;
    private ItemPedidoModel itemPedidoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRef = FirebaseDatabase.getInstance()
                .getReference("pedidos")
                .child("TESTE");

        itemPedidoModel = new ItemPedidoModel();
        itemModel = (ItemModel) getIntent().getSerializableExtra(getString(R.string.itemCardapio));
        itemPedidoModel.setItem(itemModel);
        setItemOnScreen();
        setPrecoOnScreen();

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
        }
        return true;
    }

    private void pedidoRealizado() {
        itemPedidoModel.setStatus(PedidoModel.Status.CRIADO.name());
        mRef.push().setValue(itemPedidoModel);
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

    private void setItemOnScreen() {

        ImageView imgView = findViewById(R.id.detalhes_imagem);
        new LoadImgByURLTask(imgView).execute(itemModel.getImage());

        TextView txtViewdescricao = findViewById(R.id.detalhes_descricao);
        txtViewdescricao.setText(itemModel.getDescricao());

        TextView txtViewPreco = findViewById(R.id.detalhes_preco);
        txtViewPreco.setText(String.format(getString(R.string.moeda), String.valueOf(itemPedidoModel.getItem().getPreco())));
    }

    private void setPrecoOnScreen(){

        Button btnQuantidade = findViewById(R.id.detalhes_btn_quantidade);
        btnQuantidade.setText(String.valueOf(itemPedidoModel.getQuantidade()));

        TextView precoTotal = findViewById(R.id.detalhes_total_preco);
        precoTotal.setText(String.valueOf(itemPedidoModel.getPreco()));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText observacoes = dialog.getDialog().findViewById(R.id.observacoes_edit_txt);
        String observacoesContent = observacoes.getText().toString();
        itemPedidoModel.setObservacoes(observacoesContent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) { }

    @Override
    public void onItemDialogClick(String Quantidadeitem) {
        itemPedidoModel.setQuantidade(Integer.parseInt(Quantidadeitem));
        itemPedidoModel.setPreco(itemPedidoModel.getItem().getPreco() * itemPedidoModel.getQuantidade());
        setPrecoOnScreen();
    }
}
