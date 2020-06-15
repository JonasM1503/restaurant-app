package com.example.restaurant_app.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon Rothmann
 * @content adapter for SharedPreferences to save objects locally on the phone
 */
public class SharedPreferencesAdapter {
    public static void setDefaults(String key, String value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void setListDefaults(String key, List<String> arr, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(key + "_size", arr.size());

        for(int i=0; i<arr.size(); i++)
        {
            editor.remove(key + "_" + i);
            editor.putString(key + "_" + i, arr.get(i));
        }

        editor.commit();
    }
    public static List<String> getListDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        List<String> arr = new ArrayList();

        int size = preferences.getInt(key + "_size", 0);

        for(int i=0; i<size; i++)
        {
            arr.add(preferences.getString(key + "_" + i, null));
        }

        return arr;
    }
}
