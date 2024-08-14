package com.svalero.comicbookstoresapp.presenter;

import android.content.Context;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.AddReviewContract;
import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.dto.ReviewDTO;
import com.svalero.comicbookstoresapp.model.AddReviewModel;
import com.svalero.comicbookstoresapp.view.AddReviewView;

public class AddReviewPresenter implements AddReviewContract.Presenter, AddReviewContract.Model.OnSaveReviewListener {
    private AddReviewView view;
    private AddReviewModel model;

    public AddReviewPresenter(AddReviewView view, Context context) {
        this.view = view;
        model = new AddReviewModel(context);
    }

    @Override
    public void saveReview(Long userId, Long storeId, String title, String content) {
        if (title.isEmpty() || content.isEmpty()) {
            view.showSaveReviewErrorDialog(view.getString(R.string.error_empty_fields));
        } else {
            ReviewDTO reviewDTO = new ReviewDTO(userId, storeId, title, content);
            model.saveReview(reviewDTO, this);
        }
    }

    @Override
    public void onSaveReviewSuccess(Review review) {
        view.showSaveReviewSuccessDialog(view.getString(R.string.review_posted_successfully));
    }

    @Override
    public void onSaveReviewError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showSaveReviewErrorDialog(message);
    }
}
