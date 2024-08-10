package com.svalero.comicbookstoresapp.presenter;

import android.content.Context;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.AddEditUserContract;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.dto.UserDTO;
import com.svalero.comicbookstoresapp.model.AddEditUserModel;
import com.svalero.comicbookstoresapp.view.AddEditUserView;

public class AddEditUserPresenter implements AddEditUserContract.Presenter, AddEditUserContract.Model.OnSaveUserListener,
AddEditUserContract.Model.OnLocationReceivedListener {
    private AddEditUserView view;
    private AddEditUserModel model;

    public AddEditUserPresenter(AddEditUserView view, Context context) {
        this.view = view;
        model = new AddEditUserModel(context);
    }

    @Override
    public void saveUser(String username, String email, String password, Float latitude, Float longitude) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            view.showSaveUserErrorDialog(view.getString(R.string.error_empty_fields));
        } else {
            UserDTO userDTO = new UserDTO(username, email, password, latitude, longitude);
            model.saveUser(userDTO, this);
        }
    }

    @Override
    public void onSaveUserSuccess(User user) {
        view.getEditor().putLong("USER_ID", user.getId());
        view.getEditor().apply();

        view.showSaveUserSuccessDialog(user.getUsername() + view.getString(R.string.registered_successfully));
    }

    @Override
    public void onSaveUserError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showSaveUserErrorDialog(message);
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
}
