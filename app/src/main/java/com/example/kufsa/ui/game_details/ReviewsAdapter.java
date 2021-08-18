package com.example.kufsa.ui.game_details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kufsa.R;
import com.example.kufsa.data.Review;
import com.example.kufsa.databinding.ItemReviewInRecyclerBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;

public class ReviewsAdapter extends FirestoreRecyclerAdapter<Review, ReviewsAdapter.ReviewHolder> {

    public ReviewsAdapter(@NonNull FirestoreRecyclerOptions<Review> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReviewHolder holder, int position, @NonNull Review model) {
        holder.binding.reviewContentTextView.setText(model.getDescription());
        holder.binding.smallRatingBarInfo.setRating(model.getStarNum());
        holder.binding.userNameTextview.setText(model.getUserName());
        holder.binding.dateReviewTextView.setText(new SimpleDateFormat("dd/MM/yy").format(model.getDate()));


        Glide.with(holder.itemView)
                .load(model.getPhotoUrl())
                .error(R.drawable.outline_account_circle_24)
                .into(holder.binding.profileImgView);
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_in_recycler,
                parent, false);
        return new ReviewHolder(v);
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        ItemReviewInRecyclerBinding binding;

        public ReviewHolder(View itemView) {
            super(itemView);
            binding = ItemReviewInRecyclerBinding.bind(itemView);
        }
    }

}

