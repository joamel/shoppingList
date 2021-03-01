package com.inkopslistan2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooserActivity extends AppCompatActivity implements Serializable {
    public static final String PARAM_INSTRUCTION = "instr";
    public static final String PARAM_CHOICES = "choices";
    public static final String RETURN_VALUE = "return";
    private ArrayList<Recipe> recipes, menu;
    //private ArrayList<ArrayList<String>> dishList;
    private ArrayList<Integer> indexList;
    ListView myList;
    private String selectedString = "";
    private Recipe selectedRec;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        //String[] array = this.getIntent().getStringArrayExtra(PARAM_CHOICES);
        Intent intent = getIntent();

        recipes = this.getIntent().getParcelableArrayListExtra("recipes");
        menu = this.getIntent().getParcelableArrayListExtra("menu");
        ArrayList<String> tempDish = new ArrayList<>();

        //recipes = (ArrayList<Recipe>) intent.getSerializableExtra("ArrayList");
        //CharSequence[] cs = recipes.toArray(new CharSequence[recipes.size()]);


        //Sektion för att enbart visa icke-valda alternativ i listViewn
        tempDish.add("Välj rätt");

        for(Recipe rec:recipes) {
            try {
                tempDish.add(rec.name);
            }
            catch(NullPointerException e) {
                Log.v("myTag", String.valueOf(e));
            }
        }
        for (Recipe men:menu) {
            try {
                tempDish.remove(men.name);
            }
            catch(NullPointerException e) {
                Log.v("myTag", String.valueOf(e));
            }

        }



        /*
        for (int i = 0; i < recipes.size(); i++) {
            try {
                tempDish.add(recipes.get(i).name);
                assert menu != null;
                for (int j = 0; j < menu.size(); j++) {
                    if(!recipes.contains(menu.get(j)))
                        tempDish.remove(menu.get(j).name);
                }
            }
            catch (NullPointerException e) {
                Log.v("myTag", String.valueOf(e));
            }*/


        final String[][] dishList = new String[recipes.size()][];

        int i = 0;
        for (Recipe rec: recipes) {
            final String[] ingrdntList = new String[rec.ingredients.size()];
            for (int j = 0; j < rec.ingredients.size(); j++) {
                if (rec.ingredients.get(j).getAmount() != 0)
                    ingrdntList[j] = rec.ingredients.get(j).getAmount() + " " + rec.ingredients.get(j).getIngrdnt();
                else
                    ingrdntList[j] = rec.ingredients.get(j).getIngrdnt();
            }
            dishList[i] = ingrdntList;
            i++;
        }

        myList = findViewById(R.id.listView);
        myList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tempDish));
        Helper.getListViewSize(myList);

        Toast.makeText(ChooserActivity.this,"Håll inne valet för att se recept.",
                Toast.LENGTH_SHORT).show();

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ChooserActivity.this);
                if(pos > 0) {
                    mBuilder.setTitle(R.string.dialog_title);
                    mBuilder.setItems(dishList[pos-1], null);
                    mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog mDialog = mBuilder.create();
                    mDialog.show();
                }
                return true;
            }
        });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
              if (pos > 0)
                  selectedString = myList.getItemAtPosition(pos).toString();
              quit();
          }
          public void quit() {
              Intent returnIntent = new Intent();
              for(Recipe rec:recipes){
                  if(selectedString.equals(rec.name)) {
                      returnIntent.putExtra(RETURN_VALUE, rec);
                      setResult(RESULT_OK, returnIntent);
                      finish();
                  }
              }
          }
        });
    }
}
