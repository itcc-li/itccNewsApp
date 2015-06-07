package li.itcc.itcc.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.util.Calendars;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import li.itcc.itcc.R;
import li.itcc.itcc.utils.CalendarUtils;

public class TestFragment extends Fragment {

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RetrieveFromNetwork().execute("http://www.google.com/calendar/ical/itcc.li_3rg7pl88gpl3od8i2rm4utu324%40group.calendar.google.com/public/basic.ics");
    }

    class RetrieveFromNetwork extends AsyncTask<String, Void, Calendar> {

        protected Calendar doInBackground(String... urls) {
            URL down = null;
            Calendar cal = new Calendar();

            try {
                down = new URL("http://www.google.com/calendar/ical/itcc.li_3rg7pl88gpl3od8i2rm4utu324%40group.calendar.google.com/public/basic.ics");
                cal = Calendars.load(down);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserException e) {
                e.printStackTrace();
            }

            return cal;
        }

        protected void onPostExecute(Calendar download) {

            CalendarUtils util = new CalendarUtils();
            Intent intent = null;

            ArrayList<Component> events = download.getComponents();
            int a = 0;
            for (Component c : events) {
                intent = util.createCalendarIntent(c.getProperty("SUMMARY").getValue(), c.getProperty("LOCATION").getValue(), c.getProperty("DESCRIPTION").getValue(), c.getProperty("DTSTART").getValue(), c.getProperty("DTEND").getValue(), c.getProperty("UID").getValue());
                startActivity(intent);
            }


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }
}
