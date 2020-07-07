package com.example.kicktracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewSessionDataActivity extends AppCompatActivity {

    gridViewSessionDisplayDataParent m_gridView;
    int [][][] m_sessionArray = new int[7][10][2];

    TextView m_displayKicksValue;
    TextView m_displaySccssValue;
    TextView m_displayPcntgValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_session_data);

        m_gridView = findViewById(R.id.grid_view);
        m_gridView.setM_parentSession(ViewSessionDataActivity.this);

        //String sessionString = getIntent().getStringExtra("sessionString");
        //m_sessionArray = m_gridView.convertSessionStringToArray(sessionString);

        m_sessionArray = (int[][][]) getIntent().getExtras().getSerializable("array");

        m_displayKicksValue = findViewById(R.id.kickDisplayValue);
        m_displaySccssValue = findViewById(R.id.sccssDisplayValue);
        m_displayPcntgValue = findViewById(R.id.percentageValue);

    }

    public void displayGridSquareStats(int row, int col){
        int numKicks = m_sessionArray[row][col][0];
        int numSccss = m_sessionArray[row][col][1];

        m_displayKicksValue.setText(Integer.toString(numKicks));
        m_displaySccssValue.setText(Integer.toString(numSccss));
        if(numKicks == 0) {
            m_displayPcntgValue.setText("-");
        }
        else{
            m_displayPcntgValue.setText(Integer.toString(100*numSccss / numKicks));
        }
    }

    public int [][][] getSessionArray(){
        return m_sessionArray;
    }
}
