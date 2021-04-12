package com.inkopslistan2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        int currDish = getArguments().getInt("current");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        assert menu != null;
        Recipe temp = menu.get(currDish);
        String[] dishList;
        boolean[] checkedItems = new boolean[temp.ingredients.size()];
        final String[] ingrdntList = new String[temp.ingredients.size()];
        for (int j = 0; j < temp.ingredients.size(); j++) {
            checkedItems[j] = temp.ingredients.get(j).getChecked();
            if (temp.ingredients.get(j).getAmount() != 0)
                ingrdntList[j] = temp.ingredients.get(j).getAmount() + " " + temp.ingredients.get(j).getUnit() + " " + temp.ingredients.get(j).getIngrdnt();
            else
                ingrdntList[j] = temp.ingredients.get(j).getIngrdnt();
        }

        dishList = ingrdntList;




        mBuilder.setTitle(R.string.dialog_title);
        mBuilder.setMultiChoiceItems(dishList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
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
                //checkedItemsList[currDish] = checkedItems;
                try {
                    for (int i = 0; i < temp.ingredients.size(); i++) {
                        //System.out.println(checkedItemsList[currDish][i]);
                        temp.ingredients.get(i).setChecked(checkedItems[i]);
                    }

                } catch (NullPointerException e) {
                    Log.v("myTag", String.valueOf(e));
                }
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
                    menu.get(currDish).ingredients.get(i).setChecked(false);
                    //mUserItems.clear();
                    //Uppdaterar mUserItemsList med värdet innan ny knapp trycks och mUserItems skrivs över.
                    //mUserItemsList[currDish] = mUserItems;
                }
            }
        });

        return mBuilder.create();
    }

    public interface InventDialogFragmentListener {
        void applyTexts(ArrayList<Recipe> menu, String[] checkedItems, String[] currDishList);
    }
}
