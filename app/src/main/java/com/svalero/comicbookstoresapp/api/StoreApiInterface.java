package com.svalero.comicbookstoresapp.api;

import com.svalero.comicbookstoresapp.domain.Store;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoreApiInterface {
    @GET("stores")
    Call<List<Store>> getStores();

    @GET("store/{id}")
    Call<Store> getStore(@Path("id") long id);
}
