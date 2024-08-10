package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.User;

public interface AddEditContract {
    interface Model {
        interface OnSaveUserListener {
            void onSaveSuccess(User user);
            void onSaveError(String message);
        }
        void saveUser(User user, OnSaveUserListener listener);

//        interface OnUpdateUserListener {
//            void onUpdateSuccess(User user);
//            void onUpdateError(String message);
//        }
//        void updateUser(User user, OnUpdateUserListener listener);

        void getCurrentLocation(OnLocationReceivedListener listener);
        interface OnLocationReceivedListener {
            void onLocationReceived(double latitude, double longitude);
            void onLocationError(String message);
        }
    }

    interface View {
        void showSaveSuccessDialog(User user, String message);
//        void showUpdateSuccessDialog(User user, String message);
        void navigateToStoresMap(User user);
        void showSaveErrorDialog(String message);
//        void showUpdateErrorDialog(String message);
        void showPermissionDeniedError();
        void showLocation(double latitude, double longitude);
        void showLocationError(String message);
    }

    interface Presenter {
        void saveUser(String username, String email, String password, Float latitude, Float longitude);
//        void updateUser(Long id, String username, String email, String password, Float latitude, Float longitude);
        void requestLocation();
    }
}
