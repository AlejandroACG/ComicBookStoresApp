package com.svalero.comicbookstoresapp.api;

import static com.svalero.comicbookstoresapp.util.Constants.BASE_URL;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewApi {
    public static ReviewApiInterface buildInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ReviewApiInterface.class);
    }
}
