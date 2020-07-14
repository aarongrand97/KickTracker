package com.example.kicktracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ViewDataActivity extends AppCompatActivity {
    ListView m_sessionListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        m_sessionListView = findViewById(R.id.sessionListView);

        populateListView();
        setListListener();
    }

    // Puts all the session names in to the ListView
    private void populateListView(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(ViewDataActivity.this);
        List<String> returnList = dataBaseHelper.getSessionNames();
        ArrayAdapter customArrayAdaptor = new ArrayAdapter<>(ViewDataActivity.this, android.R.layout.simple_list_item_1, returnList);
        m_sessionListView.setAdapter(customArrayAdaptor);


    }

    // Waits for click, gets name of session clicked and retrieves the session array to send to viewSessionDataActivity
    private void setListListener(){
        m_sessionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String sessionName = (String) adapterView.getItemAtPosition(i);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(ViewDataActivity.this);
                int[][][] sessionArray = dataBaseHelper.getSessionArray(sessionName);
                String sessionNotes = dataBaseHelper.getSessionNotes(sessionName);
                // Want the intent that takes the user to the view session action
                Intent intent = new Intent(ViewDataActivity.this, ViewSessionDataActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("array", sessionArray);
                bundle.putString("notes", sessionNotes);
                intent.putExtras(bundle);
                // Should get the array here then pass it to the viewSessionDataActivity or maybe just the string??
                startActivity(intent);
            }
        });
    }
    //
    public void overallStatsPressed(View view){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(ViewDataActivity.this);
        int[][][] overallArray = dataBaseHelper.getOverallStats(); // collate all the arrays into one

        Intent intent = new Intent(ViewDataActivity.this, ViewSessionDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("array", overallArray);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
