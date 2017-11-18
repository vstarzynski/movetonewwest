package com.example.vnstarzynski.movingtonewwest;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EducationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_detail);

        final Education education = (Education) getIntent().getExtras().get("detail");
        TextView name = (TextView) findViewById(R.id.educationName);
        name.setText(education.getName());

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(education.getDescription());

        TextView category = (TextView) findViewById(R.id.category);
        if (education.getCategory().isEmpty()) {
            category.setText("N/A");
        } else {
            category.setText(education.getCategory());
        }

        TextView hours = (TextView) findViewById(R.id.hours);
        if (education.getHours().isEmpty()) {
            hours.setText("N/A");
        } else {
            hours.setText(education.getHours());
        }

        TextView location = (TextView) findViewById(R.id.location);
        if (education.getLocation().isEmpty()) {
            location.setText("N/A");
        } else {
            location.setText(education.getLocation());
        }

        TextView postalCode = (TextView) findViewById(R.id.postalCode);
        if (education.getPostalCode().isEmpty()) {
            postalCode.setText("N/A");
        } else {
            postalCode.setText(education.getPostalCode());
        }

        TextView phone = (TextView) findViewById(R.id.phone);
        if (education.getPhoneNumber().isEmpty()) {
            phone.setText("N/A");
        } else {
            phone.setText(education.getPhoneNumber());
        }

        TextView email = (TextView) findViewById(R.id.email);
        if (education.getEmail().isEmpty()) {
            email.setText("N/A");
        } else {
            email.setText(education.getEmail());
        }

        TextView website = (TextView) findViewById(R.id.website);
        if (education.getWebsite().isEmpty()) {
            website.setText("N/A");
        } else {
            website.setText(education.getWebsite());
            website.setMovementMethod(LinkMovementMethod.getInstance());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.favourite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FavouriteActivity.favorite.contains(education.getName())) {
                    FavouriteActivity.favorite.add(education.getName());
                    String msg;
                    msg = "Added to favourite!";

                    Toast t = Toast.makeText(EducationDetailActivity.this, msg, Toast.LENGTH_LONG);
                    t.show();
                } else {
                    String msg;
                    msg = "Already exists in favourite!";

                    Toast t = Toast.makeText(EducationDetailActivity.this, msg, Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }
}
