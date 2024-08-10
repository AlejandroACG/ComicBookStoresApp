package com.svalero.comicbookstoresapp.util;

import android.content.Intent;
import com.svalero.comicbookstoresapp.view.StoresMapView;

public class OuterBaseActivity extends BaseActivity {
    @Override
    protected void onStart() {
        super.onStart();

        long userId = prefs.getLong("USER_ID", 0);

        if (userId > 0) {
            Intent intent = new Intent(this, StoresMapView.class);
            startActivity(intent);
            finish();
        }
    }
}
