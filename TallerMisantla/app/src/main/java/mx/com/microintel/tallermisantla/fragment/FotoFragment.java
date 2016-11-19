package mx.com.microintel.tallermisantla.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class FotoFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        builder.setView(inflater.inflate(R.layout.fragment_foto, null))
//                .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        ((Spinner) getDialog().findViewById(R.id.spnRazon)).getSelectedItemPosition();
//                        String[] aKeyParams = getContext().getResources().getStringArray(R.array.razones);
//                        mListener.onUnassign(aKeyParams[((Spinner) getDialog().findViewById(R.id.spnRazon)).getSelectedItemPosition()]);
//                    }
//                })
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                });
        return builder.create();
    }
}
