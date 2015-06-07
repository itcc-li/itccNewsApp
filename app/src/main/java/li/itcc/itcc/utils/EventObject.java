package li.itcc.itcc.utils;

import android.content.Intent;
import android.provider.CalendarContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class EventObject {

    //Eventparameters
    private String type = "vnd.android.cursor.item/event";
    private String title = null;
    private String eventLocation = null;
    private String description = null;
    private long beginTime = 0;
    private long endTime = 0;
    private boolean allDay = false; // full day Event
    private String uid = null;

    private Intent intent = null;

    //constructor
    EventObject(Intent intent) {
        this.intent = intent;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public static Long getDate(int year, int month, int day, int hour, int minute) {
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

    public void setBeginTime(String beginTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        formatter.setLenient(false);

        String oldTime = beginTime;
        Date oldDate = null;
        try {
            oldDate = formatter.parse(oldTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        long beginTimeLong = oldDate.getTime();

        this.beginTime = beginTimeLong;
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, this.beginTime);
    }

    public void setEndTime(String endTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        formatter.setLenient(false);

        String oldTime = endTime;
        Date oldDate = null;
        try {
            oldDate = formatter.parse(oldTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        long endTimeLong = oldDate.getTime();

        this.endTime = endTimeLong;
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, this.endTime);
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}