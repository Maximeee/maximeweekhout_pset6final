package com.example.maximeweekhout.bioscoopvandaag;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maximeweekhout.bioscoopvandaag.Movie;
import com.example.maximeweekhout.bioscoopvandaag.MovieStorage;
import com.example.maximeweekhout.bioscoopvandaag.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView title, year, genre, actors, plot;
    String id;
    CheckBox checkbox;
    ImageView poster;
    ListView timetable;

    Movie movie;

    MovieStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        movie = (Movie) bundle.getSerializable("movie");

        storage = new MovieStorage(this);

        title = (TextView) findViewById(R.id.vTitle);
        year = (TextView) findViewById(R.id.vYear);
        genre = (TextView) findViewById(R.id.vGenre);
        actors = (TextView) findViewById(R.id.vActors);
        plot = (TextView) findViewById(R.id.vPlot);
        checkbox = (CheckBox) findViewById(R.id.vCheckbox);
        poster = (ImageView) findViewById(R.id.vPoster);
        timetable = (ListView) findViewById(R.id.vTimetable);
        poster.setBackgroundColor(Color.rgb(230, 230, 230));

//        String movieId;
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if (extras == null) {
//                movieId = null;
//            } else {
//                movieId = extras.getString("Movie");
//            }
//        } else {
//            movieId = (String) savedInstanceState.getSerializable("Movie");
//        }

//        checkbox.setChecked(storage.exists(movieId));
//        new Omdb().execute("http://www.omdbapi.com/?i=" + movieId);
        updateTimeTable();
    }

    void onCheckboxClicked (View v) {
        if (checkbox.isChecked()) {
            storage.add(id);
        } else {
            storage.remove(id);
        }
    }
    void updateDetailPage () {

//        new ImageFromUrl().execute(movie.getImageUrl());

        title.setText(movie.getTitle());
//        year.setText(movie.getYear());
//        genre.setText(movie.getGenre());
//        actors.setText(movie.getActors());
//        plot.setText(movie.getPlot());
//        id = movie.getId();

    }

    void updateTimeTable() {
        timetable = (ListView) findViewById(R.id.vTimetable);

        List<String> your_array_list = new ArrayList<String>();

        for (int i = 0; i < movie.getShows().size(); i++) {
            Show show = movie.getShows().get(i);
            String start = show.getStart();
            String end = show.getEnd();
            your_array_list.add(start + " - " + end);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        timetable.setAdapter(arrayAdapter);
    }

    // https://developer.android.com/reference/android/os/AsyncTask.html
    public class Omdb extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            DetailActivity.this.updateDetailPage();

        }
    }

    // https://developer.android.com/reference/android/os/AsyncTask.html
    class ImageFromUrl extends AsyncTask<String, Void, Drawable> {

        protected Drawable doInBackground(String... url) {

            try {
                InputStream is = (InputStream) new URL(url[0]).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (Exception e) {
                return null;
            }

        }

        protected void onPostExecute(Drawable image) {
            DetailActivity.this.poster.setImageDrawable(image);
        }
    }
}
