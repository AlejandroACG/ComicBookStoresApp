package com.svalero.comicbookstoresapp.model;

import android.util.Log;
import com.google.gson.Gson;
import com.svalero.comicbookstoresapp.api.ReviewApi;
import com.svalero.comicbookstoresapp.api.ReviewApiInterface;
import com.svalero.comicbookstoresapp.api.StoreApi;
import com.svalero.comicbookstoresapp.api.StoreApiInterface;
import com.svalero.comicbookstoresapp.api.UserApi;
import com.svalero.comicbookstoresapp.api.UserApiInterface;
import com.svalero.comicbookstoresapp.contract.StoreDetailsContract;
import com.svalero.comicbookstoresapp.domain.ApiError;
import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;
import java.io.IOException;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreDetailsModel implements StoreDetailsContract.Model {
    @Override
    public void getUser(Long id, OnGetUserListener listener) {
        UserApiInterface api = UserApi.buildInstance();
        Call<User> getUserCall = api.getUser(id);
        getUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("getUser", response.message());
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    listener.onGetUserSuccess(user);
                } else {
                    Log.e("getUser", response.message());
                    ApiError apiError = null;
                    try {
                        apiError = new Gson().fromJson(response.errorBody().string(), ApiError.class);
                    } catch (IOException e) {
                        listener.onGetUserError("");
                        throw new RuntimeException(e);
                    }
                    listener.onGetUserError(apiError.getMessage());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("getUser", Objects.requireNonNull(t.getMessage()));
                listener.onGetUserError("");
            }
        });
    }

    @Override
    public void getStore(Long id, OnGetStoreListener listener) {
        StoreApiInterface api = StoreApi.buildInstance();
        Call<Store> getStoreCall = api.getStore(id);
        getStoreCall.enqueue(new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> response) {
                Log.e("getStore", response.message());
                if (response.isSuccessful() && response.body() != null) {
                    Store store = response.body();
                    listener.onGetStoreSuccess(store);
                } else {
                    Log.e("getStore", response.message());
                    ApiError apiError = null;
                    try {
                        apiError = new Gson().fromJson(response.errorBody().string(), ApiError.class);
                    } catch (IOException e) {
                        listener.onGetStoreError("");
                        throw new RuntimeException(e);
                    }
                    listener.onGetStoreError(apiError.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Store> call, Throwable t) {
                Log.e("getStore", Objects.requireNonNull(t.getMessage()));
                listener.onGetStoreError("");
            }
        });
    }

    @Override
    public void deleteReview(Long id, StoreDetailsContract.Model.OnDeleteReviewListener listener) {
        ReviewApiInterface api = ReviewApi.buildInstance();
        Call<Void> deleteReviewCall = api.deleteReview(id);
        deleteReviewCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e("deleteReview", response.message());
                if (response.isSuccessful()) {
                    listener.onDeleteReviewSuccess();
                } else {
                    Log.e("deleteReview", response.message());
                    ApiError apiError = null;
                    try {
                        apiError = new Gson().fromJson(response.errorBody().string(), ApiError.class);
                    } catch (IOException e) {
                        listener.onDeleteReviewError("");
                        throw new RuntimeException(e);
                    }
                    listener.onDeleteReviewError(apiError.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("deleteReview", Objects.requireNonNull(t.getMessage()));
                listener.onDeleteReviewError("");
            }
        });
    }
}
