package com.svalero.comicbookstoresapp.api;

import com.svalero.comicbookstoresapp.domain.Review;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewApiInterface {
    @GET("store-reviews")
    Call<List<Review>> getReviews();

    @POST("store-reviews")
    Call<Review> addReview(@Body Review review);

    @DELETE("store-review/{id}")
    Call<Void> deleteReview(@Path("id") Long id);
}
