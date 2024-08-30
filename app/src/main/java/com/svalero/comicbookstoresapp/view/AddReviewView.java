package com.svalero.comicbookstoresapp.view;

import static com.svalero.comicbookstoresapp.util.Constants.PREFERENCES_ID;
import static com.svalero.comicbookstoresapp.util.Constants.STORE_ID;
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
import com.svalero.comicbookstoresapp.contract.AddReviewContract;
import com.svalero.comicbookstoresapp.presenter.AddReviewPresenter;
import com.svalero.comicbookstoresapp.util.InnerBaseActivity;

public class AddReviewView extends InnerBaseActivity implements AddReviewContract.View {
    private Long storeId;
    private EditText etTitle;
    private EditText etContent;
    private AddReviewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_review);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        storeId = getIntent().getLongExtra(STORE_ID, 0);
        setupInputFields();

        presenter = new AddReviewPresenter(this, this);
    }

    private void setupInputFields() {
        etTitle = findViewById(R.id.create_review_title);
        etContent = findViewById(R.id.create_review_content);
    }

    public void postReview(View view) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        presenter.saveReview(prefs.getLong(PREFERENCES_ID, 0), storeId, title, content);
    }

    @Override
    public void showSaveReviewSuccessDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    navigateToStoreDetails();
                })
                .show();
    }

    @Override
    public void showSaveReviewErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_post_review)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    public void navigateToStoreDetails() {
        Intent intent = new Intent(this, StoreDetailsView.class);
        intent.putExtra(STORE_ID, storeId);
        startActivity(intent);
        finish();
    }
}
