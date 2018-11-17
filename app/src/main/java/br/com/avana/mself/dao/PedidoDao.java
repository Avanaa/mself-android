package br.com.avana.mself.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import br.com.avana.mself.model.ItemPedidoModel;

public class PedidoDao {

    private DatabaseReference mPedidoRef;

    public PedidoDao(){
        mPedidoRef = FirebaseDatabase.getInstance()
                        .getReference("PEDIDOS/Avana")
                        .orderByChild("status")
                        .equalTo(ItemPedidoModel.Status.CRIADO.name())
                        .getRef();
    }

    public DatabaseReference getPedidoRef() {
        return mPedidoRef;
    }

    public void push(final ItemPedidoModel itemPedidoModel){

        itemPedidoModel.setStatus(ItemPedidoModel.Status.CRIADO.name());
        Query query = getPedidoRef().orderByChild("itemKey").equalTo(itemPedidoModel.getItemKey());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    ItemPedidoModel item = data.getValue(ItemPedidoModel.class);
                    if (item == null || item.getItemKey() == null || !item.getItemKey().equals(itemPedidoModel.getItemKey())) {
                        getPedidoRef().push().setValue(itemPedidoModel);
                    } else {
                        getPedidoRef().child(data.getKey()).getRef().setValue(itemPedidoModel);
                        //getPedidoRef().child(item.getKey()).setValue(itemPedidoModel);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}
