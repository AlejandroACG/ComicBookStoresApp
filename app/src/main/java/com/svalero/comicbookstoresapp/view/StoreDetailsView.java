package com.svalero.comicbookstoresapp.view;

import static com.svalero.comicbookstoresapp.util.Constants.PREFERENCES_ID;
import static com.svalero.comicbookstoresapp.util.Constants.STORE_ID;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.adapter.ReviewAdapter;
import com.svalero.comicbookstoresapp.contract.StoreDetailsContract;
import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.presenter.StoreDetailsPresenter;
import com.svalero.comicbookstoresapp.util.InnerBaseActivity;
import java.util.ArrayList;
import java.util.List;

public class StoreDetailsView extends InnerBaseActivity implements StoreDetailsContract.View {
    private ReviewAdapter adapter;
    private StoreDetailsPresenter presenter;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvPhone;
    private TextView tvWebsite;
    private TextView tvEmail;
    private List<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_store_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        setUpFields();
        reviews = new ArrayList<>();
        presenter = new StoreDetailsPresenter(this);

        presenter.getStore(getIntent().getLongExtra(STORE_ID, 0));
    }

    private void setUpFields() {
        tvName = findViewById(R.id.store_name);
        tvAddress = findViewById(R.id.store_address);
        tvPhone = findViewById(R.id.store_phone);
        tvWebsite = findViewById(R.id.store_website);
        tvEmail = findViewById(R.id.store_email);
    }

    @Override
    public void showLoadReviewsError(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_loading_reviews)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    @Override
    public void setupStore(Store store) {
        reviews.clear();
        reviews.addAll(store.getStoreReviews());

        tvName.setText(store.getName());
        tvAddress.setText(store.getAddress());
        tvPhone.setText(store.getPhone());
        tvWebsite.setText(store.getWebsite());
        tvEmail.setText(store.getEmail());

        presenter.getUser(prefs.getLong(PREFERENCES_ID, 0));
    }

    @Override
    public void showGetStoreError(String message) {
        Intent intent = new Intent(StoreDetailsView.this, StoresMapView.class);
        startActivity(intent);
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_loading_store)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
        finish();
    }

    @Override
    public void loadUser(User user) {
        RecyclerView recyclerView = findViewById(R.id.review_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ReviewAdapter(reviews, user);
        recyclerView.setAdapter(adapter);
        if (reviews != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showGetUserError(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_loading_reviews)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    public void addReview(View view) {
        Intent intent = new Intent(this, AddReviewView.class);
        startActivity(intent);
    }
}
