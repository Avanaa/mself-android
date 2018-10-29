package br.com.avana.mself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.avana.mself.adapter.ListaCardapioAdapter;
import br.com.avana.mself.model.ItemModel;
import br.com.avana.mself.model.ItemPedidoModel;

public class CardapioActivity extends AppCompatActivity implements ChildEventListener {

    public static final int ITEM_PEDIDO_CRIADO_RESULT = 1;
    public static final int ITEM_PEDIDO_CANCELADO_RESULT = 0;
    public static final int DETALHES_REQUEST = 2;
    public static final int CARRINHO_REQUEST = 3;

    DatabaseReference mRef;
    List<ItemModel> cardapio = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRef = FirebaseDatabase.getInstance()
                .getReference("item");

        mRef.addChildEventListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cardapio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_item_cart:
                Intent intent = new Intent(this, CarrinhoActivity.class);
                startActivityForResult(intent, CARRINHO_REQUEST);
                break;
        }
        return true;
    }

    private void setList() {
        RecyclerView recyclerView = findViewById(R.id.lista_cardapio);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new ListaCardapioAdapter(cardapio, this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CardapioActivity.DETALHES_REQUEST){
            switch (resultCode){

                case CardapioActivity.ITEM_PEDIDO_CRIADO_RESULT:
                    //Pedido realizado
                    break;

                case CardapioActivity.ITEM_PEDIDO_CANCELADO_RESULT:

                    break;
            }
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);
        itemModel.setKey(dataSnapshot.getKey());
        cardapio.add(itemModel);
        setList();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);
        itemModel.setKey(dataSnapshot.getKey());
        for (int i = 0; i < cardapio.size(); i++){
            assert itemModel != null;
            if (cardapio.get(i).getKey().equals(itemModel.getKey())){
                cardapio.set(i, itemModel);
                break;
            }
        }
        setList();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);
        itemModel.setKey(dataSnapshot.getKey());
        for (int i = 0; i < cardapio.size(); i++){
            assert itemModel != null;
            if (cardapio.get(i).getKey().equals(itemModel.getKey())){
                cardapio.remove(i);
                break;
            }
        }
        setList();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);
        itemModel.setKey(dataSnapshot.getKey());
        for (int i = 0; i < cardapio.size(); i++){
            assert itemModel != null;
            if (cardapio.get(i).getKey().equals(itemModel.getKey())){
                cardapio.remove(i);
                break;
            }
        }
        setList();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(this, R.string.erro_banco_aviso, Toast.LENGTH_LONG).show();
    }
}

