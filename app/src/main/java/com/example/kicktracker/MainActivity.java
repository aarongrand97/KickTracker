package com.example.kicktracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocale();
        setContentView(R.layout.activity_main);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                // Implementation
                if(key.equals("languagePreference")){
                    recreate();
                }
            }
        };
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(listener);

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

    public void settingsClicked(View view){
        Intent intent = new Intent(this, MySettingsActivity.class);
        startActivity(intent);
    }


    private void loadLocale(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        String lang = sharedPreferences.getString("languagePreference", "");

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }


}
