package com.example.maximeweekhout.bioscoopvandaag;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    ListView storageList;
    MovieStorage storage;

    List<String> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set storage
        storage = new MovieStorage(this);

        // Configure FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, MovieTheatreListActivity.class);
                startActivity(intent);
            }
        });

        // Configure list
        storageList = (ListView) findViewById(R.id.listView);
        storageList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StartActivity.this, ShowDetailActivity.class);
                intent.putExtra("showJson", elements.get(position));
                startActivity(intent);
            }
        });
        updateList();
    }

    /**
     * Update list on resume of view
     * This is uses on backButtonPress, when a show is possibly stored
     */
    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    /**
     * Update the list when the activity starts, or is reloaded
     */
    private void updateList() {
        List<String> list = new ArrayList<String>();

        // Get current elements from storage
        elements = storage.get();

        // Put them into the list
        for (int i = 0; i <  elements.size(); i++) {
            StorableShow show = new StorableShow(elements.get(i));
            list.add(show.getShow().getDate() + " om " + show.getShow().getStart() + " - \"" + show.getTitle() + "\""
            );
        }

        // Set Adapter and update list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list);
        storageList.setAdapter(arrayAdapter);
    }

}
