package br.com.avana.mself;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.avana.mself.adapter.ListaCardapioAdapter;
import br.com.avana.mself.model.PedidoModel;
import br.com.avana.mself.model.PratoModel;

public class CardapioActivity extends AppCompatActivity implements ChildEventListener {

    DatabaseReference mRef;
    List<PratoModel> cardapio = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRef = FirebaseDatabase.getInstance().getReference("prato");
        mRef.addChildEventListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cardapio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Ação não implementada ainda", Toast.LENGTH_LONG).show();
        return true;
    }

    private void setList() {
        RecyclerView recyclerView = findViewById(R.id.lista_cardapio);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new ListaCardapioAdapter(cardapio));

        /*
        listView.setAdapter(new ListaCardapioAdapter(cardapio, this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PratoModel prato = (PratoModel) parent.getItemAtPosition(position);
                startDetalhes(prato);
            }
        });
        */
    }

    private void startDetalhes(PratoModel prato) {

        Intent detalhesIntent = new Intent(CardapioActivity.this, DetalhesActivity.class);
        detalhesIntent.putExtra("prato", prato);
        startActivity(detalhesIntent);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        PratoModel pratoModel = dataSnapshot.getValue(PratoModel.class);
        cardapio.add(pratoModel);
        setList();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        PratoModel pratoModel = dataSnapshot.getValue(PratoModel.class);
        for (int i = 0; i < cardapio.size(); i++){
            assert pratoModel != null;
            if (cardapio.get(i).getCodigo().equals(pratoModel.getCodigo())){
                cardapio.set(i, pratoModel);
                break;
            }
        }
        setList();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        PratoModel pratoModel = dataSnapshot.getValue(PratoModel.class);
        for (int i = 0; i < cardapio.size(); i++){
            assert pratoModel != null;
            if (cardapio.get(i).getCodigo().equals(pratoModel.getCodigo())){
                cardapio.remove(i);
                break;
            }
        }
        setList();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        PratoModel pratoModel = dataSnapshot.getValue(PratoModel.class);
        for (int i = 0; i < cardapio.size(); i++){
            assert pratoModel != null;
            if (cardapio.get(i).getCodigo().equals(pratoModel.getCodigo())){
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

