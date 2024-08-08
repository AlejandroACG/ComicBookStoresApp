package com.svalero.comicbookstoresapp.presenter;

import com.svalero.comicbookstoresapp.contract.StoreDetailsContract;
import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.model.StoreDetailsModel;
import com.svalero.comicbookstoresapp.view.StoreDetailsView;
import java.util.List;

public class StoreDetailsPresenter implements StoreDetailsContract.Presenter, StoreDetailsContract.Model.OnLoadReviewsListener {
    private StoreDetailsView view;
    private StoreDetailsModel model;

    public StoreDetailsPresenter(StoreDetailsView view) {
        this.view = view;
        model = new StoreDetailsModel();
    }

    @Override
    public void loadAllReviews() {
        model.loadAllReviews(this);
    }

    @Override
    public void onLoadReviewsSuccess(List<Review> reviews) {
        view.listReviews(reviews);
    }

    @Override
    public void onLoadReviewsError(String message) {
        view.showMessage(message);
    }
}
