package com.example.vnstarzynski.movingtonewwest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {
    public static ArrayList<String> favorite = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        ListView lv = (ListView) findViewById(R.id.favourite_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, favorite
        );

        lv.setAdapter(arrayAdapter);
    }
}
