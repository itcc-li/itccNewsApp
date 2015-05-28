package li.itcc.itcc.utils;

import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.GregorianCalendar;

public class CalendarUtils {

    private static final String DEBUG_TAG = "HttpExample";

    public static String downloadUrl(String myurl) throws IOException {

        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(100000 /* milliseconds */);
            conn.setConnectTimeout(150000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public static String readIt(InputStream stream, int len) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public Intent createCalendarIntent() {
        //http://developer.android.com/guide/topics/providers/calendar-provider.html#intents

        // ACTION_INSERT does not work on all phones
        // use  Intent.ACTION_EDIT in this case
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);

        EventObject eventObj = new EventObject(intent);
        eventObj.setTitle("abc");
        eventObj.setBeginTime(2015, 05, 26, 17, 0);
        eventObj.setEndTime(2015, 05, 26, 18, 0);
        intent = eventObj.getIntent();

        return intent;
    }

    private class EventObject {

        //Eventparameters
        private String type = "vnd.android.cursor.item/event";
        private String title = null;
        private String eventLocation = null;
        private String description = null;
        private long beginTime = 0;
        private long endTime = 0;
        private boolean allDay = false; // full day Event

        private Intent intent = null;

        //constructor
        EventObject(Intent intent) {
            this.intent = intent;
        }

        public Intent getIntent() {
            return this.intent;
        }

        private Long getDate(int year, int month, int day, int hour, int minute) {
            GregorianCalendar calDate = new GregorianCalendar(year, month, day, hour, minute);
            return calDate.getTimeInMillis();
        }

        //Setters for event Parameters
        public void setType(String type) {
            this.type = type;
            intent.setType(this.type);
        }

        public void setTitle(String title) {
            this.title = title;
            intent.putExtra(CalendarContract.Events.TITLE, this.title);
        }

        public void setEventLocation(String eventLocation) {
            this.eventLocation = eventLocation;
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, this.eventLocation);
        }

        public void setDescription(String description) {
            this.description = description;
            intent.putExtra(CalendarContract.Events.DESCRIPTION, this.description);
        }

        public void setBeginTime(int year, int month, int day, int hour, int minute) {
            this.beginTime = getDate(year, month, day, hour, minute);
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, this.beginTime);
        }

        public void setEndTime(int year, int month, int day, int hour, int minute) {
            this.endTime = getDate(year, month, day, hour, minute);
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, this.endTime);
        }

        public void setAllDay(boolean allDay) {
            this.allDay = allDay;
            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        }
    }
}