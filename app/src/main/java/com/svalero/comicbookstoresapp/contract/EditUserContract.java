package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.User;

public interface EditUserContract {
    interface Model {
        interface OnGetUserListener {
            void onGetUserSuccess(User user);
            void onGetUserError(String message);
        }
        void getUser(Long id, OnGetUserListener listener);

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
        void showGetUserErrorDialog(String message);
        //        void showUpdateSuccessDialog(User user, String message);
        //        void showUpdateErrorDialog(String message);
        void navigateToStoresMap();
        void showPermissionDeniedError();
        void showLocation(double latitude, double longitude);
        void showLocationError(String message);
    }

    interface Presenter {
        void getUser();
        //        void updateUser(Long id, String username, String email, String password, Float latitude, Float longitude);
        void requestLocation();
    }
}
