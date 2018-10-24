package br.com.avana.mself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.avana.mself.adapter.ListaCardapioAdapter;
import br.com.avana.mself.model.PratoModel;

public class MainActivity extends AppCompatActivity implements ChildEventListener {

    DatabaseReference mRef;
    List<PratoModel> cardapio = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRef= FirebaseDatabase.getInstance().getReference("prato");
        mRef.addChildEventListener(this);

    }

    private void setList() {
        ListView viewById = findViewById(R.id.lista_cardapio);
        viewById.setAdapter(new ListaCardapioAdapter(cardapio, this));
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
            if (cardapio.get(i).getCodigo() == pratoModel.getCodigo()){
                cardapio.set(i, pratoModel);
                break;
            }
        }
        setList();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
