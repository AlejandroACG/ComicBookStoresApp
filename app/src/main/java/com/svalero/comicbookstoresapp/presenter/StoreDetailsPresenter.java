package com.svalero.comicbookstoresapp.presenter;

import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.StoreDetailsContract;
import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.model.StoreDetailsModel;
import com.svalero.comicbookstoresapp.view.StoreDetailsView;

public class StoreDetailsPresenter implements StoreDetailsContract.Presenter, StoreDetailsContract.Model.OnGetUserListener,
        StoreDetailsContract.Model.OnGetStoreListener, StoreDetailsContract.Model.OnDeleteReviewListener {
    private StoreDetailsView view;
    private StoreDetailsModel model;

    public StoreDetailsPresenter(StoreDetailsView view) {
        this.view = view;
        model = new StoreDetailsModel();
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
    public void onGetStoreSuccess(Store store) { view.setupStore(store); }

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
}
