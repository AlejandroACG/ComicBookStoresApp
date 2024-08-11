package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.dto.UserDTO;

public interface RegisterContract {
    interface Model {
        interface OnSaveUserListener {
            void onSaveUserSuccess(User user);
            void onSaveUserError(String message);
        }
        void saveUser(UserDTO userDTO, OnSaveUserListener listener);

        void getCurrentLocation(OnLocationReceivedListener listener);
        interface OnLocationReceivedListener {
            void onLocationReceived(double latitude, double longitude);
            void onLocationError(String message);
        }
    }

    interface View {
        void showSaveUserSuccessDialog(String message);
        void showSaveUserErrorDialog(String message);
        void navigateToStoresMap();
        void showPermissionDeniedError();
        void showLocation(double latitude, double longitude);
        void showLocationError(String message);
    }

    interface Presenter {
        void saveUser(String username, String email, String password, Float latitude, Float longitude);
        void requestLocation();
    }
}
