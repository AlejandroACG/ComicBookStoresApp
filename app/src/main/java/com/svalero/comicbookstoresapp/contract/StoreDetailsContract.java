package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;

public interface StoreDetailsContract {
    interface Model {
        interface OnGetUserListener {
            void onGetUserSuccess(User user);
            void onGetUserError(String message);
        }
        void getUser(Long id, OnGetUserListener listener);

        interface OnGetStoreListener {
            void onGetStoreSuccess(Store store);
            void onGetStoreError(String message);
        }
        void getStore(Long id, OnGetStoreListener listener);

        interface OnDeleteReviewListener {
            void onDeleteReviewSuccess();
            void onDeleteReviewError(String message);
        }
        void deleteReview(Long id, StoreDetailsContract.Model.OnDeleteReviewListener listener);
    }

    interface View {
        void showLoadReviewsError(String message);
        void loadUser(User user);
        void showGetUserError(String message);
        void setupStore(Store store);
        void showGetStoreError(String message);
        void showDeleteReviewSuccessDialog(String message);
        void showDeleteReviewErrorDialog(String message);
    }

    interface Presenter {
        void getUser(Long id);
        void getStore(Long id);
        void deleteReview(Long id);
    }
}
