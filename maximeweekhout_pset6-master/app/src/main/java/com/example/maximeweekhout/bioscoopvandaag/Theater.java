package com.example.maximeweekhout.bioscoopvandaag;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Maxim on 18-10-2016.
 */

public class Theater {

    private String name, url, city, company;

    public Theater(JSONObject o) throws JSONException {
        this.name = o.has("name") ? o.getString("name") : "Unknown";
        this.url = o.has("url") ? o.getString("url") : "Unknown";
        this.city = o.has("city") ? o.getString("city") : "Unknown";
        this.company = o.has("company") ? o.getString("company") : "Unknown";
    }

    /**
     * get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get url
     * @return url
     */
    public String getUrl() {
        return url;
    }
}
