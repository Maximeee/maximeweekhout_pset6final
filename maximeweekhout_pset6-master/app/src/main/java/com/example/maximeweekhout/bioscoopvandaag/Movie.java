package com.example.maximeweekhout.bioscoopvandaag;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 18-10-2016.
 */

public class Movie implements Serializable {
    private String title, theater;
    private List<Show> shows = new ArrayList<>();
    private String imdbId, posterUrl;

    // https://developer.android.com/reference/org/json/JSONObject.html
    // http://www.tutorialspoint.com/android/android_json_parser.htm
    Movie(JSONObject o) throws Exception {
        this.title = o.has("title") ? o.getString("title") : "Unknown Title";

        this.theater = o.has("theaterName") ? o.getString("theaterName") : "";

        if (o.has("times")) {
            JSONArray times =  o.getJSONArray("times");
            for (int i = 0; i < times.length() ; i++) {
                shows.add(new Show(times.getJSONObject(i)));
            }
        }

        if (o.has("possibleImdb")) {
            if (!o.getString("possibleImdb").equals("null")) {
                JSONObject imdb = o.getJSONObject("possibleImdb");
                this.imdbId = imdb.has("imdbID") ? imdb.getString("imdbID") : null;
                this.posterUrl = imdb.has("Poster") ? imdb.getString("Poster") : null;
            }
        }
    }

    /**
     * Get title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get shows
     * @return array
     */
    public List<Show> getShows() {
        return shows;
    }

    /**
     * Return ImdbID when available
     * @return id or empty string
     */
    public String getImdbId() {
        if (imdbId == null) {
            return "";
        }
        return imdbId;
    }

    /**
     * Return poster url when available
     * @return url or empty string
     */
    public String getPoster() {
        if (posterUrl == null) {
            return "";
        }
        return posterUrl;
    }

    public String getBioscoop() {
        return theater;
    }
}
