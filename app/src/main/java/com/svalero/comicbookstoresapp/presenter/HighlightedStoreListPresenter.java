package com.svalero.comicbookstoresapp.presenter;

import android.content.Context;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.HighlightedStoreListContract;
import com.svalero.comicbookstoresapp.db.HighlightedStore;
import com.svalero.comicbookstoresapp.model.HighlightedStoreListModel;
import com.svalero.comicbookstoresapp.view.HighlightedStoreListView;
import java.util.List;

public class HighlightedStoreListPresenter implements HighlightedStoreListContract.Presenter,
        HighlightedStoreListContract.Model.OnGetHighlightedStoresListener  {
    private HighlightedStoreListView view;
    private HighlightedStoreListModel model;

    public HighlightedStoreListPresenter(HighlightedStoreListView view, Context context) {
        this.view = view;
        model = new HighlightedStoreListModel(context);
    }

    @Override
    public void getHighlightedStores() {
        model.getHighlightedStores(this);
    }

    @Override
    public void onGetHighlightedStoresSuccess(List<HighlightedStore> highlightedStores) {
        view.loadHighlightedStores(highlightedStores);
    }

    @Override
    public void onGetHighlightedStoresError() {
        view.showGetHighlightedStoresErrorDialog(view.getString(R.string.error_stores_get));
    }
}
