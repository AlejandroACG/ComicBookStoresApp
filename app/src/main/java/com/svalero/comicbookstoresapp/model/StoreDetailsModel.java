package com.svalero.comicbookstoresapp.model;

import android.util.Log;

import com.svalero.comicbookstoresapp.api.ReviewApi;
import com.svalero.comicbookstoresapp.api.ReviewApiInterface;
import com.svalero.comicbookstoresapp.contract.StoreDetailsContract;
import com.svalero.comicbookstoresapp.domain.Review;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreDetailsModel implements StoreDetailsContract.Model {
    @Override
    public void loadAllReviews(OnLoadReviewsListener listener) {
        ReviewApiInterface api = ReviewApi.buildInstance();
        Call<List<Review>> getReviewsCall = api.getReviews();
        getReviewsCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                Log.e("getReviews", response.message());
                if (response.isSuccessful() && response.body() != null) {
                    List<Review> reviews = response.body();
                    listener.onLoadReviewsSuccess(reviews);
                } else {
                    listener.onLoadReviewsError("Failed to load reviews");
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.e("getReviews", Objects.requireNonNull(t.getMessage()));
                listener.onLoadReviewsError(t.getMessage());
            }
        });
    }
}
