package com.svalero.comicbookstoresapp.model;

import android.util.Log;
import com.google.gson.Gson;
import com.svalero.comicbookstoresapp.api.UserApi;
import com.svalero.comicbookstoresapp.api.UserApiInterface;
import com.svalero.comicbookstoresapp.contract.LoginContract;
import com.svalero.comicbookstoresapp.domain.ApiError;
import com.svalero.comicbookstoresapp.dto.LoginDTO;
import com.svalero.comicbookstoresapp.dto.idDTO;
import java.io.IOException;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements LoginContract.Model {
    @Override
    public void login(LoginDTO loginDTO, OnLoginListener listener) {
        UserApiInterface api = UserApi.buildInstance();
        Call<idDTO> loginCall = api.login(loginDTO);
        loginCall.enqueue(new Callback<idDTO>() {
            @Override
            public void onResponse(Call<idDTO> call, Response<idDTO> response) {
                Log.e("login", response.message());
                if (response.isSuccessful() && response.body() != null) {
                    Long id = response.body().getId();
                    listener.onLoginSuccess(id);
                } else {
                    Log.e("login", response.message());
                    ApiError apiError = null;
                    try {
                        apiError = new Gson().fromJson(response.errorBody().string(), ApiError.class);
                    } catch (IOException e) {
                        listener.onLoginError("");
                        throw new RuntimeException(e);
                    }
                    listener.onLoginError(apiError.getMessage());
                }
            }

            @Override
            public void onFailure(Call<idDTO> call, Throwable t) {
                Log.e("login", Objects.requireNonNull(t.getMessage()));
                listener.onLoginError("");
            }
        });
    }
}
