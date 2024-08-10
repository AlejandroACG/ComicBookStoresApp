package com.svalero.comicbookstoresapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.adapter.ReviewAdapter;
import com.svalero.comicbookstoresapp.contract.StoreDetailsContract;
import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.presenter.StoreDetailsPresenter;
import com.svalero.comicbookstoresapp.util.InnerBaseActivity;
import java.util.ArrayList;
import java.util.List;

public class StoreDetailsView extends InnerBaseActivity implements StoreDetailsContract.View {
    private List<Review> reviews;
    private ReviewAdapter adapter;
    private StoreDetailsPresenter presenter;

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

        presenter = new StoreDetailsPresenter(this);
        reviews = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.review_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReviewAdapter(reviews);
        recyclerView.setAdapter(adapter);
        presenter.loadAllReviews();
    }

    public void addReview(View view) {
        Intent intent = new Intent(this, AddEditReviewView.class);
        startActivity(intent);
    }

    @Override
    public void listReviews(List<Review> reviews) {
        this.reviews.clear();
        this.reviews.addAll(reviews);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}