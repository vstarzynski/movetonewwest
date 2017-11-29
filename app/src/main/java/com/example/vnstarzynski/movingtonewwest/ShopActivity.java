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

public class ShopActivity extends AppCompatActivity implements Serializable{
    private List<Shop> shopList;
    private List<String> name;
    private ProgressDialog pDialog;
    private String service_url = "http://opendata.newwestcity.ca/downloads/major-shopping/MAJOR_SHOPPING.json";
    private ListView lv;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getSupportActionBar().setTitle("Shop");

        lv = (ListView) findViewById(R.id.shopList);
        shopList = new ArrayList<>();
        name = new ArrayList<>();

        getSupportActionBar().setHomeButtonEnabled(true);

        new ShopAsyncTask().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String shopName =  ((TextView)view).getText().toString();
                Shop shopDetail = null;
                for (int j = 0; j < shopList.size(); j++) {
                    if(shopList.get(j).getBuildingName().equalsIgnoreCase(shopName)) {
                        shopDetail = shopList.get(j);
                        break;
                    }
                }
                if(i >= 0) {
                    Intent intent = new Intent(ShopActivity.this, ShopDetailActivity.class);
                    intent.putExtra("detail", shopDetail);
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class ShopAsyncTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "EventActivity";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ShopActivity.this);
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
                        Shop shop = new Shop();
                        name.add(o.getString("BLDGNAM"));
                        shop.setBuildingName(o.getString("BLDGNAM"));
                        shop.setAddress(o.getString("STRNUM") + " " + o.getString("STRNAM"));
                        shop.setLongitude(Double.parseDouble(o.getString("X")));
                        shop.setLatitude(Double.parseDouble(o.getString("Y")));
                        shopList.add(shop);
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
                    ShopActivity.this, android.R.layout.simple_list_item_1, name
            );
            lv.setAdapter(arrayAdapter);
        }
    }
}
