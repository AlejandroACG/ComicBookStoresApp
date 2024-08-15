package com.svalero.comicbookstoresapp.util;

import static com.svalero.comicbookstoresapp.util.Constants.PREFERENCES_ID;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.view.EditUserView;
import com.svalero.comicbookstoresapp.view.MainView;
import com.svalero.comicbookstoresapp.view.StoresMapView;

public class InnerBaseActivity extends BaseActivity {
    @Override
    protected void onStart() {
        super.onStart();

        long userId = prefs.getLong(PREFERENCES_ID, 0);

        if (userId < 1) {
            Intent intent = new Intent(this, MainView.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_map) {
            Intent intent = new Intent(this, StoresMapView.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            editor.remove(PREFERENCES_ID);
            editor.apply();
            Intent intent = new Intent(this, MainView.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.action_profile) {
            Intent intent = new Intent(this, EditUserView.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
