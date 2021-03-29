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

import java.io.Serializable;
import java.util.ArrayList;

public class InventDialogFragment extends DialogFragment implements Serializable {

    private EditText editTextAmount;
    private EditText editTextUnit;
    private EditText editTextIngrdnt;
    private EditText editTextCat;
    private InventDialogFragmentListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle extras = getActivity().getIntent().getExtras();
        ArrayList<Recipe> menu = extras.getParcelableArrayList("menu");
        int currDish = extras.getInt("current");
        String[] dishList = extras.getStringArray("dishList");
        Serializable boleanList = extras.getSerializable("bundleList");
        System.out.println(boleanList);

        //checkedItems = checkedItemsList[currDish];
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
                        //listener.applyTexts(amount, unit, ingrdnt);
                    }
                });

        editTextAmount = view.findViewById(R.id.edit_amount);
        editTextUnit = view.findViewById(R.id.edit_unit);
        editTextIngrdnt = view.findViewById(R.id.edit_ingrdnt);
        editTextCat = view.findViewById(R.id.edit_cat);

        return mBuilder.create();
    }
    /*
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (InventDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement InventDialogFragmentListener");
        }

    }*/

    public interface InventDialogFragmentListener {
        void applyTexts(ArrayList<Recipe> menu, String[] checkedItems, String[] currDishList);
    }
}
