package com.svalero.comicbookstoresapp.presenter;

import android.content.Context;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.StoresMapContract;
import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.model.StoresMapModel;
import com.svalero.comicbookstoresapp.view.StoresMapView;
import java.util.List;

public class StoresMapPresenter implements StoresMapContract.Presenter, StoresMapContract.Model.OnGetStoresListener,
        StoresMapContract.Model.OnGetUserListener {
    private StoresMapView view;
    private StoresMapModel model;

    public StoresMapPresenter(StoresMapView view, Context context) {
        this.view = view;
        model = new StoresMapModel(context);
    }

    @Override
    public void getStores() { model.getStores(this); }

    @Override
    public void onGetStoresSuccess(List<Store> stores) {
        view.addStoreMarkers(stores);
    }

    @Override
    public void onGetStoresError(String message) {
    }

    @Override
    public void getUser(Long id) { model.getUser(id, this); }

    @Override
    public void onGetUserSuccess(User user) {
        view.addUserMarker(user);
    }

    @Override
    public void onGetUserError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showGetUserErrorDialog(message);
    }
}
