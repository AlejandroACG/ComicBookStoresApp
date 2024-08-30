package com.svalero.comicbookstoresapp.model;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.svalero.comicbookstoresapp.api.ReviewApi;
import com.svalero.comicbookstoresapp.api.ReviewApiInterface;
import com.svalero.comicbookstoresapp.contract.EditReviewContract;
import com.svalero.comicbookstoresapp.domain.ApiError;
import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.dto.ReviewDTO;
import java.io.IOException;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditReviewModel implements EditReviewContract.Model {
    private final FusedLocationProviderClient fusedLocationClient;
    private final Context context;

    public EditReviewModel(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        this.context = context;
    }

    @Override
    public void getReview(Long id, OnGetReviewListener listener) {
        ReviewApiInterface api = ReviewApi.buildInstance();
        Call<Review> getReviewCall = api.getReview(id);
        getReviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Log.e("getReview", response.message());
                if (response.isSuccessful() && response.body() != null) {
                    Review review = response.body();
                    listener.onGetReviewSuccess(review);
                } else {
                    Log.e("getReview", response.message());
                    ApiError apiError = null;
                    try {
                        apiError = new Gson().fromJson(response.errorBody().string(), ApiError.class);
                    } catch (IOException e) {
                        listener.onGetReviewError("");
                        throw new RuntimeException(e);
                    }
                    listener.onGetReviewError(apiError.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.e("getReview", Objects.requireNonNull(t.getMessage()));
                listener.onGetReviewError("");
            }
        });
    }

    @Override
    public void updateReview(Long id, ReviewDTO reviewDTO, OnUpdateReviewListener listener) {
        ReviewApiInterface api = ReviewApi.buildInstance();
        Call<Review> updateReviewCall = api.updateReview(id, reviewDTO);
        updateReviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Log.e("updateReview", response.message());
                if (response.isSuccessful() && response.body() != null) {
                    Review review = response.body();
                    listener.onUpdateReviewSuccess(review);
                } else {
                    Log.e("updateReview", response.message());
                    ApiError apiError = null;
                    try {
                        apiError = new Gson().fromJson(response.errorBody().string(), ApiError.class);
                    } catch (IOException e) {
                        listener.onUpdateReviewError("");
                        throw new RuntimeException(e);
                    }
                    listener.onUpdateReviewError(apiError.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.e("updateReview", Objects.requireNonNull(t.getMessage()));
                listener.onUpdateReviewError("");
            }
        });
    }
}
