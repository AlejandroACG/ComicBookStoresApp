package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.User;

public interface RegisterContract {
    interface Model {
        interface OnSaveUserListener {
            void onSaveSuccess(User user);
            void onSaveError(String message);
        }
        void saveUser(User user, OnSaveUserListener listener);

        void getCurrentLocation(OnLocationReceivedListener listener);
        interface OnLocationReceivedListener {
            void onLocationReceived(double latitude, double longitude);
            void onLocationError(String message);
        }
    }

    interface View {
        void showSaveSuccessDialog(User user, String message);
        void navigateToStoresMap(User user);
        void showSaveErrorDialog(String message);
        void showPermissionDeniedError();
        void showLocation(double latitude, double longitude);
        void showLocationError(String message);
    }

    interface Presenter {
        void saveUser(String username, String email, String password, Float latitude, Float longitude);
        void requestLocation();
    }
}
