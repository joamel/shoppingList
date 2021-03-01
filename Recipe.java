package com.inkopslistan2;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.text.Normalizer;
import java.util.ArrayList;

public class Recipe implements Parcelable {
    String name;
    ArrayList<Ingredient> ingredients;
    String iv;

    //   konstruktor
    public Recipe(String name){
        this.name = name;
        ingredients = new ArrayList<>();
        iv = name.replace(" ", "_");
        iv = Normalizer.normalize(iv,
                Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        ingredients = new ArrayList<>();
        in.readTypedList(ingredients, Ingredient.CREATOR);
        iv = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    //public void addIngredients(String[] amountlist, String[] ingredientList, String[] unitList) {
    public void addIngredients(String[] amountlist, String[] ingredientList, String[] unitList, String[] catList) {
        for (int i = 0; i < ingredientList.length; i++) {
            ingredients.add(new Ingredient(ingredientList[i], Double.parseDouble(amountlist[i]), unitList[i], catList[i]));
            //ingredients.add(new Ingredient(ingredientList[i], Double.parseDouble(amountlist[i]), unitList[i]));
        }
    }

    public ArrayList<String> getIngredients() {
        ArrayList<String> tempList = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            tempList.add(ingredients.get(i).getIngrdnt());
        }
        return tempList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeString(iv);
    }
}