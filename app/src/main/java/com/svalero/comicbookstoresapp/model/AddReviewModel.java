package com.svalero.comicbookstoresapp.model;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.svalero.comicbookstoresapp.api.ReviewApi;
import com.svalero.comicbookstoresapp.api.ReviewApiInterface;
import com.svalero.comicbookstoresapp.contract.AddReviewContract;
import com.svalero.comicbookstoresapp.domain.ApiError;
import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.dto.ReviewDTO;
import java.io.IOException;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReviewModel implements AddReviewContract.Model {
    private final Context context;

    public AddReviewModel(Context context) {
        this.context = context;
    }

    @Override
    public void saveReview(ReviewDTO reviewDTO, OnSaveReviewListener listener) {
        ReviewApiInterface api = ReviewApi.buildInstance();
        Call<Review> saveReviewCall = api.createReview(reviewDTO);
        saveReviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Log.e("saveReview", response.message());
                if (response.isSuccessful() && response.body() != null) {
                    Review review = response.body();
                    listener.onSaveReviewSuccess(review);
                } else {
                    Log.e("saveReview", response.message());
                    ApiError apiError = null;
                    try {
                        apiError = new Gson().fromJson(response.errorBody().string(), ApiError.class);
                    } catch (IOException e) {
                        listener.onSaveReviewError("");
                        throw new RuntimeException(e);
                    }
                    listener.onSaveReviewError(apiError.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.e("saveReview", Objects.requireNonNull(t.getMessage()));
                listener.onSaveReviewError("");
            }
        });
    }
}
