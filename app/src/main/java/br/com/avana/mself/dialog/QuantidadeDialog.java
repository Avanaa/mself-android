package br.com.avana.mself.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import br.com.avana.mself.R;


public class QuantidadeDialog extends DialogFragment {

    public interface QuantidadeDialogListener {
        public void onItemDialogClick(String item);
    }

    private QuantidadeDialogListener mListener;
    public String[] values;

    public QuantidadeDialog() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (QuantidadeDialogListener) getActivity();
        } catch (ClassCastException e){
            Log.e("ERRO:", e.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);

        values = getActivity().getResources().getStringArray(R.array.detalhes_quantidade);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_quantidade, null);
        ListView listView = view.findViewById(R.id.quantidade_lista);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = values[position];
                mListener.onItemDialogClick(value);
                QuantidadeDialog.this.getDialog().cancel();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
