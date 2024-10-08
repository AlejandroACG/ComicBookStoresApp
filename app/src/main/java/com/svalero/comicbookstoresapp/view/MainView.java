package com.svalero.comicbookstoresapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.util.OuterBaseActivity;

public class MainView extends OuterBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void navigateToRegister(View view) {
        Intent intent = new Intent(this, RegisterView.class);
        startActivity(intent);
    }

    public void navigateToLogin(View view) {
        Intent intent = new Intent(this, LoginView.class);
        startActivity(intent);
    }
}