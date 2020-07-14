package com.example.kicktracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class    NewSessionActivity extends AppCompatActivity {
    int[][][] m_sessionArray = new int[7][10][2]; // first layer is attempts, second is successes

    TextView m_displayKicksValue, m_displaySccssValue, m_displayPrcntgValue, m_dateTime;
    View m_backGroundView;

    int m_totalKicks, m_totalSccss;
    String m_notes;

    gridViewSessionDisplay m_gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_session);

        m_gridView = findViewById(R.id.grid_view); // custom object on top of pitch image
        m_gridView.setM_parentSession(this);

        m_displayKicksValue = findViewById(R.id.kickDisplayValue);
        m_displaySccssValue = findViewById(R.id.sccssDisplayValue);
        m_displayPrcntgValue = findViewById(R.id.prctgeDisplayValue);

        m_backGroundView = findViewById(R.id.backGroundView);
        setUpBackgroundListener();

        m_dateTime = findViewById(R.id.dataTimeTextView);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        m_dateTime.setText(currentDateTimeString);

        m_notes = "";

        m_totalKicks = 0;
        m_totalSccss = 0;
        displaySessionStats();
    }

    public void newKick(View view){
        Intent intent = new Intent(this, NewKickActivity.class);
        Button newKickButton = findViewById(R.id.new_kick);
        startActivityForResult(intent, 1);
    }

    @Override
    // NewKickActivity sends back an array of [row, col, attempts, successes]
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){ // NEW KICK
            if(resultCode == RESULT_OK){
                int[] result = data.getIntArrayExtra("kick");
                int row = result[0];
                int col = result[1];
                int kicks = result[2];
                int sccss = result[3];

                // Add new data to the session array
                m_sessionArray[row][col][0] += kicks;
                m_sessionArray[row][col][1] += sccss;

                m_gridView.setSelectedCell(-1,-1); // remove any selection

                // update session stats
                m_totalKicks += kicks;
                m_totalSccss += sccss;
                displaySessionStats();
            }
        }
        else if(requestCode == 2){ //ADD NOTES


            if(resultCode == RESULT_OK) {
                String notes = data.getStringExtra("notesString");
                m_notes = notes;
            }
        }
    }

    // Called from the custom gridView, shows user details of a grid square
    public void displayGridSquareStats(int row, int col){
        int numKicks = m_sessionArray[row][col][0];
        int numSccss = m_sessionArray[row][col][1];

       m_displayKicksValue.setText(Integer.toString(numKicks));
       m_displaySccssValue.setText(Integer.toString(numSccss));
       if(numKicks == 0){
           m_displayPrcntgValue.setText("-");
       }
       else {
           m_displayPrcntgValue.setText(Integer.toString(numSccss * 100 / numKicks));
       }

    }

    private void displaySessionStats(){
        m_displayKicksValue.setText(Integer.toString(m_totalKicks));
        m_displaySccssValue.setText(Integer.toString(m_totalSccss));
        if(m_totalKicks == 0){
            m_displayPrcntgValue.setText("-");
        }
        else{
            m_displayPrcntgValue.setText(Integer.toString(m_totalSccss * 100 / m_totalKicks));
        }
    }

    // called from link in XML file, saves session to database
    public void saveSession(View view){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(NewSessionActivity.this);
        boolean success = dataBaseHelper.addOne(m_dateTime.getText().toString(), sessionArrayToString(), m_notes);
        Toast.makeText(NewSessionActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();
        finish();

    }

    public void addNotes(View view){
        Intent intent = new Intent(this, AddNotesActivity.class);
        intent.putExtra("notes", m_notes);
        startActivityForResult(intent, 2);

    }

    // Coverts the array to a string to store in database in form (0,0,0),(0,0,1),(0,1,0),(0,1,1)...
    private String sessionArrayToString(){
        String arrayString = "";
        for(int row = 0; row != 7; row++){
            for(int col = 0; col != 10; col++) {
                arrayString = arrayString + m_sessionArray[row][col][0];
                arrayString = arrayString + m_sessionArray[row][col][1];
            }
        }
        return arrayString;
    }

    private void setUpBackgroundListener(){
        m_backGroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_gridView.reset();
                displaySessionStats();
            }
        });
    }

    public int[][][] getSessionArray(){
        return m_sessionArray;
    }
}
