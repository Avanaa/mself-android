package br.com.avana.mself;

import android.app.Activity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.avana.mself.adapter.ListaItensCarrinhoAdapter;
import br.com.avana.mself.dao.PedidoDao;
import br.com.avana.mself.model.ItemModel;
import br.com.avana.mself.model.ItemPedidoModel;

public class CarrinhoActivity extends AppCompatActivity implements ChildEventListener {

    PedidoDao dao = new PedidoDao();
    List<ItemPedidoModel> itensCarrinho = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        dao.getPedidoRef().addChildEventListener(this);

        Button button = findViewById(R.id.carrinho_btn_enviar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(CarrinhoActivity.this)
                        .setPrompt(getString(R.string.leitura_info))
                        .initiateScan();
            }
        });
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
                Toast.makeText(this, "Escaneamento cancelado", Toast.LENGTH_LONG).show();
            } else {
                TextView textView = findViewById(R.id.carrinho_text_view_mesa);
                textView.setText(String.format("Código da mesa: %s",result.getContents()));
                for (ItemPedidoModel item : itensCarrinho){
                    item.setMesa(result.getContents());
                    dao.push(item);
                }
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
        Toast.makeText(this, "Item removido do pedido", Toast.LENGTH_LONG).show();
        setList();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ItemPedidoModel item = dataSnapshot.getValue(ItemPedidoModel.class);
        item.setKey(dataSnapshot.getKey());
        itensCarrinho.add(item);
        setList();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        ItemPedidoModel item = dataSnapshot.getValue(ItemPedidoModel.class);
        item.setKey(dataSnapshot.getKey());
        itensCarrinho.set(itensCarrinho.indexOf(item), item);
        setList();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        ItemPedidoModel item = dataSnapshot.getValue(ItemPedidoModel.class);
        item.setKey(dataSnapshot.getKey());
        itensCarrinho.remove(item);
        setList();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        ItemPedidoModel item = dataSnapshot.getValue(ItemPedidoModel.class);
        item.setKey(dataSnapshot.getKey());
        itensCarrinho.remove(item);
        setList();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(this, R.string.aviso_erro, Toast.LENGTH_LONG).show();
    }
}
