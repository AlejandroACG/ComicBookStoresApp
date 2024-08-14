package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.dto.ReviewDTO;

public interface AddReviewContract {
    interface Model {
        interface OnSaveReviewListener {
            void onSaveReviewSuccess(Review review);
            void onSaveReviewError(String message);
        }
        void saveReview(ReviewDTO reviewDTO, OnSaveReviewListener listener);
    }

    interface View {
        void showSaveReviewSuccessDialog(String message);
        void showSaveReviewErrorDialog(String message);
    }

    interface Presenter {
        void saveReview(Long userId, Long storeId, String title, String content);
    }
}
