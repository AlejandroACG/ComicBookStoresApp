package com.svalero.comicbookstoresapp.api;

import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.dto.LoginDTO;
import com.svalero.comicbookstoresapp.dto.UserDTO;
import com.svalero.comicbookstoresapp.dto.IdDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiInterface {
    @POST("users")
    Call<User> createUser(@Body UserDTO userDTO);

    @POST("user/login")
    Call<IdDTO> login(@Body LoginDTO loginDTO);

    @GET("user/{id}")
    Call<User> getUser(@Path("id") long id);

    @PUT("user/{id}")
    Call<User> updateUser(@Path("id") long id, @Body UserDTO userDTO);

    @DELETE("user/{id}")
    Call<Void> deleteUser(@Path("id") long id);
}
