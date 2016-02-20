package com.example.russell_test.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LobbyActivity extends AppCompatActivity {

    Intent intent;
    Intent createEventIntent;

    Button getIntent;
    TextView text;
    Button createEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        createEvent = (Button) findViewById(R.id.createEvent);
        createEventIntent = new Intent(this, EventCreationActivity.class);

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(createEventIntent);
            }
        });

        //text = (TextView) findViewById(R.id.);

        //getIntent = (Button) findViewById(R.id.intentButton);
        //getIntent.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        intent = getIntent();
        //        String message = intent.getStringExtra("com.example.russell_test.myapplication.MESSAGE");
        //        text.setText(message);
        //    }
        //});
    }
}
