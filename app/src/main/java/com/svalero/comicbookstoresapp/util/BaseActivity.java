package com.svalero.comicbookstoresapp.util;

import static com.svalero.comicbookstoresapp.util.Constants.SHARED_PREFERENCES;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected SharedPreferences prefs;
    protected SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        editor = prefs.edit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (prefs == null) {
            prefs = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        }
        if (editor == null) {
            editor = prefs.edit();
        }
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }
}
