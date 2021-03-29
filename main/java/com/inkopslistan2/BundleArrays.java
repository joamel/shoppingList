package com.inkopslistan2;

import java.io.Serializable;

public class BundleArrays implements Serializable {
    //public final static int serialVersionUID = let eclipse generate your uid
    public boolean[][] myArray = null;
    public BundleArrays (boolean[][] _myArray) {
        this.myArray = _myArray;
    }

    public void restore (Serializable array) {
        //
    }
}
