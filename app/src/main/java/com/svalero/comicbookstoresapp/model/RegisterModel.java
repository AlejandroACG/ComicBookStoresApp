package com.svalero.comicbookstoresapp.model;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.svalero.comicbookstoresapp.api.UserApi;
import com.svalero.comicbookstoresapp.api.UserApiInterface;
import com.svalero.comicbookstoresapp.contract.RegisterContract;
import com.svalero.comicbookstoresapp.domain.ApiError;
import com.svalero.comicbookstoresapp.domain.User;
import java.io.IOException;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterModel implements RegisterContract.Model {
    private FusedLocationProviderClient fusedLocationClient;

    public RegisterModel(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void saveUser(User user, OnSaveUserListener listener) {
        UserApiInterface api = UserApi.buildInstance();
        Call<User> saveUserCall = api.createUser(user);
        saveUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("saveUser", response.message());
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    listener.onSaveSuccess(user);
                } else {
                    Log.e("saveUser", response.message());
                    ApiError apiError = null;
                    try {
                        apiError = new Gson().fromJson(response.errorBody().string(), ApiError.class);
                    } catch (IOException e) {
                        listener.onSaveError("");
                        throw new RuntimeException(e);
                    }
                    listener.onSaveError(apiError.getMessage());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("saveUser", Objects.requireNonNull(t.getMessage()));
                listener.onSaveError("");
            }
        });
    }

    @Override
    public void getCurrentLocation(OnLocationReceivedListener listener) {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        listener.onLocationReceived(location.getLatitude(), location.getLongitude());
                    } else {
                        listener.onLocationError("Location is null");
                    }
                })
                .addOnFailureListener(e -> listener.onLocationError(e.getMessage()));
    }
}
