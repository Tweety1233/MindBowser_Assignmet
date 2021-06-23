package com.mindbowser.assignmet.repository;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedRepository implements IDataSource.SharedData {

    SharedPreferences sharedPreferences;
    Context context;
    String filename = "mindbowser";

    public SharedRepository(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
    }


    @Override
    public void setUpdate(Boolean b) {
        sharedPreferences.edit().putBoolean("update", b).apply();

    }

    @Override
    public Boolean getUpdate() {
        return sharedPreferences.getBoolean("update", false);
    }


}