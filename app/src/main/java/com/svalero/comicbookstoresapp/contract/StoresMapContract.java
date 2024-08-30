package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;

import java.util.List;

public interface StoresMapContract {
    interface Model {
        interface OnGetStoresListener {
            void onGetStoresSuccess(List<Store> stores);
            void onGetStoresError(String message);
        }
        void getStores(OnGetStoresListener listener);

        interface OnGetUserListener {
            void onGetUserSuccess(User user);
            void onGetUserError(String message);
        }
        void getUser(Long id, OnGetUserListener listener);
    }

    interface View {
        void getStores();
        void addStoreMarkers(List<Store> stores);
        void showGetStoresErrorDialog(String message);
        void getUser();
        void addUserMarker(User user);
        void showGetUserErrorDialog(String message);
    }

    interface Presenter {
        void getStores();
        void getUser(Long id);
    }
}
