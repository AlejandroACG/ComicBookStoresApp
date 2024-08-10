package com.svalero.comicbookstoresapp.util;

import static com.svalero.comicbookstoresapp.util.Constants.SHARED_PREFERENCES;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import com.svalero.comicbookstoresapp.view.StoresMapView;

public class OuterBaseActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        long userId = prefs.getLong("USER_ID", 0);
        if (userId > 0) {
            Intent intent = new Intent(this, StoresMapView.class);
            startActivity(intent);
            finish();
        }
    }
}
