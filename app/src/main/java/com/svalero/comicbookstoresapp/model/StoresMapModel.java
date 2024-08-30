package com.svalero.comicbookstoresapp.model;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.svalero.comicbookstoresapp.api.StoreApi;
import com.svalero.comicbookstoresapp.api.StoreApiInterface;
import com.svalero.comicbookstoresapp.api.UserApi;
import com.svalero.comicbookstoresapp.api.UserApiInterface;
import com.svalero.comicbookstoresapp.contract.StoresMapContract;
import com.svalero.comicbookstoresapp.domain.ApiError;
import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoresMapModel implements StoresMapContract.Model {
    private final Context context;

    public StoresMapModel(Context context) {
        this.context = context;
    }

    @Override
    public void getStores(OnGetStoresListener listener) {
        StoreApiInterface api = StoreApi.buildInstance();
        Call<List<Store>> getStoresCall = api.getStores();
        getStoresCall.enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                Log.e("getStores", response.message());
                if (response.isSuccessful() && response.body() != null) {
                    List<Store> stores = response.body();
                    listener.onGetStoresSuccess(stores);
                } else {
                    Log.e("getStores", response.message());
                    ApiError apiError = null;
                    try {
                        apiError = new Gson().fromJson(response.errorBody().string(), ApiError.class);
                    } catch (IOException e) {
                        listener.onGetStoresError("");
                        throw new RuntimeException(e);
                    }
                    listener.onGetStoresError(apiError.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                Log.e("getStores", Objects.requireNonNull(t.getMessage()));
                listener.onGetStoresError("");
            }
        });
    }

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
}
