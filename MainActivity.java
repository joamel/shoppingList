package com.inkopslistan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    private String[] dishes;
    private Map<String, String> amountDict = new HashMap<>();
    private Map<String, String> dishDict = new HashMap<>();
    private Map<String, String> unitDict = new HashMap<>();
    private Map<String, String> catDict = new HashMap<>();
    private String dishString;
    private ArrayAdapter<String> adapter;
    private Spinner spin1;
    //private ArrayList<String> menu = new ArrayList<>(Collections.nCopies(7, ""));

    private String SAVE_OBJECT_FILE =  "recipes.dat";
    private String TAG   = "MYTAG";

    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    private ImageView dish1,dish2,dish3,dish4,dish5,dish6,dish7;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    Recipe recipe;

    private ArrayList<String> shoppingList = new ArrayList<>();
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Recipe> menu = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();


    private final int DISH_1 = 1;
    private final int DISH_2 = 2;
    private final int DISH_3 = 3;
    private final int DISH_4 = 4;
    private final int DISH_5 = 5;
    private final int DISH_6 = 6;
    private final int DISH_7 = 7;
    private int DISH_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Försöker läsa in objektet från fil
        try {
            FileInputStream file = openFileInput(SAVE_OBJECT_FILE);
            ObjectInputStream in = new ObjectInputStream(file);
            while(true) {
                Recipe recipe = (Recipe) in.readObject();
                if(!recipes.contains(recipe)) {
                    recipes.add(recipe);
                }
            }
        }
        catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this,"Filen fanns ej.",
                    Toast.LENGTH_SHORT).show();

        } catch (ClassNotFoundException e) {
            Toast.makeText(MainActivity.this,"Klassen fanns ej.",
                    Toast.LENGTH_SHORT).show();

            //IOException kommer alltid kastas vid första tomma raden i filen.
        } catch (IOException ignored) {
        }

        for(Recipe rec:recipes){
            String str = String.valueOf(rec.getIngredients());
            //System.out.println(str);
        }

        dish1 = findViewById(R.id.iv_1);
        dish1.setOnClickListener(this);
        tv1 = findViewById(R.id.textView1);
        dish2 = findViewById(R.id.iv_2);
        dish2.setOnClickListener(this);
        tv2 = findViewById(R.id.textView2);
        dish3 = findViewById(R.id.iv_3);
        dish3.setOnClickListener(this);
        tv3 = findViewById(R.id.textView3);
        dish4 = findViewById(R.id.iv_4);
        dish4.setOnClickListener(this);
        tv4 = findViewById(R.id.textView4);
        dish5 = findViewById(R.id.iv_5);
        dish5.setOnClickListener(this);
        tv5 = findViewById(R.id.textView5);
        dish6 = findViewById(R.id.iv_6);
        dish6.setOnClickListener(this);
        tv6 = findViewById(R.id.textView6);
        dish7 = findViewById(R.id.iv_7);
        dish7.setOnClickListener(this);
        tv7 = findViewById(R.id.textView7);

        for (int i = 0; i < 7; i++) {
            menu.add(null);
        };

        //Skapar dictionaries för mängder, ingredienser, och enheter från strings.xml
        amountDict.put("Lasagne", "lasagne_amount");
        amountDict.put("Bolognese", "bolognese_amount");
        amountDict.put("Pizza Bianca", "pizza_bianca_amount");
        amountDict.put("Spenatpaj", "spenatpaj_amount");
        amountDict.put("Grönkålspaj", "gronkalspaj_amount");
        amountDict.put("Falafel", "falafel_amount");
        amountDict.put("Tacos", "tacos_amount");
        amountDict.put("Risotto", "risotto_amount");
        amountDict.put("Palak Paneer", "palak_paneer_amount");
        amountDict.put("Zucchinipasta", "zucchinipasta_amount");

        dishDict.put("Lasagne", "lasagne");
        dishDict.put("Bolognese", "bolognese");
        dishDict.put("Pizza Bianca", "pizza_bianca");
        dishDict.put("Spenatpaj", "spenatpaj");
        dishDict.put("Grönkålspaj", "gronkalspaj");
        dishDict.put("Falafel", "falafel");
        dishDict.put("Tacos", "tacos");
        dishDict.put("Risotto", "risotto");
        dishDict.put("Palak Paneer", "palak_paneer");
        dishDict.put("Zucchinipasta", "zucchinipasta");

        unitDict.put("Lasagne", "lasagne_unit");
        unitDict.put("Bolognese", "bolognese_unit");
        unitDict.put("Pizza Bianca", "pizza_bianca_unit");
        unitDict.put("Spenatpaj", "spenatpaj_unit");
        unitDict.put("Grönkålspaj", "gronkalspaj_unit");
        unitDict.put("Falafel", "falafel_unit");
        unitDict.put("Tacos", "tacos_unit");
        unitDict.put("Risotto", "risotto_unit");
        unitDict.put("Palak Paneer", "palak_paneer_unit");
        unitDict.put("Zucchinipasta", "zucchinipasta_unit");

        catDict.put("Lasagne", "lasagne_cat");
        catDict.put("Bolognese", "bolognese_cat");
        catDict.put("Pizza Bianca", "pizza_bianca_cat");
        catDict.put("Spenatpaj", "spenatpaj_cat");
        catDict.put("Grönkålspaj", "gronkalspaj_cat");
        catDict.put("Falafel", "falafel_cat");
        catDict.put("Tacos", "tacos_cat");
        catDict.put("Risotto", "risotto_cat");
        catDict.put("Palak Paneer", "palak_paneer_cat");
        catDict.put("Zucchinipasta", "zucchinipasta_cat");

        dishes = getResources().getStringArray(R.array.dishes);

        String[] amountList, ingredientList, unitList, catList;
        String amountName, dishName, unitName, catName;
        int amountId, dishId, unitId, catId;
        for (int i = 0; i < dishes.length; i++) {
            recipe = new Recipe(dishes[i]);
            amountName = amountDict.get(dishes[i]);
            amountId = getResources().getIdentifier(amountName, "array", getPackageName());
            amountList = getResources().getStringArray(amountId);

            dishName = dishDict.get(dishes[i]);
            dishId = getResources().getIdentifier(dishName, "array", getPackageName());
            ingredientList = getResources().getStringArray(dishId);

            unitName = unitDict.get(dishes[i]);
            unitId = getResources().getIdentifier(unitName, "array", getPackageName());
            unitList = getResources().getStringArray(unitId);

            catName = catDict.get(dishes[i]);
            catId = getResources().getIdentifier(catName, "array", getPackageName());
            catList = getResources().getStringArray(catId);

            recipe.addIngredients(amountList, ingredientList, unitList, catList);
            recipes.add(recipe);
        }
        writeToFile();
        //readFromFile();*/

    }

    /*@Override
    protected void onPause()     {
        super.onPause();
        try       {
            FileOutputStream fos = openFileOutput(SAVE_OBJECT_FILE, Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fos);
            for(Recipe rec: recipes)
            {
                pw.println(rec);

            }
            pw.close();
        }
        catch (FileNotFoundException e) {
            Log.e(TAG ,e.getMessage(),e);
        }
    }*/



    /**
     * Sparar kunder till fil.
     */
    public void writeToFile() {

        if(recipes.size() > 0)
        {
            //Försöker skriva till fil
            try
            {
                FileOutputStream file = openFileOutput(SAVE_OBJECT_FILE, Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream(file);
                //Skriver sedan alla objekt till filen
                for(Recipe rec: recipes)
                {
                    out.writeObject(rec);
                }
                //Stänger filen
                out.close();
            }
            catch (FileNotFoundException e)
            {
                Toast.makeText(MainActivity.this,"Filen fanns ej.",
                        Toast.LENGTH_SHORT).show();
            }
            //Fångar eventuella problem som kan uppstå
            catch (IOException ex)
            {
                Toast.makeText(MainActivity.this,"IOException uppstod.",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            //Om inga recept finns, skrivs inget till filen.
            Toast.makeText(MainActivity.this,"Inga recept att spara.",
                    Toast.LENGTH_SHORT).show();
        }
        //readFromFile();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_1:
                DISH_ID = DISH_1;
                break;
            case R.id.iv_2:
                DISH_ID = DISH_2;
                break;
            case R.id.iv_3:
                DISH_ID = DISH_3;
                break;
            case R.id.iv_4:
                DISH_ID = DISH_4;
                break;
            case R.id.iv_5:
                DISH_ID = DISH_5;
                break;
            case R.id.iv_6:
                DISH_ID = DISH_6;
                break;
            case R.id.iv_7:
                DISH_ID = DISH_7;
                break;
            default:
                break;
        }

    String instruction = "Välj veckans meny";
    Intent intent = new Intent(this, com.inkopslistan2.ChooserActivity.class);
    intent.putExtra("recipes", recipes);
    intent.putExtra("menu", menu);
    //intent.putExtra("ArrayList", (Serializable) recipes);
    startActivityForResult(intent, DISH_ID);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Recipe rec = data.getParcelableExtra(ChooserActivity.RETURN_VALUE);

            assert rec != null;
            int ivId = getResources().getIdentifier(rec.iv.toLowerCase(), "drawable", getPackageName());
            if (requestCode == DISH_1) {
                ImageView iv_1 = findViewById(R.id.iv_1);
                iv_1.setImageResource(ivId);
                tv1.setText(rec.name);
                menu.set(0,rec);
            } else if (requestCode == DISH_2) {
                ImageView iv_2 = findViewById(R.id.iv_2);
                iv_2.setImageResource(ivId);
                tv2.setText(rec.name);
                menu.set(1,rec);
            } else if (requestCode == DISH_3) {
                ImageView iv_3 = findViewById(R.id.iv_3);
                iv_3.setImageResource(ivId);
                tv3.setText(rec.name);
                menu.set(2,rec);
            } else if (requestCode == DISH_4) {
                ImageView iv_4 = findViewById(R.id.iv_4);
                iv_4.setImageResource(ivId);
                tv4.setText(rec.name);
                menu.set(3,rec);
            } else if (requestCode == DISH_5) {
                ImageView iv_5 = findViewById(R.id.iv_5);
                iv_5.setImageResource(ivId);
                tv5.setText(rec.name);
                menu.set(4,rec);
            } else if (requestCode == DISH_6) {
                ImageView iv_6 = findViewById(R.id.iv_6);
                iv_6.setImageResource(ivId);
                tv6.setText(rec.name);
                menu.set(5,rec);
            } else if (requestCode == DISH_7) {
                ImageView iv_7 = findViewById(R.id.iv_7);
                iv_7.setImageResource(ivId);
                tv7.setText(rec.name);
                menu.set(6,rec);
            }
        }
    }

    public void onInvent(View caller) {

        /* Sorting on category*/
        /*System.out.println("Category Sorting:");
        Collections.sort(menu.get(0).ingredients, Ingredient.sortIngrdnts);
        for(Ingredient str: menu.get(0).ingredients){
            System.out.println(str);
        }*/
        Intent intent = new Intent(this, com.inkopslistan2.InventActivity.class);
        intent.putExtra("menu", menu);

        this.startActivity(intent);

    }

}
