package com.svalero.comicbookstoresapp.util;

import android.content.Intent;
import com.svalero.comicbookstoresapp.view.MainView;
import com.svalero.comicbookstoresapp.view.StoresMapView;

public class HybridBaseActivity extends BaseActivity {
    protected Integer mode = 0;

    @Override
    protected void onStart() {
        super.onStart();

        long userId = prefs.getLong("USER_ID", 0);

        if (userId > 0 && mode == 0) {
            redirectToActivity(StoresMapView.class);
        } else if (userId < 1 && mode == 1) {
            redirectToActivity(MainView.class);
        }
    }

    private void redirectToActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }
}
