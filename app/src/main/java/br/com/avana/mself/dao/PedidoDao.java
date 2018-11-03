package br.com.avana.mself.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.avana.mself.model.ItemPedidoModel;
import br.com.avana.mself.model.PedidoModel;

public class PedidoDao {

    private DatabaseReference mPedidoRef;

    public PedidoDao(){
        mPedidoRef = FirebaseDatabase.getInstance()
                        .getReference(String.format("PEDIDO/%s", "TESTE"))
                        .orderByChild("status")
                        .equalTo(PedidoModel.Status.CRIADO.name())
                        .getRef();
        ;
    }

    public DatabaseReference getPedidoRef() {
        return mPedidoRef;
    }

    public void push(ItemPedidoModel itemPedidoModel){
        itemPedidoModel.setStatus(PedidoModel.Status.CRIADO.name());
        mPedidoRef.push().setValue(itemPedidoModel);
    }
}
