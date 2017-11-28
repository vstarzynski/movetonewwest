package com.example.vnstarzynski.movingtonewwest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class SportsDetailActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_detail);

        final SportsField field = (SportsField) getIntent().getExtras().get("detail");
        TextView name = (TextView) findViewById(R.id.parkName);
        name.setText(field.getParkName());

        TextView activities = (TextView) findViewById(R.id.activities);
        if (field.getActivities().equalsIgnoreCase("null")) {
            activities.setText("Sports");
        } else {
            activities.setText(field.getActivities());
        }

        Button mapBtn = (Button) findViewById(R.id.map_field);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsDetailActivity.this, MapsActivity.class);
                i.putExtra("longitude", field.getLongitude());
                i.putExtra("latitude", field.getLatitude());
                i.putExtra("name", field.getParkName());
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.favourite_field);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FavouriteActivity.favorite.contains(field.getParkName())) {
                    FavouriteActivity.favorite.add(field.getParkName());
                    String msg;
                    msg = "Added to favourite!";

                    Toast t = Toast.makeText(SportsDetailActivity.this, msg, Toast.LENGTH_LONG);
                    t.show();
                } else {
                    String msg;
                    msg = "Already exists in favourite!";

                    Toast t = Toast.makeText(SportsDetailActivity.this, msg, Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }
}
