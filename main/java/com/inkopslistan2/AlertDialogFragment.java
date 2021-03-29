package com.inkopslistan2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlertDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    public static final String ARG_MUSER_ITEMS = "mUser_items";
    public static boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    public static final String ARG_CHECKED_ITEMS = "checked_items";
    public static final String ARG_TITLE = "title";
    public static final String RETURN_CHECKED = "checked_items";


    public static AlertDialogFragment newInstance(int title) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        //String message = args.getString(ARG_MESSAGE);
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        assert args != null;
        checkedItems = args.getBooleanArray(ARG_CHECKED_ITEMS);
        int title = getArguments().getInt("title");

        return new AlertDialog.Builder(getActivity())
                //.setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.shopping_title)
                .setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                )
                .setNegativeButton(R.string.dismiss_label,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.dismiss();
                                    }
                            }

                )
                .setNeutralButton(R.string.clear_all_label,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra(ARG_CHECKED_ITEMS, checkedItems);
                            returnIntent.putExtra(ARG_MUSER_ITEMS, mUserItems);
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, returnIntent);
                        }
                    }
                })
                .create();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }
}