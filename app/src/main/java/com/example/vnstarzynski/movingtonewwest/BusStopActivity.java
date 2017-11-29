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

import java.util.ArrayList;
import java.util.List;

public class BusStopActivity extends AppCompatActivity {
    private List<BusStop> stopList;
    private List<String> stopNumber;
    private ProgressDialog pDialog;
    private String service_url = "http://opendata.newwestcity.ca/downloads/bus-stops/BUS_STOPS.json";
    private ListView lv;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

        lv = (ListView) findViewById(R.id.stopList);
        stopList = new ArrayList<>();
        stopNumber = new ArrayList<>();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Bus Stops");

        new StopAsyncTask().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String stopNumber =  ((TextView)view).getText().toString();
                BusStop stopDetail = null;
                for (int j = 0; j < stopList.size(); j++) {
                    if(Integer.toString(stopList.get(j).getStopNum()).equalsIgnoreCase(stopNumber)) {
                        stopDetail = stopList.get(j);
                        break;
                    }
                }
                if(i >= 0) {
                    Intent intent = new Intent(BusStopActivity.this, BusStopDetailActivity.class);
                    intent.putExtra("detail", stopDetail);
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class StopAsyncTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "BusStopActivity";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(BusStopActivity.this);
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
                        BusStop stop = new BusStop();
                        stopNumber.add(Integer.toString(o.getInt("BUSSTOPNUM")));
                        stop.setStopNum(o.getInt("BUSSTOPNUM"));
                        stop.setOnStreet(o.getString("ONSTREET"));
                        stop.setAtStreet(o.getString("ATSTREET"));
                        stop.setPosition(o.getString("POSITION"));
                        stop.setDirection(o.getString("DIRECTION"));
                        stop.setStatus(o.getString("STATUS"));
                        stop.setLongitude(Double.parseDouble(o.getString("X")));
                        stop.setLatitude(Double.parseDouble(o.getString("Y")));
                        stopList.add(stop);
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
                    BusStopActivity.this, android.R.layout.simple_list_item_1, stopNumber
            );
            lv.setAdapter(arrayAdapter);
        }
    }
}