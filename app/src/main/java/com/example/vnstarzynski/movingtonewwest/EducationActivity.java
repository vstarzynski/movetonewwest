package com.example.vnstarzynski.movingtonewwest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        ListView listView = (ListView) findViewById(R.id.education_list);
        educationList = new ArrayList<>();
        name = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getEducations();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, name
        );

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("education.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void getEducations() {
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());

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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
