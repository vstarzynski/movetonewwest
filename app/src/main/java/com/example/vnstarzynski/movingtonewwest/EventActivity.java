package com.example.vnstarzynski.movingtonewwest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity implements Serializable {
    private List<Event> eventList;
    private List<String> name;
    private ProgressDialog pDialog;
    private String service_url = "http://opendata.newwestcity.ca/downloads/cultural-events/EVENTS.json";
    private ListView lv;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        lv = (ListView) findViewById(R.id.event_list);
        eventList = new ArrayList<>();
        name = new ArrayList<>();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Events");

        new EventAsyncTask().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String eventName =  ((TextView)view).getText().toString();
                Event eventDetail = null;
                for (int j = 0; j < eventList.size(); j++) {
                    if(eventList.get(j).getName().equalsIgnoreCase(eventName)) {
                        eventDetail = eventList.get(j);
                        break;
                    }
                }
                if(i >= 0) {
                    Intent intent = new Intent(EventActivity.this, EventDetailActivity.class);
                    intent.putExtra("detail", eventDetail);
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class EventAsyncTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "EventActivity";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(EventActivity.this);
            pDialog.setMessage("Getting data");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(service_url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    // looping through All Contacts
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        JSONObject geo = o.getJSONObject("json_geometry");
                        JSONArray coordinate = geo.getJSONArray("coordinates");
                        Event event = new Event();
                        name.add(o.getString("Name"));
                        event.setName(o.getString("Name"));
                        event.setDescription(o.getString("Descriptn"));
                        event.setSummary(o.getString("summary"));
                        event.setLocation(o.getString("Address"));
                        event.setPhoneNumber(o.getString("phone"));
                        event.setEmail(o.getString("email"));
                        event.setWebsite(o.getString("website"));
                        event.setLongitude(coordinate.getDouble(0));
                        event.setLatitude(coordinate.getDouble(1));
                        event.setPostalCode(o.getString("pcode"));
                        eventList.add(event);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) pDialog.dismiss();

            // Attach the adapter to a ListView
            arrayAdapter = new ArrayAdapter<String>(
                    EventActivity.this, android.R.layout.simple_list_item_1, name
            );
            lv.setAdapter(arrayAdapter);
        }
    }
}
