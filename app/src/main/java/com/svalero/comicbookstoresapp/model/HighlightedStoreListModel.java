package com.svalero.comicbookstoresapp.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.svalero.comicbookstoresapp.contract.HighlightedStoreListContract;
import com.svalero.comicbookstoresapp.db.AppDatabase;
import com.svalero.comicbookstoresapp.db.DatabaseClient;
import com.svalero.comicbookstoresapp.db.HighlightedStore;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HighlightedStoreListModel implements HighlightedStoreListContract.Model {
    private AppDatabase db;
    private ExecutorService executor;

    public HighlightedStoreListModel(Context context) {
        this.db = DatabaseClient.getInstance(context).getAppDatabase();
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void getHighlightedStores(OnGetHighlightedStoresListener listener) {
        executor.execute(() -> {
            try {
                List<HighlightedStore> highlightedStores = db.highlightedStoreDao().getAll();

                new Handler(Looper.getMainLooper()).post(() -> listener.onGetHighlightedStoresSuccess(highlightedStores));
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(listener::onGetHighlightedStoresError);
            }
        });
    }
}
