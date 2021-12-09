package com.example.lab10_idnp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonLM = (Button) findViewById(R.id.buttonLM);
        Button buttonFLPC = (Button) findViewById(R.id.buttonFLPC);
    }

    public void activityLM(View view){
        Intent lm = new Intent(this, ActivityLM.class);
        startActivity(lm);
    }

    public void activityFLPC(View view){
        Intent fl = new Intent(this, ActivityFLPC.class);
        startActivity(fl);
    }
}