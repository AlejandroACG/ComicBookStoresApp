package com.svalero.comicbookstoresapp.presenter;

import android.content.Context;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.StoreDetailsContract;
import com.svalero.comicbookstoresapp.db.HighlightedStore;
import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.model.StoreDetailsModel;
import com.svalero.comicbookstoresapp.view.StoreDetailsView;

public class StoreDetailsPresenter implements StoreDetailsContract.Presenter, StoreDetailsContract.Model.OnGetUserListener,
        StoreDetailsContract.Model.OnGetStoreListener, StoreDetailsContract.Model.OnDeleteReviewListener,
        StoreDetailsContract.Model.OnUpsertHighlightedStoreListener, StoreDetailsContract.Model.OnDeleteHighlightedStoreListener{
    private StoreDetailsView view;
    private StoreDetailsModel model;
    private Store store;

    public StoreDetailsPresenter(StoreDetailsView view, Context context) {
        this.view = view;
        model = new StoreDetailsModel(context);
    }

    @Override
    public void getUser(Long id) { model.getUser(id, this); }

    @Override
    public void onGetUserSuccess(User user) { view.loadUser(user); }

    @Override
    public void onGetUserError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showGetUserError(message);
    }

    @Override
    public void getStore(Long id) { model.getStore(id, this); }

    @Override
    public void onGetStoreSuccess(Store store) {
        this.store = store;
        view.setupStore(store);
    }

    @Override
    public void onGetStoreError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showGetStoreError(message);
    }

    @Override
    public void deleteReview(Long id) {
        model.deleteReview(id, this);
    }

    @Override
    public void onDeleteReviewSuccess() {
        view.showDeleteReviewSuccessDialog(view.getString(R.string.review_deleted_successfully));
    }

    @Override
    public void onDeleteReviewError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showDeleteReviewErrorDialog(message);
    }

    @Override
    public void upsertHighlightedStore(Store store, Boolean isGood) {
        HighlightedStore highlightedStore = new HighlightedStore();
        highlightedStore.setId(store.getId());
        highlightedStore.setName(store.getName());
        highlightedStore.setAddress(store.getAddress());
        highlightedStore.setLatitude(store.getLatitude());
        highlightedStore.setLongitude(store.getLongitude());
        highlightedStore.setPhone(store.getPhone());
        highlightedStore.setEmail(store.getEmail());
        highlightedStore.setWebsite(store.getWebsite());
        highlightedStore.setGood(isGood);

        model.upsertHighlightedStore(highlightedStore, this);
    }

    @Override
    public void onUpsertHighlightedStoreSuccess(HighlightedStore highlightedStore) {
        String message;
        if (highlightedStore.getGood()) {
            message = (highlightedStore.getName() + view.getString(R.string.set_as_favorite));
        } else {
            message = (highlightedStore.getName() + view.getString(R.string.set_as_hated));
        }
        view.showUpsertHighlightedStoreSuccessDialog(message);
    }

    @Override
    public void onUpsertHighlightedStoreError() {
        view.showUpsertHighlightedStoreErrorDialog(view.getString(R.string.error_modifying_the_store));
    }

    @Override
    public void deleteHighlightedStore(Store store) {
        HighlightedStore highlightedStore = new HighlightedStore();
        highlightedStore.setId(store.getId());
        model.deleteHighlightedStore(highlightedStore, this);
    }

    @Override
    public void onDeleteHighlightedStoreSuccess() {
        String message = (store.getName() + view.getString(R.string.set_as_indifferent));
        view.showDeleteHighlightedStoreSuccessDialog(message);
    }

    @Override
    public void onDeleteHighlightedStoreError() {
        view.showDeleteHighlightedStoreErrorDialog(view.getString(R.string.error_modifying_the_store));
    }
}
