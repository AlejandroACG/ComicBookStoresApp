package com.svalero.comicbookstoresapp.api;

import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.dto.LoginDTO;
import com.svalero.comicbookstoresapp.dto.UserDTO;
import com.svalero.comicbookstoresapp.dto.idDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiInterface {
    @POST("users")
    Call<User> createUser(@Body UserDTO userDTO);

    @POST("user/login")
    Call<idDTO> login(@Body LoginDTO loginDTO);
}
