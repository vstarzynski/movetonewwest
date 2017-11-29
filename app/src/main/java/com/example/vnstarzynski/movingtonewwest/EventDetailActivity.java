package com.example.vnstarzynski.movingtonewwest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class EventDetailActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        final Event event = (Event) getIntent().getExtras().get("detail");
        TextView name = (TextView) findViewById(R.id.eventName);
        name.setText(event.getName());

        getSupportActionBar().setTitle("Events");

        TextView description = (TextView) findViewById(R.id.description_event);
        description.setText(event.getDescription());

        TextView location = (TextView) findViewById(R.id.address_event);
        if (event.getLocation().isEmpty()) {
            location.setText("N/A");
        } else {
            location.setText(event.getLocation());
        }

        TextView postalCode = (TextView) findViewById(R.id.postalCode_event);
        if (event.getPostalCode().isEmpty()) {
            postalCode.setText("N/A");
        } else {
            postalCode.setText(event.getPostalCode());
        }

        TextView phone = (TextView) findViewById(R.id.phone_event);
        if (event.getPhoneNumber().isEmpty()) {
            phone.setText("N/A");
        } else {
            phone.setText(event.getPhoneNumber());
        }

        TextView email = (TextView) findViewById(R.id.email_event);
        if (event.getEmail().isEmpty()) {
            email.setText("N/A");
        } else {
            email.setText(event.getEmail());
        }

        TextView website = (TextView) findViewById(R.id.website_event);
        if (event.getWebsite().isEmpty()) {
            website.setText("N/A");
        } else {
            website.setText(event.getWebsite());
            website.setMovementMethod(LinkMovementMethod.getInstance());
        }

        TextView summary = (TextView) findViewById(R.id.summary_event);
        if (event.getSummary().isEmpty()) {
            summary.setText("N/A");
        } else {
            summary.setText(event.getSummary());
        }

        Button mapBtn = (Button) findViewById(R.id.map_event);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventDetailActivity.this, MapsActivity.class);
                i.putExtra("longitude", event.getLongitude());
                i.putExtra("latitude", event.getLatitude());
                i.putExtra("name", event.getName());
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.favourite_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FavouriteActivity.favorite.contains(event.getName())) {
                    FavouriteActivity.favorite.add(event.getName());
                    String msg;
                    msg = "Added to favourite!";

                    Toast t = Toast.makeText(EventDetailActivity.this, msg, Toast.LENGTH_LONG);
                    t.show();
                } else {
                    String msg;
                    msg = "Already exists in favourite!";

                    Toast t = Toast.makeText(EventDetailActivity.this, msg, Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }
}
