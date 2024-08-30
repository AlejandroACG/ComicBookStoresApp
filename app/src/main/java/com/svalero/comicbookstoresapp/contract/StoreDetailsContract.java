package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.db.HighlightedStore;
import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.dto.ReviewDTO;

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

        interface OnUpsertHighlightedStoreListener {
            void onUpsertHighlightedStoreSuccess(HighlightedStore highlightedStore);
            void onUpsertHighlightedStoreError();
        }
        void upsertHighlightedStore(HighlightedStore highlightedStore, OnUpsertHighlightedStoreListener listener);

        interface OnDeleteHighlightedStoreListener {
            void onDeleteHighlightedStoreSuccess();
            void onDeleteHighlightedStoreError();
        }
        void deleteHighlightedStore(HighlightedStore highlightedStore, OnDeleteHighlightedStoreListener listener);
    }

    interface View {
        void loadUser(User user);
        void showGetUserError(String message);
        void setupStore(Store store);
        void showGetStoreError(String message);
        void showDeleteReviewSuccessDialog(String message);
        void showDeleteReviewErrorDialog(String message);
        void showUpsertHighlightedStoreSuccessDialog(String message);
        void showUpsertHighlightedStoreErrorDialog(String message);
        void showDeleteHighlightedStoreSuccessDialog(String message);
        void showDeleteHighlightedStoreErrorDialog(String message);
    }

    interface Presenter {
        void getUser(Long id);
        void getStore(Long id);
        void deleteReview(Long id);
        void upsertHighlightedStore(Store store, Boolean isGood);
        void deleteHighlightedStore(Store store);
    }
}
