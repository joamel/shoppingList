package com.inkopslistan2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChooserActivity extends AppCompatActivity implements Serializable {
    public static final String PARAM_INSTRUCTION = "instr";
    public static final String PARAM_CHOICES = "choices";
    public static final String RETURN_VALUE = "return";
    private ArrayList<Recipe> recipes, menu, tempList;
    //private ArrayList<ArrayList<String>> dishList;
    private ArrayList<Integer> indexList;
    ListView myList;
    private String selectedString = "";
    private Recipe selectedRec;


    @RequiresApi(api = Build.VERSION_CODES.N)
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
        ArrayList<Recipe> tempList = new ArrayList<>();

        //tempList = menu.removeAll(Collections.singelton(null));
        //tempList = menu.removeAll(Collections.(null));
        /*List<Recipe> tempList = menu.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());*/
        //for(Recipe temp: tempList)
        //    System.out.println(temp);



        //Sektion för att enbart visa icke-valda alternativ i listViewn
        tempDish.add("Välj rätt");

        for(Recipe rec:recipes) {
            //if(rec.name != null)
            tempDish.add(rec.name);
            tempList.add(rec);
        }

        for (Recipe men:menu) {
            if (men != null) {
                int idx = tempDish.indexOf(men.name);
                tempDish.remove(men.name);
                //-1 eftersom tempDish har "Välj rätt" på index 0.
                tempList.remove(idx - 1);
            }
        }

        final String[][] dishList = new String[tempList.size()][];
        int i = 0;
        for (Recipe rec: tempList) {
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
