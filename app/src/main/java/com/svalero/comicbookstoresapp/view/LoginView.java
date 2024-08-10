package com.svalero.comicbookstoresapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.LoginContract;
import com.svalero.comicbookstoresapp.presenter.LoginPresenter;
import com.svalero.comicbookstoresapp.util.OuterBaseActivity;

public class LoginView extends OuterBaseActivity implements LoginContract.View {
    private EditText etUsername;
    private EditText etPassword;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        presenter = new LoginPresenter(this);
        setupInputFields();
    }

    private void setupInputFields() {
        etUsername = findViewById(R.id.username_login);
        etPassword = findViewById(R.id.password_login);
    }

    public void login(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        presenter.login(username, password);
    }

    @Override
    public void showLoginSuccessDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    navigateToStoresMap();
                })
                .show();
    }

    @Override
    public void showLoginErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_login)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void navigateToStoresMap() {
        Intent intent = new Intent(this, StoresMapView.class);
        startActivity(intent);
        finish();
    }
}
