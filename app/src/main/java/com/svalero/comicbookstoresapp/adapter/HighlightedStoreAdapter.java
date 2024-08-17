package com.svalero.comicbookstoresapp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
            holder.clName.setBackgroundColor(Color.parseColor("#fcba03"));
            holder.clAddress.setBackgroundColor(Color.parseColor("#fcba03"));
            holder.clPhone.setBackgroundColor(Color.parseColor("#fcba03"));
            holder.clWebsite.setBackgroundColor(Color.parseColor("#fcba03"));
            holder.clEmail.setBackgroundColor(Color.parseColor("#fcba03"));
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
        public ConstraintLayout clName;
        public TextView tvName;
        public ConstraintLayout clAddress;
        public TextView tvAddress;
        public ConstraintLayout clPhone;
        public TextView tvPhone;
        public ConstraintLayout clWebsite;
        public TextView tvWebsite;
        public ConstraintLayout clEmail;
        public TextView tvEmail;
        public View parentView;

        public HighlightedStoreHolder(@NonNull View view) {
            super(view);
            parentView = view;

            clName = view.findViewById(R.id.constraint_name);
            tvName = view.findViewById(R.id.highlighted_store_name);
            clAddress = view.findViewById(R.id.constraint_address);
            tvAddress = view.findViewById(R.id.highlighted_store_address);
            clPhone = view.findViewById(R.id.constraint_phone);
            tvPhone = view.findViewById(R.id.highlighted_store_phone);
            clWebsite = view.findViewById(R.id.constraint_website);
            tvWebsite = view.findViewById(R.id.highlighted_store_website);
            clEmail = view.findViewById(R.id.constraint_email);
            tvEmail = view.findViewById(R.id.highlighted_store_email);
        }
    }
}
