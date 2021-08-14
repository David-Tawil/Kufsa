package com.example.kufsa.ui.game_details;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.kufsa.R;
import com.example.kufsa.databinding.FragmentGameReviewsTabBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import java.util.Random;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class GameReviewsTabFragment extends Fragment {

    RatingReviews ratingReviews;
    MaterialRatingBar materialRatingBar;
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
    FirebaseAuth auth = FirebaseAuth.getInstance();


    public GameReviewsTabFragment() {
        super(R.layout.fragment_game_reviews_tab);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameReviewsTabBinding.inflate(inflater, container, false);
        ratingReviews = binding.ratingReviews;
        materialRatingBar = binding.bigRatingBar;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        materialRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                float ratingRound = (float) Math.ceil(rating);
                ratingBar.setRating(ratingRound);
                showCustomDialog(view, ratingRound);
            }
        });
        binding.writeReviewButton.setOnClickListener(view1 -> showCustomDialog(view, 0));
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


    private void showCustomDialog(View view, float rating) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_add_review);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Glide.with(view)
                .load(auth.getCurrentUser().getPhotoUrl())
                .error(R.drawable.outline_account_circle_24)
                .into((ImageView) dialog.findViewById(R.id.profile_img_view));

        final EditText et_post = dialog.findViewById(R.id.et_post);
        final MaterialRatingBar rating_bar_dialog = dialog.findViewById(R.id.rating_bar);
        rating_bar_dialog.setRating(rating);
        dialog.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = et_post.getText().toString().trim();
                if (review.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill review text", Toast.LENGTH_SHORT).show();
                } else {

                }
                dialog.dismiss();
                Toast.makeText(requireContext(), "Submitted", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
