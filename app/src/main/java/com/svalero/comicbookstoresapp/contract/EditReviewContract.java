package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.dto.ReviewDTO;

public interface EditReviewContract {
    interface Model {
        interface OnGetReviewListener {
            void onGetReviewSuccess(Review review);
            void onGetReviewError(String message);
        }
        void getReview(Long id, OnGetReviewListener listener);

        interface OnUpdateReviewListener {
            void onUpdateReviewSuccess(Review review);
            void onUpdateReviewError(String message);
        }
        void updateReview(Long id, ReviewDTO reviewDTO, OnUpdateReviewListener listener);
    }

    interface View {
        void setupReviewData(Review review);
        void showGetReviewErrorDialog(String message);
        void showUpdateReviewSuccessDialog(String message);
        void showUpdateReviewErrorDialog(String message);
    }

    interface Presenter {
        void getReview(Long id);
        void updateReview(Long id, ReviewDTO reviewDTO);
    }
}
