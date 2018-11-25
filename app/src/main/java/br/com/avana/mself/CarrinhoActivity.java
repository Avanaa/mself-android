package br.com.avana.mself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.avana.mself.adapter.ListaItensCarrinhoAdapter;
import br.com.avana.mself.dao.PedidoDao;
import br.com.avana.mself.model.ItemPedidoModel;

public class CarrinhoActivity extends AppCompatActivity implements ChildEventListener {

    private PedidoDao dao = new PedidoDao();
    private List<ItemPedidoModel> itensCarrinho = new ArrayList<>();
    private String codigoMesa;
    private boolean carrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        dao.getPedidoRef().addChildEventListener(this);
        carrinho = getIntent().getBooleanExtra("carrinho", false);

        updateUi();

    }

    private void updateUi() {

        Button button = findViewById(R.id.carrinho_btn_enviar);
        if (!carrinho){
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.carrinho_acompanhe_pedido));
            button.setVisibility(View.GONE);
            TextView txtViewMesa = findViewById(R.id.carrinho_text_view_mesa);
            txtViewMesa.setVisibility(View.GONE);
        } else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new IntentIntegrator(CarrinhoActivity.this)
                            .setPrompt(getString(R.string.leitura_info))
                            .initiateScan();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (codigoMesa != null){
            TextView txtViewMesa = findViewById(R.id.carrinho_text_view_mesa);
            txtViewMesa.setText(String.format(getString(R.string.carrinho_escaneado_codigo_mesa), codigoMesa));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, R.string.carrinho_escaneamento_cancelado, Toast.LENGTH_LONG).show();
            } else {
                TextView textView = findViewById(R.id.carrinho_text_view_mesa);
                codigoMesa = result.getContents();
                textView.setText(String.format(getString(R.string.carrinho_codigo_mesa),codigoMesa));

                Button button = findViewById(R.id.carrinho_btn_enviar);
                button.setText(String.format(getString(R.string.carrinho_confirmar_pedido_botao), codigoMesa));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (ItemPedidoModel item : itensCarrinho) {
                            item.setMesa(codigoMesa);
                            item.setStatus(ItemPedidoModel.Status.ENVIADO.name());
                            dao.push(item);
                        }
                    }
                });
            }
        }
    }

    private void setList(){
        RecyclerView recyclerView = findViewById(R.id.carrinho_lista);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ListaItensCarrinhoAdapter(itensCarrinho, this));
        atualizaTotal();
    }

    private void atualizaTotal() {
        Button btnEnviar = findViewById(R.id.carrinho_btn_enviar);
        double total = 0;
        for(ItemPedidoModel item : itensCarrinho){
            double totalPorItem = item.getPrecoPedido();
            total += totalPorItem;
        }
        btnEnviar.setText(String.format(getString(R.string.carrinho_concluir_pedido), total));
    }

    public void removeItemCarrinho(ItemPedidoModel item){
        dao.getPedidoRef().child(item.getKey()).removeValue();
        Toast.makeText(this, R.string.carrinho_item_removido, Toast.LENGTH_LONG).show();
        setList();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ItemPedidoModel item = dataSnapshot.getValue(ItemPedidoModel.class);
        assert item != null;
        item.setKey(dataSnapshot.getKey());
        incluiItemCarrinho(item);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        ItemPedidoModel item = dataSnapshot.getValue(ItemPedidoModel.class);
        assert item != null;
        item.setKey(dataSnapshot.getKey());
        atualizaItemCarrinho(item);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        ItemPedidoModel item = dataSnapshot.getValue(ItemPedidoModel.class);
        assert item != null;
        item.setKey(dataSnapshot.getKey());
        itensCarrinho.remove(item);
        setList();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        ItemPedidoModel item = dataSnapshot.getValue(ItemPedidoModel.class);
        assert item != null;
        item.setKey(dataSnapshot.getKey());
        itensCarrinho.remove(item);
        setList();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(this, R.string.aviso_erro, Toast.LENGTH_LONG).show();
    }

    private void incluiItemCarrinho(ItemPedidoModel item) {
        if (!carrinho) {
            if (item.getStatus().equals(ItemPedidoModel.Status.ENVIADO.name())
                    || item.getStatus().equals(ItemPedidoModel.Status.EM_PREPARACAO.name())
                    || item.getStatus().equals(ItemPedidoModel.Status.PRONTO.name())
                    || item.getStatus().equals(ItemPedidoModel.Status.ENTREGUE.name())) {
                itensCarrinho.add(item);
                setList();
            }
        } else {
            if (item.getStatus().equals(ItemPedidoModel.Status.CRIADO.name())) {
                itensCarrinho.add(item);
                setList();
            }
        }
    }

        private void atualizaItemCarrinho (ItemPedidoModel item){
            int index = itensCarrinho.indexOf(item);
            if (index < 0 || index >= itensCarrinho.size()) {
                return;
            }
            if (!carrinho){
                if (item.getStatus().equals(ItemPedidoModel.Status.ENVIADO.name())
                        || item.getStatus().equals(ItemPedidoModel.Status.EM_PREPARACAO.name())
                        || item.getStatus().equals(ItemPedidoModel.Status.PRONTO.name())
                        || item.getStatus().equals(ItemPedidoModel.Status.ENTREGUE.name())){
                    itensCarrinho.set(index, item);
                } else {
                    itensCarrinho.remove(index);
                }
            } else {
                if (item.getStatus().equals(ItemPedidoModel.Status.CRIADO.name())) {
                    itensCarrinho.set(index, item);
                } else {
                    itensCarrinho.remove(index);
                }
            }
            setList();
        }
}
