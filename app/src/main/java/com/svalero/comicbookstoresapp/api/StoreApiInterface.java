package com.svalero.comicbookstoresapp.api;

import com.svalero.comicbookstoresapp.domain.Store;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StoreApiInterface {
    @GET("stores")
    Call<List<Store>> getStores();
}
