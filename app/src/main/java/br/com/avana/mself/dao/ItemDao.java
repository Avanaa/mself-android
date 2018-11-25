package br.com.avana.mself.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemDao {

    private DatabaseReference itemDao;

    public ItemDao(){

        //Comentar essa linha depois
        itemDao = FirebaseDatabase.getInstance().getReference("/ITEM/");

        //Descomentar essa linha depois
        //itemDao = FirebaseDatabase.getInstance().getReference(String.format("ITEM/%s", categoria.name()));
    }

    public DatabaseReference getItemDao() {
        return itemDao;
    }
}
