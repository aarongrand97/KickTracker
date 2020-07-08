package com.example.kicktracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewSessionDataActivity extends AppCompatActivity {

    gridViewSessionDisplayDataParent m_gridView;
    int [][][] m_sessionArray = new int[7][10][2];

    TextView m_displayKicksValue;
    TextView m_displaySccssValue;
    TextView m_displayPcntgValue;
    ConstraintLayout m_backGroundLayout;

    int m_totalKicks, m_totalSccss, m_totalPrcntg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_session_data);

        m_gridView = findViewById(R.id.grid_view);
        m_gridView.setM_parentSession(ViewSessionDataActivity.this);

        m_sessionArray = (int[][][]) getIntent().getExtras().getSerializable("array");

        m_displayKicksValue = findViewById(R.id.kickDisplayValue);
        m_displaySccssValue = findViewById(R.id.sccssDisplayValue);
        m_displayPcntgValue = findViewById(R.id.prctgeDisplayValue);

        m_backGroundLayout  = findViewById(R.id.backgroundView);
        setBackGroundListener();

        calculateSessionTotals();
        showOverallSessionStats();

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

    private void showOverallSessionStats(){
         m_displayKicksValue.setText(Integer.toString(m_totalKicks));
         m_displaySccssValue.setText(Integer.toString(m_totalSccss));
         m_displayPcntgValue.setText(Integer.toString(m_totalPrcntg));
    }

    private void setBackGroundListener(){
        m_backGroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_gridView.reset(); // removes the selection of cell
                showOverallSessionStats();
            }
        });
    }

    private void calculateSessionTotals(){
        for(int row = 0; row != 7; row++){
            for(int col = 0; col != 10; col++){
                m_totalKicks += m_sessionArray[row][col][0];
                m_totalSccss += m_sessionArray[row][col][1];
            }
        }
        m_totalPrcntg = m_totalSccss*100/m_totalKicks;
    }

    public int [][][] getSessionArray(){
        return m_sessionArray;
    }
}
