package br.com.avana.mself;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import br.com.avana.mself.adapter.ListaItensCarrinhoAdapter;
import br.com.avana.mself.model.ItemPedidoModel;
import br.com.avana.mself.model.PedidoModel;

public class CarrinhoActivity extends AppCompatActivity implements ChildEventListener {

    DatabaseReference mRef;
    List<ItemPedidoModel> itensCarrinho = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRef = FirebaseDatabase.getInstance()
                .getReference("pedidos/TESTE")
                .orderByChild("status")
                .equalTo(PedidoModel.Status.CRIADO.name())
                .getRef();

        mRef.addChildEventListener(this);

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
            total += item.getPreco();
        }
        btnEnviar.setText(String.format(getString(R.string.carrinho_concluir_pedido), total));
    }

    public void removeItemCarrinho(ItemPedidoModel item){
        mRef.child(item.getKey()).removeValue();
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
    public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        ItemPedidoModel item = dataSnapshot.getValue(ItemPedidoModel.class);
        item.setKey(dataSnapshot.getKey());
        itensCarrinho.remove(item);
        setList();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

    @Override
    public void onCancelled(DatabaseError databaseError) { }
}
