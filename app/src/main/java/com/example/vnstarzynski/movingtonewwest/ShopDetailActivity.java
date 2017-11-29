package com.example.vnstarzynski.movingtonewwest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShopDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);


        getSupportActionBar().setTitle("Shop");
        final Shop shop = (Shop) getIntent().getExtras().get("detail");
        TextView name = (TextView) findViewById(R.id.buildingName);
        name.setText(shop.getBuildingName());

        TextView address = (TextView) findViewById(R.id.address_shop);
        address.setText(shop.getAddress());

        Button mapBtn = (Button) findViewById(R.id.map_shop);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShopDetailActivity.this, MapsActivity.class);
                i.putExtra("longitude", shop.getLongitude());
                i.putExtra("latitude", shop.getLatitude());
                i.putExtra("name", shop.getBuildingName());
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.favourite_shop);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FavouriteActivity.favorite.contains(shop.getBuildingName())) {
                    FavouriteActivity.favorite.add(shop.getBuildingName());
                    String msg;
                    msg = "Added to favourite!";

                    Toast t = Toast.makeText(ShopDetailActivity.this, msg, Toast.LENGTH_LONG);
                    t.show();
                } else {
                    String msg;
                    msg = "Already exists in favourite!";

                    Toast t = Toast.makeText(ShopDetailActivity.this, msg, Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }
}
