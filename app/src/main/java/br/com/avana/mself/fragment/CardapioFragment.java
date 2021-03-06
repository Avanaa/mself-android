package br.com.avana.mself.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import br.com.avana.mself.R;
import br.com.avana.mself.adapter.ListaCardapioAdapter;
import br.com.avana.mself.dao.ItemDao;
import br.com.avana.mself.model.ItemModel;


public class CardapioFragment extends Fragment implements ChildEventListener {

    private List<ItemModel> cardapio = new ArrayList<>();

    public CardapioFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference dao = new ItemDao().getItemDao();
        dao.addChildEventListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cardapio, container, false);
    }

    private void setList() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.lista_cardapio);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new ListaCardapioAdapter(cardapio, getActivity()));
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
        ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);

        assert itemModel != null;
        itemModel.setKey(dataSnapshot.getKey());
        cardapio.add(itemModel);
        setList();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
        ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);

        assert itemModel != null;
        itemModel.setKey(dataSnapshot.getKey());
        int index = cardapio.indexOf(itemModel);
        cardapio.set(index, itemModel);
        setList();
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);

        assert itemModel != null;
        itemModel.setKey(dataSnapshot.getKey());
        cardapio.remove(itemModel);
        setList();
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
        ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);

        assert itemModel != null;
        itemModel.setKey(dataSnapshot.getKey());
        cardapio.remove(itemModel);
        setList();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Toast.makeText(getActivity(), R.string.erro_banco_aviso, Toast.LENGTH_LONG).show();
    }
}
