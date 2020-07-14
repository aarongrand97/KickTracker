package com.example.kicktracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNotesActivity extends AppCompatActivity {
    EditText m_notesInput;
    String m_existingNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        m_notesInput = findViewById(R.id.notesInput);
        m_existingNotes = getIntent().getStringExtra("notes");
        if(!m_existingNotes.isEmpty()){
            m_notesInput.setText(m_existingNotes);
        }
    }

    public void saveNotesPressed(View view){
        String notes = m_notesInput.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("notesString", notes);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void onBackPressed() {
        String inp = m_notesInput.getText().toString();
        if (!inp.equals(m_existingNotes)) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Leave without saving notes?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else{
            finish();
        }
    }


}
