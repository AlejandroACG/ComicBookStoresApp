package com.svalero.comicbookstoresapp.api;

import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.dto.ReviewDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReviewApiInterface {
    @POST("store-reviews")
    Call<Review> createReview(@Body ReviewDTO reviewDTO);

    @GET("store-review/{id}")
    Call<Review> getReview(@Path("id") long id);

    @PUT("store-review/{id}")
    Call<Review> updateReview(@Path("id") long id, @Body ReviewDTO reviewDTO);

    @DELETE("store-review/{id}")
    Call<Void> deleteReview(@Path("id") long id);
}
