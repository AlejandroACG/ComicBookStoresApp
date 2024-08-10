package com.svalero.comicbookstoresapp.util;

import android.content.Intent;
import com.svalero.comicbookstoresapp.view.MainView;

public class InnerBaseActivity extends BaseActivity {
    @Override
    protected void onStart() {
        super.onStart();

        long userId = prefs.getLong("USER_ID", 0);

        if (userId < 1) {
            Intent intent = new Intent(this, MainView.class);
            startActivity(intent);
            finish();
        }
    }
}
