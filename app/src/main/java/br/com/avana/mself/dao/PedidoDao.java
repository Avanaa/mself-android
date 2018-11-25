package br.com.avana.mself.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
