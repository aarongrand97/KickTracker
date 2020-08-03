package com.example.kicktracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Locale;

public class ViewDataActivity extends AppCompatActivity {
    ListView m_sessionListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_view_data);

        m_sessionListView = findViewById(R.id.sessionListView);

        populateListView();
        setListListener();
    }

    // Puts all the session names in to the ListView
    private void populateListView(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(ViewDataActivity.this);
        List<String> returnList = dataBaseHelper.getSessionNames();
        //ArrayAdapter customArrayAdaptor = new ArrayAdapter<>(ViewDataActivity.this, android.R.layout.simple_list_item_1, returnList);
        ArrayAdapter customArrayAdaptor = new ArrayAdapter<>(ViewDataActivity.this, R.layout.custom_list_view_format, R.id.customListTextView, returnList);
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
                bundle.putString("sessionName", sessionName);
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

    private void loadLocale(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        String lang = sharedPreferences.getString("languagePreference", "");

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
