package br.com.avana.mself.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.avana.mself.model.ItemPedidoModel;

public class PedidoDao {

    private DatabaseReference mPedidoRef;

    public PedidoDao(){
        mPedidoRef = FirebaseDatabase.getInstance()
                        .getReference("PEDIDO")
                        .orderByChild("status")
                        .equalTo(ItemPedidoModel.Status.CRIADO.name())
                        .getRef();
    }

    public DatabaseReference getPedidoRef() {
        return mPedidoRef;
    }

    public void push(ItemPedidoModel itemPedidoModel){
        itemPedidoModel.setStatus(ItemPedidoModel.Status.CRIADO.name());
        mPedidoRef.push().setValue(itemPedidoModel);
    }
}
