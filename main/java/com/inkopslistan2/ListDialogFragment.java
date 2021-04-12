package com.inkopslistan2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class ListDialogFragment extends DialogFragment implements Serializable {

    private EditText editTextAmount;
    private EditText editTextUnit;
    private EditText editTextIngrdnt;
    private EditText editTextCat;
    private ListDialogFragmentListener listener;
    private boolean[] checkedItems;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle extras = getActivity().getIntent().getExtras();
        String[] shoppingList = getArguments().getStringArray("shoppingList");
        boolean[] checkedItems = getArguments().getBooleanArray("checkedItems");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());



        mBuilder.setTitle(R.string.dialog_title);
        mBuilder.setMultiChoiceItems(shoppingList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                /*if (isChecked) {
                    if (!mUserItems.contains(position))
                        mUserItems.add(position);
                } else {
                    mUserItems.remove((Integer.valueOf(position)));
                }
            */
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
            }

        });
        mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                }
            }
        });

        return mBuilder.create();
    }

    public interface ListDialogFragmentListener {
        void applyTexts(boolean[] checkedItems);
    }
}
