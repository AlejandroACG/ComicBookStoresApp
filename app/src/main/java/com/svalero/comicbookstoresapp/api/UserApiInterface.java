package com.svalero.comicbookstoresapp.api;

import com.svalero.comicbookstoresapp.domain.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiInterface {
    @POST("users")
    Call<User> createUser(@Body User user);
}
