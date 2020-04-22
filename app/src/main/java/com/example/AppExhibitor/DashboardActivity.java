package com.example.AppExhibitor;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;


public class DashboardActivity extends AppCompatActivity  {
    private TextView resultExhibitor, resultStand, resultEvent, resultDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnScann = (Button) findViewById(R.id.btnScann);
        Button btnReport= (Button) findViewById(R.id.btnReport);

        resultExhibitor   = findViewById(R.id.tvExhibitor);
        resultStand       = findViewById(R.id.tvStand);
        resultEvent       = findViewById(R.id.tvEvent);
        resultDescription = findViewById(R.id.tvDescription);

        Bundle extras = getIntent().getExtras();
        final String exhibitor  = extras.getString("result_exhibitor");
        final String stand      = extras.getString("result_stand");
        final String event      = extras.getString("result_event");
        final String description= extras.getString("result_desc");

        resultExhibitor.setText(exhibitor);
        resultStand.setText(stand);
        resultEvent.setText(event);
        resultDescription.setText(description);

        btnScann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ScannerActivity.class);
                intent.putExtra("result_exhibitor", exhibitor);
                intent.putExtra("result_stand", stand);
                intent.putExtra("result_event", event);
                intent.putExtra("result_desc", description);
                startActivity(intent);
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Logout(MenuItem mi) {
        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}


