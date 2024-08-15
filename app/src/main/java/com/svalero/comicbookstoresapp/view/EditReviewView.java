package com.svalero.comicbookstoresapp.view;

import static com.svalero.comicbookstoresapp.util.Constants.PREFERENCES_ID;
import static com.svalero.comicbookstoresapp.util.Constants.REVIEW_ID;
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
import com.svalero.comicbookstoresapp.contract.EditReviewContract;
import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.dto.ReviewDTO;
import com.svalero.comicbookstoresapp.presenter.EditReviewPresenter;
import com.svalero.comicbookstoresapp.util.InnerBaseActivity;

public class EditReviewView extends InnerBaseActivity implements EditReviewContract.View {
    private Long reviewId;
    private Long storeId;
    private Long userId;
    private EditText etTitle;
    private EditText etContent;
    private EditReviewPresenter presenter;
    private Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_review);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter = new EditReviewPresenter(this, this);
        reviewId = getIntent().getLongExtra(REVIEW_ID, 0);
        storeId = getIntent().getLongExtra(STORE_ID, 0);

        presenter.getReview(reviewId);
    }

    @Override
    public void setupReviewData(Review review) {
        this.review = review;
        setupInputFields();
    }

    @Override
    public void showGetReviewErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_review_get)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private void setupInputFields() {
        userId = prefs.getLong(PREFERENCES_ID, 0);
        etContent = findViewById(R.id.edit_review_content);
        etTitle = findViewById(R.id.edit_review_title);

        etTitle.setText(review.getTitle());
        etContent.setText(review.getContent());
    }

    public void editReview(View view) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.edit_dialog))
                .setMessage(R.string.submit_changes)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    ReviewDTO reviewDTO = new ReviewDTO(userId, storeId, etTitle.getText().toString(), etContent.getText().toString());

                    presenter.updateReview(reviewId, reviewDTO);
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    @Override
    public void showUpdateReviewSuccessDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    navigateToStoreDetails();
                })
                .show();
    }

    @Override
    public void showUpdateReviewErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_review_update)
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
