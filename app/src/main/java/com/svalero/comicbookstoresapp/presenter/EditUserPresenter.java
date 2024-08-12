package com.svalero.comicbookstoresapp.presenter;

import android.content.Context;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.EditUserContract;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.dto.UserDTO;
import com.svalero.comicbookstoresapp.model.EditUserModel;
import com.svalero.comicbookstoresapp.view.EditUserView;

public class EditUserPresenter implements EditUserContract.Presenter, EditUserContract.Model.OnLocationReceivedListener,
        EditUserContract.Model.OnGetUserListener, EditUserContract.Model.OnUpdateUserListener,
        EditUserContract.Model.OnDeleteUserListener {
    private EditUserView view;
    private EditUserModel model;

    public EditUserPresenter(EditUserView view, Context context) {
        this.view = view;
        model = new EditUserModel(context);
    }

    @Override
    public void getUser(Long id) {
        model.getUser(id, this);
    }

    @Override
    public void onGetUserSuccess(User user) {
        view.setupUserData(user);
    }

    @Override
    public void onGetUserError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showGetUserErrorDialog(message);
    }

    @Override
    public void requestLocation() {
        model.getCurrentLocation(this);
    }

    @Override
    public void onLocationReceived(double latitude, double longitude) {
        view.showLocation(latitude, longitude);
    }

    @Override
    public void onLocationError(String error) {
        view.showLocationError(error);
        view.addNoGPSMarker();
    }

    @Override
    public void updateUser(Long id, UserDTO userDTO) {
        if (userDTO.getUsername().isEmpty() || userDTO.getEmail().isEmpty() || userDTO.getPassword().isEmpty() ||
                userDTO.getLatitude() == null || userDTO.getLongitude() == null) {
            view.showUpdateUserErrorDialog(view.getString(R.string.error_empty_fields));
        } else {
            model.updateUser(id, userDTO, this);
        }
    }

    @Override
    public void onUpdateUserSuccess(User user) {
        view.showUpdateUserSuccessDialog(user.getUsername() + view.getString(R.string.edited_successfully));
    }

    @Override
    public void onUpdateUserError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showUpdateUserErrorDialog(message);
    }

    @Override
    public void deleteUser(Long id) {
        model.deleteUser(id, this);
    }

    @Override
    public void onDeleteUserSuccess() {
        view.showDeleteUserSuccessDialog(view.getString(R.string.user_deleted_successfully));
    }

    @Override
    public void onDeleteUserError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showDeleteUserErrorDialog(message);
    }
}
