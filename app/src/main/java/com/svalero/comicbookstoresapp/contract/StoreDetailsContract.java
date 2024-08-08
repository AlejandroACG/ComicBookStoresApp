package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.Review;
import java.util.List;

public interface StoreDetailsContract {
    interface View {
        void listReviews(List<Review> reviews);
        void showMessage(String message);
    }

    interface Presenter {
        void loadAllReviews();
    }

    interface Model {
        interface OnLoadReviewsListener {
            void onLoadReviewsSuccess(List<Review> reviews);
            void onLoadReviewsError(String message);
        }
        void loadAllReviews(OnLoadReviewsListener listener);
    }
}
