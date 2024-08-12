package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;
import java.util.List;

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
    }

    interface View {
        void showLoadReviewsError(String message);
        void loadUser(User user);
        void showGetUserError(String message);
        void setupStore(Store store);
        void showGetStoreError(String message);
    }

    interface Presenter {
        void getUser(Long id);
        void getStore(Long id);
    }
}
