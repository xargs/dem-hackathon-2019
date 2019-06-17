package com.example.myfirstapp.picklist.components;

import android.util.Log;

public abstract class SwipeControllerActions {

    public void onLeftClicked(int position) {
        Log.i("Clicked button","Left");
    }

    public void onRightClicked(int position) {
        Log.i("Clicked button","Right");
    }

}
