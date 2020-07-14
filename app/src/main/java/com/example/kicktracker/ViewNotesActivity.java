package com.example.kicktracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewNotesActivity extends AppCompatActivity {
    TextView NotesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        NotesView = findViewById(R.id.NotesView);
        String notes = getIntent().getStringExtra("notes");
        NotesView.setText(notes);
    }
}
