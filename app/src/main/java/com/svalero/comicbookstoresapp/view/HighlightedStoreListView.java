package com.svalero.comicbookstoresapp.view;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.adapter.HighlightedStoreAdapter;
import com.svalero.comicbookstoresapp.contract.HighlightedStoreListContract;
import com.svalero.comicbookstoresapp.db.HighlightedStore;
import com.svalero.comicbookstoresapp.presenter.HighlightedStoreListPresenter;
import com.svalero.comicbookstoresapp.util.InnerBaseActivity;
import java.util.ArrayList;
import java.util.List;

public class HighlightedStoreListView extends InnerBaseActivity implements HighlightedStoreListContract.View {
    private CheckBox cbHated;
    private HighlightedStoreAdapter adapter;
    private HighlightedStoreListPresenter presenter;
    private List<HighlightedStore> highlightedStores;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_highlighted_stores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        cbHated = findViewById(R.id.check_box_hated);
        cbHated.setEnabled(false);
        cbHated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                launchGetHighlightedStores();
            }
        });

        recyclerView = findViewById(R.id.highlighted_store_list);
        recyclerView.setHasFixedSize(true);

        highlightedStores = new ArrayList<>();
        presenter = new HighlightedStoreListPresenter(this, this);
        launchGetHighlightedStores();
    }

    private void launchGetHighlightedStores() {
        cbHated.setEnabled(false);
        presenter.getHighlightedStores();
    }

    @Override
    public void loadHighlightedStores(List<HighlightedStore> highlightedStores) {
        this.highlightedStores.clear();
        this.highlightedStores.addAll(highlightedStores);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HighlightedStoreAdapter(highlightedStores, cbHated.isChecked());
        recyclerView.setAdapter(adapter);
        if (highlightedStores != null) {
            adapter.notifyDataSetChanged();
        }
        cbHated.setEnabled(true);
    }

    @Override
    public void showGetHighlightedStoresErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }
}
