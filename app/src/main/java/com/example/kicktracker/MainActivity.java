package com.example.kicktracker;

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
    }

    // Both of these linked from the xml layout file
    public void startNewSession(View view){
        Intent intent = new Intent(this, NewSessionActivity.class);
        startActivity(intent);
    }

    public void viewData(View view){
        Intent intent = new Intent(this, ViewDataActivity.class);
        startActivity(intent);
    }

}
