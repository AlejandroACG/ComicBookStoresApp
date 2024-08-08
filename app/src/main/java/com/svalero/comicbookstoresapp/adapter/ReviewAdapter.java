package com.svalero.comicbookstoresapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.domain.Review;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private final List<Review> reviews;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewHolder holder, int position) {
        holder.tvTitle.setText(reviews.get(position).getTitle());
        holder.tvContent.setText(reviews.get(position).getContent());
        holder.tvRating.setText(String.valueOf(reviews.get(position).getRating()));
        holder.tvDate.setText(String.valueOf(reviews.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvRating;
        public TextView tvDate;
        public Button btnEdit;
        public Button btnDelete;
        public View parentView;

        public ReviewHolder(@NonNull View view) {
            super(view);
            parentView = view;

            tvTitle = view.findViewById(R.id.review_title);
            tvContent = view.findViewById(R.id.review_content);
            tvRating = view.findViewById(R.id.review_rating);
            tvDate = view.findViewById(R.id.review_date);
            btnEdit = view.findViewById(R.id.review_edit);
            btnDelete = view.findViewById(R.id.review_delete);
        }
    }
}
