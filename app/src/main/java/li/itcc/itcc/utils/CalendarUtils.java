package li.itcc.itcc.utils;

import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CalendarUtils {

    public Intent createCalendarIntent(String title, String eventLocation, String description, String startDate, String endDate, String uid) {
        //http://developer.android.com/guide/topics/providers/calendar-provider.html#intents

        // ACTION_INSERT does not work on all phones
        // use  Intent.ACTION_EDIT in this case
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);

        EventObject eventObj = new EventObject(intent);
        eventObj.setTitle(title);
        eventObj.setDescription(description);
        eventObj.setEventLocation(eventLocation);
        eventObj.setBeginTime(startDate);
        eventObj.setEndTime(endDate);
        eventObj.setUid(uid);
        intent = eventObj.getIntent();

        return intent;
    }
}