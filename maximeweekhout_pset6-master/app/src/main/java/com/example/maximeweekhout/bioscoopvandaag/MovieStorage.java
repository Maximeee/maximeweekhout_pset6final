package com.example.maximeweekhout.bioscoopvandaag;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Maxime Weekhout on 25-9-2016.
 */

// https://developer.android.com/guide/topics/data/data-storage.html
public class MovieStorage {

    public static final String PREFS_NAME = "movieListStorage";
    public static final int VERSION = 1;

    private Context context;
    private SharedPreferences sharedPreferences;

    public MovieStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME + VERSION, 0);
    }

    public void add(StorableShow show) {

        List<String> list = this.get();
        list.add(show.getJson());

        /**
         * Save the tast list to preference
         */
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {

            editor.putStringSet("jsonData", new HashSet<String>(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }


    public List<String> get() {

        List<String> list = new ArrayList<String>();

        Set<String> data = sharedPreferences.getStringSet("jsonData", null);
        if (data != null){
            list.addAll(data);
        }

        if (null == list) {
            return new ArrayList<String>();
        }

        return list;
    }

    /**
     * Removes items from list
     * @param show
     */
    public void remove(StorableShow show) {

        List<String> list = this.get();

        for (String item: list) {
            try {
                StorableShow showItem = new StorableShow(item);

                if (showItem.getTitle().equals(show.getTitle()) &&
                showItem.getShow().getStart().equals(show.getShow().getStart())) {
                    list.remove(item);
		    break;
                }
            } catch (Exception e) {

            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("jsonData", new HashSet<>(list));
        editor.commit();
    }
}

