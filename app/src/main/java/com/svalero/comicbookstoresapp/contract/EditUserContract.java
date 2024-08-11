package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.dto.UserDTO;

public interface EditUserContract {
    interface Model {
        interface OnGetUserListener {
            void onGetUserSuccess(User user);
            void onGetUserError(String message);
        }
        void getUser(Long id, OnGetUserListener listener);

        interface OnLocationReceivedListener {
            void onLocationReceived(double latitude, double longitude);
            void onLocationError(String message);
        }
        void getCurrentLocation(OnLocationReceivedListener listener);

        interface OnUpdateUserListener {
            void onUpdateUserSuccess(User user);
            void onUpdateUserError(String message);
        }
        void updateUser(Long id, UserDTO userDTO, OnUpdateUserListener listener);

        interface OnDeleteUserListener {
            void onDeleteUserSuccess();
            void onDeleteUserError(String message);
        }
        void deleteUser(Long id, OnDeleteUserListener listener);
    }

    interface View {
        void showGetUserErrorDialog(String message);
        void showUpdateUserSuccessDialog(String message);
        void showUpdateUserErrorDialog(String message);
        void showDeleteUserSuccessDialog(String message);
        void showDeleteUserErrorDialog(String message);
        void navigateToStoresMap();
        void navigateToMain();
        void showPermissionDeniedError();
        void showLocation(double latitude, double longitude);
        void showLocationError(String message);
    }

    interface Presenter {
        void getUser(Long id);
        void requestLocation();
        void updateUser(Long id, UserDTO userDTO);
        void deleteUser(Long id);
    }
}
