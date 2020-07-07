package com.example.kicktracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewKickActivity extends AppCompatActivity {

    private TextView kicksView, sccssView;
    private int numKicks, numSccss;
    private gridViewKickInput m_gridView;
    private Button m_storeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_kick);

        kicksView = findViewById(R.id.kicks_cntr);
        sccssView = findViewById(R.id.sccss_cntr);

        m_gridView = findViewById(R.id.grid_view);
        m_gridView.setM_parentSession(this);

        m_storeButton = findViewById(R.id.storeButton);
        m_storeButton.setEnabled(false); // disable until user has selected a location

        numKicks = 1; // possibly want to find a way to set this from a setting
        numSccss = 0;

        updateKicks();
        updateSccss();
    }

    // Send [row,col,kick,sccss] back to the session activity
    public void storeKick(View view){
         Intent resultIntent = new Intent();

         int selectedRow = m_gridView.getSelectedRow();
         int selectedCol = m_gridView.getSelectedCol();

         int[] result  = {selectedRow,selectedCol,numKicks,numSccss};
         resultIntent.putExtra("kick", result);

         setResult(RESULT_OK, resultIntent);
         finish();
    }

    private void updateKicks(){
        kicksView.setText(Integer.toString(numKicks));
    }
    private void updateSccss(){
        sccssView.setText(Integer.toString(numSccss));
    }

    public void incKick(View view){
            numKicks++;
            updateKicks();
    }
    public void decKick(View view){
        if(numKicks>1) {
            numKicks--;
            updateKicks();
            if(numSccss>numKicks)
                decSccss(view);
        }
    }
    public void incSccss(View view){
        if(numSccss<numKicks) {
            numSccss++;
            updateSccss();
        }
    }
    public void decSccss(View view){
        if(numSccss>=1) {
            numSccss--;
            updateSccss();
        }
    }

    // Called from custom gridViewKickInput
    public void enableStore(){
        m_storeButton.setEnabled(true);
    }
}
