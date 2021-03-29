package com.inkopslistan2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
public class ListActivity extends AppCompatActivity {
    //public static final String SHOPPING_LIST = "shoppingList";
    private String[] shoppingList;
    private boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        ArrayList<Ingredient> ingredients = this.getIntent().getParcelableArrayListExtra("ingredients");
        assert ingredients != null;
        shoppingList = new String[ingredients.size()];
        for(int i = 0; i < ingredients.size(); i++) {
            shoppingList[i] = ingredients.get(i).getAmount() + " " +
                    ingredients.get(i).getUnit() + " " + ingredients.get(i).getIngrdnt();
        }
        //shoppingList = this.getIntent().getStringArrayListExtra(SHOPPING_LIST).toArray(new String[0]);
        checkedItems = new boolean[shoppingList.length];
        shoppingList();
    }

    /*
    public void shoppingList() {
        DialogFragment dialog = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putBooleanArray(AlertDialogFragment.ARG_CHECKED_ITEMS, checkedItems);
        args.putIntegerArrayList(AlertDialogFragment.ARG_MUSER_ITEMS, mUserItems);
        dialog.setArguments(args);
        //dialog.setTargetFragment(this,);
        dialog.show(getSupportFragmentManager(), "dialog");

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            checkedItems = data.getBooleanArrayExtra("checked_items");
            mUserItems = data.getIntegerArrayListExtra("mUser_items");
        }
    }
    */


    public void shoppingList() {
    AlertDialog.Builder mBuilder = new AlertDialog.Builder(ListActivity.this);
        mBuilder.setTitle(R.string.shopping_title);
        mBuilder.setMultiChoiceItems(shoppingList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                /*if (isChecked) {
                    mUserItems.add(position);
                } else {
                    mUserItems.remove((Integer.valueOf(position)));
                }*/
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
                    mUserItems.clear();
                }
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show(); }





    public void onShoppingList(View caller) {
        shoppingList();
    }


    public void onFinish(View caller) {
        //Broadcast till andra aktiviteter för att meddela att appen ska startas om.
        Intent intent = new Intent("finish_activity");
        sendBroadcast(intent);
        //Startar MainAcitivity
        Intent newIntent = new Intent(this, MainActivity.class);
        this.startActivity(newIntent);
        finish();
    }

    public void back(View caller){
        finish();
    }

}
