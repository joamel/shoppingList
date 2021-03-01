package com.inkopslistan2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InventActivity extends AppCompatActivity implements View.OnClickListener {

    public static final ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Recipe> menu;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private ArrayList<ImageView> imageViews = new ArrayList<>();

    private int currDish;
    private String[][] dishList = new String[0][];
    private String[] listItems, listAmount, listTot;
    private boolean[][] checkedItemsList;
    private boolean[] checkedItems;
    private ArrayList[] mUserItemsList;
    private String[][] ingredientsList, amountList;
    private ArrayList mUserItems = new ArrayList<>();
    private ArrayList<Ingredient> tempList, ingredients;


    public InventActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invent);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));

        Intent intent = getIntent();
        menu = this.getIntent().getParcelableArrayListExtra("menu");
        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);
        tv5 = findViewById(R.id.textView5);
        tv6 = findViewById(R.id.textView6);
        tv7 = findViewById(R.id.textView7);
        textViews.add(tv1);
        textViews.add(tv2);
        textViews.add(tv3);
        textViews.add(tv4);
        textViews.add(tv5);
        textViews.add(tv6);
        textViews.add(tv7);

        iv1 = findViewById(R.id.iv_1);
        iv2 = findViewById(R.id.iv_2);
        iv3 = findViewById(R.id.iv_3);
        iv4 = findViewById(R.id.iv_4);
        iv5 = findViewById(R.id.iv_5);
        iv6 = findViewById(R.id.iv_6);
        iv7 = findViewById(R.id.iv_7);
        imageViews.add(iv1);
        imageViews.add(iv2);
        imageViews.add(iv3);
        imageViews.add(iv4);
        imageViews.add(iv5);
        imageViews.add(iv6);
        imageViews.add(iv7);


        for(int i = 0; i < menu.size(); i++) {
            try{
                int ivId = getResources().getIdentifier(menu.get(i).iv.toLowerCase(), "drawable", getPackageName());
                imageViews.get(i).setImageResource(ivId);
                textViews.get(i).setText(menu.get(i).name);
                imageViews.get(i).setOnClickListener(this);
                if (!(menu.get(i) == null)) {
                    imageViews.get(i).setVisibility(View.VISIBLE);
                    textViews.get(i).setVisibility(View.VISIBLE);
                }
            }
            catch(NullPointerException ignored){
            }
        }
        dishList = new String[menu.size()][];
        checkedItemsList = new boolean[menu.size()][];

        int i = 0;
        for (Recipe rec: menu) {
            if(rec != null) {
                final String[] ingrdntList = new String[rec.ingredients.size()];
                checkedItemsList[i] = new boolean[rec.ingredients.size()];
                //System.out.println("Ingredient size: " + rec.ingredients.size());
                //System.out.println("checkedItemList[i] length: " + checkedItemsList[i].length);
                for (int j = 0; j < rec.ingredients.size(); j++) {
                    checkedItemsList[i][j] = rec.ingredients.get(j).getChecked();
                    if (rec.ingredients.get(j).getAmount() != 0)
                        ingrdntList[j] = rec.ingredients.get(j).getAmount() + " " + rec.ingredients.get(j).getUnit() + " " + rec.ingredients.get(j).getIngrdnt();
                    else
                        ingrdntList[j] = rec.ingredients.get(j).getIngrdnt();
                }

                dishList[i] = ingrdntList;
            }
            i++;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_1:
                currDish = 0;
                break;
            case R.id.iv_2:
                currDish = 1;
                break;
            case R.id.iv_3:
                currDish = 2;
                break;
            case R.id.iv_4:
                currDish = 3;
                break;
            case R.id.iv_5:
                currDish = 4;
                break;
            case R.id.iv_6:
                currDish = 5;
                break;
            case R.id.iv_7:
                currDish = 6;
                break;
            default:
                break;
        }

        //mUserItems = mUserItemsList[currDish];
        checkedItems = checkedItemsList[currDish];
        /*//Om checkedItems längd är 0
        if (checkedItems.length == 0)
            //skapar tom boolean-lista med längden av listItems.
            checkedItems = new boolean[listItems.length];

        else {
            //annars sätts checkedItems till tidigare värde för denna knapp
            checkedItems = checkedItemsList[currDish];
        }*/

        //Skapar alertDialog för gällande recept från knapptryck innan.
        MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(InventActivity.this);
        mBuilder.setTitle(R.string.dialog_title);
        mBuilder.setMultiChoiceItems(dishList[currDish], checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
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
        //Uppdaterar listorna med värdena innan ny knapp trycks och dessa skrivs över.
        //mUserItemsList[currDish] = mUserItems;




        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //checkedItemsList[currDish] = checkedItems;
                try {
                    for (int i = 0; i < menu.get(currDish).ingredients.size(); i++) {

                        System.out.println(checkedItemsList[currDish][i]);

                        menu.get(currDish).ingredients.get(i).setChecked(checkedItemsList[currDish][i]);
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
                    checkedItemsList[currDish] = checkedItems;
                    //mUserItems.clear();
                    //Uppdaterar mUserItemsList med värdet innan ny knapp trycks och mUserItems skrivs över.
                    //mUserItemsList[currDish] = mUserItems;
                }
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    public void shoppingList(View caller) {
        //Skapar två temporära listor att föra över ingredienser och
        tempList = new ArrayList<>();
        ingredients = new ArrayList<>();
        //Lägger alla ingredienser och mängder i samma listor
        for (Recipe rec:menu) {
            try {
                for (Ingredient ingrdnt:rec.ingredients) {
                //Om ingrediensen redan är avcheckad
                if (ingrdnt.getChecked()) {
                    //exekveras inget.
                    //annars om ingrediensen redan finns i tempLista
                } else if (isPresent(ingrdnt.getIngrdnt())) {
                    //kallar vi på metoden addQuantites för att addera mängden till bef. mängd
                    addQuantities(ingrdnt.getIngrdnt(), ingrdnt.getAmount());
                }
                //annars läggs ingrediensen till tempList
                else {
                    tempList.add(ingrdnt);
                    }
                }
            }
            catch (NullPointerException ignored) {
                //Rätten är null, vi går vidare.
            }
        }

        ingredients = tempList;
        System.out.println(ingredients);
        //Skapar ett intent och skickar med shopList
        Intent intent = new Intent(this, com.inkopslistan2.ListActivity.class);
        intent.putExtra("ingredients", ingredients);

        //intent.putExtra(ListActivity.SHOPPING_LIST, shopList);
        //startar aktiviteten ListActivity
        this.startActivity(intent);
    }

    //Metod för att se om ingrediensen redan förekommer i tempList
    private boolean isPresent(String seekWord){
        //for each-loop
        for(Ingredient v:tempList) {
            //om ingrediensen finns med i tempList
            if (v.getIngrdnt().equals(seekWord)){
                return true;}
        }
        return false;
    }

    //Metod för att addera samman ingredienser som förekommer flera gånger.
    public void addQuantities(String seekWord, double quantity){
        for(Ingredient v: tempList) {
            if (v.getIngrdnt().equals(seekWord)){
                //mängden för sökordets position tas fram
                v.setAmount(v.getAmount()+quantity);
            }
        }
    }

    public void add(View caller) {


        MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(InventActivity.this);
        mBuilder.setTitle(R.string.dialog_title);
        mBuilder.setView(R.layout.add_items_dialog);
        mBuilder.setMessage("Enter your basic details");
        mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //checkedItemsList[currDish] = checkedItems;
            }
        });
        mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    /*final MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(this);
    FloatingActionButton fAButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
    fAButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Inflate Custom alert dialog view
            @SuppressLint("ResourceType") ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.layout.activity_invent);
            LayoutInflater inflater = getLayoutInflater();
            View myLayout = inflater.inflate(R.layout.add_items_dialog, mainLayout, false);
            mBuilder.setView(myLayout);
        }
    });*/
    }


    public void back(View caller){
        finish();
    }
}

