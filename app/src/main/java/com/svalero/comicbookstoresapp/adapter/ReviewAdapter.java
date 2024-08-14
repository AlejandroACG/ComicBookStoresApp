package com.svalero.comicbookstoresapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.domain.Review;
import com.svalero.comicbookstoresapp.domain.User;
import java.util.List;
import java.util.Objects;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private final List<Review> reviews;
    private final User user;

    public ReviewAdapter(List<Review> reviews, User user) {
        this.reviews = reviews;
        this.user = user;
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
        Review currentReview = reviews.get(position);

        holder.tvTitle.setText(currentReview.getTitle());
        holder.tvContent.setText(currentReview.getContent());
        holder.tvDate.setText(String.valueOf(currentReview.getDate()));
        holder.itemView.setTag(currentReview.getId());

        if (user.getStoreReviews() != null ) {
            for (Review userReview : user.getStoreReviews()) {
                if (Objects.equals(currentReview.getId(), userReview.getId())) {
                    Log.e("ReviewAdapter", "Found the review");
                    holder.btnEdit.setVisibility(View.VISIBLE);
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvDate;
        public Button btnEdit;
        public Button btnDelete;
        public View parentView;

        public ReviewHolder(@NonNull View view) {
            super(view);
            parentView = view;

            tvTitle = view.findViewById(R.id.review_title);
            tvContent = view.findViewById(R.id.review_content);
            tvDate = view.findViewById(R.id.review_date);
            btnEdit = view.findViewById(R.id.review_edit);
            btnDelete = view.findViewById(R.id.review_delete);
        }
    }
}
