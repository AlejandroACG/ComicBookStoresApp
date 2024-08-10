package com.svalero.comicbookstoresapp.util;

import static com.svalero.comicbookstoresapp.util.Constants.SHARED_PREFERENCES;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected SharedPreferences prefs;
    protected SharedPreferences.Editor editor;

    @Override
    protected void onStart() {
        super.onStart();

        prefs = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        editor = prefs.edit();
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }
}
