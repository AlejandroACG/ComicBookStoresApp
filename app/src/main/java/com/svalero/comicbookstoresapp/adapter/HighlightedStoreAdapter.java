package com.svalero.comicbookstoresapp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.db.HighlightedStore;
import java.util.List;

public class HighlightedStoreAdapter extends RecyclerView.Adapter<HighlightedStoreAdapter.HighlightedStoreHolder> {
    private final List<HighlightedStore> highlightedStores;

    public HighlightedStoreAdapter(List<HighlightedStore> highlightedStores) {
        this.highlightedStores = highlightedStores;
    }

    @NonNull
    @Override
    public HighlightedStoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.highlighted_store_list_item, parent, false);
        return new HighlightedStoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlightedStoreHolder holder, int position) {
        HighlightedStore highlightedStore = highlightedStores.get(position);

        if (highlightedStore.getGood()) {
            holder.llHighlightedStore.setBackgroundColor(Color.parseColor("#94de00"));
        } else {
            holder.llHighlightedStore.setBackgroundColor(Color.parseColor("#0a0000"));
        }
        holder.tvName.setText(highlightedStore.getName());
        holder.tvAddress.setText(highlightedStore.getAddress());
        holder.tvPhone.setText(highlightedStore.getPhone());
        holder.tvWebsite.setText(highlightedStore.getWebsite());
        holder.tvEmail.setText(highlightedStore.getEmail());
    }

    @Override
    public int getItemCount() {
        return highlightedStores.size();
    }

    public class HighlightedStoreHolder extends RecyclerView.ViewHolder {
        public LinearLayout llHighlightedStore;
        public TextView tvName;
        public TextView tvAddress;
        public TextView tvPhone;
        public TextView tvWebsite;
        public TextView tvEmail;
        public View parentView;

        public HighlightedStoreHolder(@NonNull View view) {
            super(view);
            parentView = view;

            llHighlightedStore = view.findViewById(R.id.highlighted_store_layout);
            tvName = view.findViewById(R.id.highlighted_store_name);
            tvAddress = view.findViewById(R.id.highlighted_store_address);
            tvPhone = view.findViewById(R.id.highlighted_store_phone);
            tvWebsite = view.findViewById(R.id.highlighted_store_website);
            tvEmail = view.findViewById(R.id.highlighted_store_email);
        }
    }
}
