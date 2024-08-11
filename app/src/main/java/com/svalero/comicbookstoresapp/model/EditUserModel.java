package com.svalero.comicbookstoresapp.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.svalero.comicbookstoresapp.api.UserApi;
import com.svalero.comicbookstoresapp.api.UserApiInterface;
import com.svalero.comicbookstoresapp.contract.EditUserContract;
import com.svalero.comicbookstoresapp.domain.ApiError;
import com.svalero.comicbookstoresapp.domain.User;
import java.io.IOException;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserModel implements EditUserContract.Model {
    private final FusedLocationProviderClient fusedLocationClient;
    private final Context context;

    public EditUserModel(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        this.context = context;
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

    @Override
    public void getCurrentLocation(OnLocationReceivedListener listener) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            listener.onLocationReceived(location.getLatitude(), location.getLongitude());
                        } else {
                            listener.onLocationError("Unable to retrieve location.");
                        }
                    })
                    .addOnFailureListener(e -> listener.onLocationError(e.getMessage()));
        } else {
            listener.onLocationError("Location permission not granted.");
        }
    }
}
