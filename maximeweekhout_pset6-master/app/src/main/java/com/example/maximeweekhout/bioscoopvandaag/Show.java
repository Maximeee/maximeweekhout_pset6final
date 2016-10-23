package com.example.maximeweekhout.bioscoopvandaag;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by DucoSebel on 23-10-16.
 */
public class Show implements Serializable {

    private int start, end;
    private int durationInMinutes;
    private String ticketUrl;

    public Show(JSONObject o) throws Exception {
        this.start = o.has("start_time") ? o.getInt("start_time") : 0;
        this.end = o.has("end_time") ? o.getInt("end_time") : 0;
        this.durationInMinutes = o.has("duration") ? o.getInt("duration") : 0;
        this.ticketUrl = o.has("ticket_url") ? o.getString("ticket_url") : "";
    }

    /**
     * Get duration in minutes
     * @return duration
     */
    public int getDuration() {
        return durationInMinutes;
    }

    /**
     * Get parsed start time
     * @return time
     */
    public String getStart() {
        return getTimeFrom("start");
    }

    /**
     * Get parsed end time
     * @return time
     */
    public String getEnd() {
        return getTimeFrom("end");
    }

    /**
     * Get ticketURL
     * @return url
     */
    public String getTicketUrl() {
        return ticketUrl;
    }

    /**
     * Parse time from UNIX timestamp
     * @param startOrEnd String start or end
     * @return String with HH:mm format or empty when an error encountered
     */
    private String getTimeFrom(String startOrEnd) {
        int time = 0;
        switch (startOrEnd){
            case "start":
                time = this.start;
                break;

            case "end":
                time = this.end;
                break;

            default:
                return "";
        }
        try{
            // FIXME: 23-10-2016 timezone bug
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(time * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }

        return "";
    }


}
