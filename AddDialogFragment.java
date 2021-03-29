package com.inkopslistan2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddDialogFragment extends DialogFragment {

    private EditText editTextAmount;
    private EditText editTextUnit;
    private EditText editTextIngrdnt;
    private EditText editTextCat;
    private AddDialogFragmentListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_ingrdnt, null);

        mBuilder.setView(view)
                .setTitle("Lägg till")
                .setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String amount = editTextAmount.getText().toString();
                        String unit = editTextUnit.getText().toString();
                        String ingrdnt = editTextIngrdnt.getText().toString();
                        String cat = editTextCat.getText().toString();
                        listener.applyTexts(amount, unit, ingrdnt, cat);
                    }
                });

        editTextAmount = view.findViewById(R.id.edit_amount);
        editTextUnit = view.findViewById(R.id.edit_unit);
        editTextIngrdnt = view.findViewById(R.id.edit_ingrdnt);
        editTextCat = view.findViewById(R.id.edit_cat);

        return mBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement AddDialogFragmentListener");
        }

    }

    public interface AddDialogFragmentListener {
        void applyTexts(String amount, String unit, String ingrdnt, String cat);
    }
}
