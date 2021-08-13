package com.example.kufsa.ui.game_details;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kufsa.R;
import com.example.kufsa.databinding.FragmentGameReviewsTabBinding;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import java.util.Random;

public class GameReviewsTabFragment extends Fragment {

    RatingReviews ratingReviews;
    Pair[] colors = new Pair[]{
            new Pair<>(Color.parseColor("#0c96c7"), Color.parseColor("#00fe77")),
            new Pair<>(Color.parseColor("#7b0ab4"), Color.parseColor("#ff069c")),
            new Pair<>(Color.parseColor("#fe6522"), Color.parseColor("#fdd116")),
            new Pair<>(Color.parseColor("#104bff"), Color.parseColor("#67cef6")),
            new Pair<>(Color.parseColor("#ff5d9b"), Color.parseColor("#ffaa69"))
    };
    int minValue = 30;
    int[] raters = new int[]{
            minValue + new Random().nextInt(100 - minValue + 1),
            minValue + new Random().nextInt(100 - minValue + 1),
            minValue + new Random().nextInt(100 - minValue + 1),
            minValue + new Random().nextInt(100 - minValue + 1),
            minValue + new Random().nextInt(100 - minValue + 1)
    };
    private FragmentGameReviewsTabBinding binding;


    public GameReviewsTabFragment() {
        super(R.layout.fragment_game_reviews_tab);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameReviewsTabBinding.inflate(inflater, container, false);
        ratingReviews = binding.ratingReviews;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.bigRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                ratingBar.setRating((float) Math.ceil(rating));
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        ratingReviews.createRatingBars(100, BarLabels.STYPE3, colors, raters);

    }

    @Override
    public void onPause() {
        super.onPause();
        ratingReviews.clearAll();
    }
}
