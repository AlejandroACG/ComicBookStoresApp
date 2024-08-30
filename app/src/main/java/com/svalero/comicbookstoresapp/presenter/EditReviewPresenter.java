package com.svalero.comicbookstoresapp.presenter;

import android.content.Context;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.EditReviewContract;
import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.dto.ReviewDTO;
import com.svalero.comicbookstoresapp.model.EditReviewModel;
import com.svalero.comicbookstoresapp.view.EditReviewView;

public class EditReviewPresenter implements EditReviewContract.Presenter, EditReviewContract.Model.OnGetReviewListener,
        EditReviewContract.Model.OnUpdateReviewListener {
    private EditReviewView view;
    private EditReviewModel model;

    public EditReviewPresenter(EditReviewView view, Context context) {
        this.view = view;
        model = new EditReviewModel(context);
    }

    @Override
    public void getReview(Long id) {
        model.getReview(id, this);
    }

    @Override
    public void onGetReviewSuccess(Review review) {
        view.setupReviewData(review);
    }

    @Override
    public void onGetReviewError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showGetReviewErrorDialog(message);
    }

    @Override
    public void updateReview(Long id, ReviewDTO reviewDTO) {
        if (reviewDTO.getTitle().isEmpty() || reviewDTO.getContent().isEmpty()) {
            view.showUpdateReviewErrorDialog(view.getString(R.string.error_empty_fields));
        } else {
            model.updateReview(id, reviewDTO, this);
        }
    }

    @Override
    public void onUpdateReviewSuccess(Review review) {
        view.showUpdateReviewSuccessDialog(view.getString(R.string.review_edited_successfully));
    }

    @Override
    public void onUpdateReviewError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showUpdateReviewErrorDialog(message);
    }
}
