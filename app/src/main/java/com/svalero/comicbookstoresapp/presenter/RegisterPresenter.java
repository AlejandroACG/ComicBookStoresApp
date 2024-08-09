package com.svalero.comicbookstoresapp.presenter;

import android.content.Context;

import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.RegisterContract;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.model.RegisterModel;
import com.svalero.comicbookstoresapp.view.RegisterView;

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.Model.OnSaveUserListener,
RegisterContract.Model.OnLocationReceivedListener {
    private RegisterView view;
    private RegisterModel model;

    public RegisterPresenter(RegisterView view, Context context) {
        this.view = view;
        model = new RegisterModel(context);
    }

    @Override
    public void saveUser(String username, String email, String password, Float latitude, Float longitude) {
        User user = new User(username, email, password, latitude, longitude);
        model.saveUser(user, this);
    }

    @Override
    public void onSaveSuccess(User user) {
    }

    @Override
    public void onSaveError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showSaveErrorDialog(message);
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
        // TODO
    }
}
