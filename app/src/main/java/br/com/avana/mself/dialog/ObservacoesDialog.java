package br.com.avana.mself.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import br.com.avana.mself.R;

public class ObservacoesDialog extends DialogFragment {


    public interface ObservacoesDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    public ObservacoesDialog(){}

    private ObservacoesDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ObservacoesDialogListener) getActivity();
        } catch (ClassCastException e){
            Log.e("ERRO:", e.getMessage() );
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);

        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_observacoes, null))
                .setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(ObservacoesDialog.this);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(ObservacoesDialog.this);
                        ObservacoesDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
