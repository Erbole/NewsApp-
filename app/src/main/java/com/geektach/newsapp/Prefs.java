package com.geektach.newsapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveBoardState(){
        preferences.edit().putBoolean("isBoardShown", true).apply();
    }

    public Boolean isBoardShown(){
        return preferences.getBoolean("isBoardShown", false);
    }
}

