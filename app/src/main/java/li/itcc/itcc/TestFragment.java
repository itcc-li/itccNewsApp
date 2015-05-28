package li.itcc.itcc;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

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
        new RetrieveFromNetwork().execute("http://www.google.ch");
    }

    class RetrieveFromNetwork extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            String download = null;
            try {
                download = CalendarUtils.downloadUrl(urls[0]);
            } catch (IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
            }
            return download;
        }

        protected void onPostExecute(String download) {
            TextView tview = (TextView) getActivity().findViewById(R.id.TextViewTestFragment);
            tview.setText(download);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }
}
