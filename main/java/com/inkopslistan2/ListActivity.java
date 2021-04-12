package com.inkopslistan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
public class ListActivity extends AppCompatActivity implements ListDialogFragment.ListDialogFragmentListener {
    private String[] shoppingList;
    private boolean[] checkedItems;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ingredients = this.getIntent().getParcelableArrayListExtra("ingredients");
        assert ingredients != null;
        shoppingList = new String[ingredients.size()];
        checkedItems = new boolean[shoppingList.length];
        shoppingList();
    }


    public void shoppingList() {
        for (int i = 0; i < ingredients.size(); i++) {
            shoppingList[i] = ingredients.get(i).getAmount() + " " +
                    ingredients.get(i).getUnit() + " " + ingredients.get(i).getIngrdnt();
        }
        Bundle bundle = new Bundle();
        bundle.putStringArray("shoppingList", shoppingList);
        bundle.putBooleanArray("checkedItems", checkedItems);

        ListDialogFragment listDialogFragment = new ListDialogFragment();
        listDialogFragment.setArguments(bundle);
        listDialogFragment.show(getSupportFragmentManager(), "Inventera");
    }


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

    @Override
    public void applyTexts(boolean[] checkedItems) {
        this.checkedItems = checkedItems;
    }
}
