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
                .getReference("PEDIDO")
                .getRef();
    }

    public DatabaseReference getPedidoRef() {
        return mPedidoRef;
    }

    public void push(final ItemPedidoModel itemPedidoModel){
        if (itemPedidoModel.getKey() != null){
            getPedidoRef().child(itemPedidoModel.getKey()).setValue(itemPedidoModel);
        } else {
            getPedidoRef().push().setValue(itemPedidoModel);
        }
    }
}
