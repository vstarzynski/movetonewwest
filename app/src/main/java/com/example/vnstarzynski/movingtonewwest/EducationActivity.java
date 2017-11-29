package com.example.vnstarzynski.movingtonewwest;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EducationActivity extends AppCompatActivity implements Serializable {
    private List<Education> educationList;
    private List<String> name;
    private ProgressDialog pDialog;
    private String service_url = "http://opendata.newwestcity.ca/downloads/education/EDUCATION_LANGUAGE_AND_LITERACY.json";
    private ListView lv;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        lv = (ListView) findViewById(R.id.education_list);
        educationList = new ArrayList<>();
        name = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Education");

        new EducationAsyncTask().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String educationName =  ((TextView)view).getText().toString();
                Education educationDetail = null;
                for (int j = 0; j < educationList.size(); j++) {
                    if(educationList.get(j).getName().equalsIgnoreCase(educationName)) {
                        educationDetail = educationList.get(j);
                        break;
                    }
                }
                if(i >= 0) {
                    Intent intent = new Intent(EducationActivity.this, EducationDetailActivity.class);
                    intent.putExtra("detail", educationDetail);
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class EducationAsyncTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "EducationActivity";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(EducationActivity.this);
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
                        Education education = new Education();
                        name.add(o.getString("Name"));
                        education.setName(o.getString("Name"));
                        education.setDescription(o.getString("Description"));
                        education.setCategory(o.getString("Category"));
                        education.setHours(o.getString("Hours"));
                        education.setLocation(o.getString("Location"));
                        education.setPhoneNumber(o.getString("Phone"));
                        education.setEmail(o.getString("Email"));
                        education.setWebsite(o.getString("Website"));
                        education.setLongitude(coordinate.getDouble(0));
                        education.setLatitude(coordinate.getDouble(1));
                        education.setPostalCode(o.getString("PC"));
                        educationList.add(education);
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
                    EducationActivity.this, android.R.layout.simple_list_item_1, name
            );
            lv.setAdapter(arrayAdapter);
        }
    }
}
