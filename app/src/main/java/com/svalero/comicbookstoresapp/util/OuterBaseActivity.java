package com.svalero.comicbookstoresapp.util;

import static com.svalero.comicbookstoresapp.util.Constants.SHARED_PREFERENCES;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import com.svalero.comicbookstoresapp.view.MainView;

public class OuterBaseActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        long userId = prefs.getLong("USER_ID", 0);
        if (userId < 1) {
            Intent intent = new Intent(this, MainView.class);
            startActivity(intent);
            finish();
        }
    }
}
