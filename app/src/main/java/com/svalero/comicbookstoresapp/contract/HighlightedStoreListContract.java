package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.db.HighlightedStore;
import java.util.List;

public interface HighlightedStoreListContract {
    interface Model {
        interface OnGetHighlightedStoresListener {
            void onGetHighlightedStoresSuccess(List<HighlightedStore> highlightedStores);
            void onGetHighlightedStoresError();
        }
        void getHighlightedStores(OnGetHighlightedStoresListener listener);
    }

    interface View {
        void loadHighlightedStores(List<HighlightedStore> highlightedStores);
        void showGetHighlightedStoresErrorDialog(String message);
    }

    interface Presenter {
        void getHighlightedStores();
    }
}
