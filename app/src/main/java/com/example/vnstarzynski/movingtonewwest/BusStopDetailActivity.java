package com.example.vnstarzynski.movingtonewwest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BusStopDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_detail);

        final BusStop stop = (BusStop) getIntent().getExtras().get("detail");
        TextView stopNum = (TextView) findViewById(R.id.busStopNumber);
        stopNum.setText(Integer.toString(stop.getStopNum()));

        TextView onStreet = (TextView) findViewById(R.id.onStreet);
        onStreet.setText(stop.getOnStreet());


        TextView atStreet = (TextView) findViewById(R.id.atStreet);
        atStreet.setText(stop.getAtStreet());


        TextView direction = (TextView) findViewById(R.id.direction);
        direction.setText(stop.getDirection());


        TextView position = (TextView) findViewById(R.id.position);
        position.setText(stop.getPosition());


        TextView status = (TextView) findViewById(R.id.status);
        status.setText(stop.getStatus());

        Button mapBtn = (Button) findViewById(R.id.map_stop);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusStopDetailActivity.this, MapsActivity.class);
                i.putExtra("longitude", stop.getLongitude());
                i.putExtra("latitude", stop.getLatitude());
                i.putExtra("name", Integer.toString(stop.getStopNum()));
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.favourite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FavouriteActivity.favorite.contains(Integer.toString(stop.getStopNum()))) {
                    FavouriteActivity.favorite.add(Integer.toString(stop.getStopNum()));
                    String msg;
                    msg = "Added to favourite!";

                    Toast t = Toast.makeText(BusStopDetailActivity.this, msg, Toast.LENGTH_LONG);
                    t.show();
                } else {
                    String msg;
                    msg = "Already exists in favourite!";

                    Toast t = Toast.makeText(BusStopDetailActivity.this, msg, Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }
}
