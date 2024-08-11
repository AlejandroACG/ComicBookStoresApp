package com.svalero.comicbookstoresapp.presenter;

import static com.svalero.comicbookstoresapp.util.Constants.PREFERENCES_ID;
import android.content.Context;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.EditUserContract;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.model.EditUserModel;
import com.svalero.comicbookstoresapp.view.EditUserView;

public class EditUserPresenter implements EditUserContract.Presenter, EditUserContract.Model.OnLocationReceivedListener,
        EditUserContract.Model.OnGetUserListener {
    private EditUserView view;
    private EditUserModel model;

    public EditUserPresenter(EditUserView view, Context context) {
        this.view = view;
        model = new EditUserModel(context);
    }

    @Override
    public void getUser() {
        model.getUser(view.getPrefs().getLong(PREFERENCES_ID, 0), this);
    }

    @Override
    public void onGetUserSuccess(User user) {
        view.getUser(user);
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
}
