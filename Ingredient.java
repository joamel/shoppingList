package com.inkopslistan2;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;

public class Ingredient implements Parcelable {
    private String ingrdnt;
    private double amount;
    private String unit;
    private String cat;
    private boolean checkedItem;

    //   konstruktor
    //public Ingredient(String ingrdnt, double amount, String unit) {
    public Ingredient(String ingrdnt, double amount, String unit, String category) {

        this.ingrdnt = ingrdnt;
        this.amount = amount;
        this.unit = unit;
        this.cat = category;
        this.checkedItem = false;
    }

    /*Comparator for sorting the list by roll no*/
    public static Comparator<Ingredient> sortIngrdnts = new Comparator<Ingredient>() {

        public int compare(Ingredient s1, Ingredient s2) {

            String cat1 = s1.getCat().toUpperCase();
            String cat2 = s2.getCat().toUpperCase();

            //ascending order
            return cat1.compareTo(cat2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };
    @Override
    public String toString () {
        return "cat= " + cat + ", Ingredient= " + ingrdnt + ", Unit= " + unit;
    }


    protected Ingredient(Parcel in) {
        ingrdnt = in.readString();
        amount = in.readDouble();
        unit = in.readString();
        cat = in.readString();
        checkedItem = in.readByte() != 0;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getIngrdnt() {
        return ingrdnt;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amnt) {
        amount = amnt;
    }

    public String getUnit() {
        return unit;
    }

    public String getCat() {
        return cat;
    }

    public boolean getChecked() {
        return checkedItem;
    }

    public void setChecked(boolean checked) {
        checkedItem = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ingrdnt);
        parcel.writeDouble(amount);
        parcel.writeString(unit);
        parcel.writeString(cat);
        parcel.writeByte((byte) (checkedItem ? 1 : 0));
    }
}
