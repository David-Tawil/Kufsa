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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.kufsa.R;
import com.example.kufsa.data.BoardGame;
import com.example.kufsa.data.Review;
import com.example.kufsa.databinding.FragmentGameReviewsTabBinding;
import com.example.kufsa.ui.login.LoginFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

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
    //int minValue = 30;
    int[] raters = new int[]{0, 0, 0, 0, 0};

    private FragmentGameReviewsTabBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference gameRef;
    ReviewsAdapter adapter;
    String gameID;
    BoardGame game;


    public GameReviewsTabFragment() {
        super(R.layout.fragment_game_reviews_tab);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gameID = getArguments() != null ? getArguments().getString("gameID") : null;
        gameRef = db.collection("games").document(gameID);
        binding = FragmentGameReviewsTabBinding.inflate(inflater, container, false);
        ratingReviews = binding.ratingReviews;
        materialRatingBar = binding.bigRatingBar;
        setUpRecyclerView();

        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        Query query =
                gameRef.collection("reviews").orderBy("date", Query.Direction.DESCENDING);
        // Configure recycler adapter options:
        //  options instructs the adapter to convert each DocumentSnapshot to a BoardGame object
        FirestoreRecyclerOptions<Review> options = new FirestoreRecyclerOptions.Builder<Review>()
                .setQuery(query, Review.class)
                .setLifecycleOwner(this.getViewLifecycleOwner())
                .build();
        adapter = new ReviewsAdapter(options);
        binding.reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.reviewsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewGroup viewGroup = binding.gameReviewsLayout;
        viewGroup.getLayoutTransition().setAnimateParentHierarchy(false);

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
        gameRef.addSnapshotListener(this.requireActivity(), (value, error) -> {
            if (error != null) {
                Toast.makeText(requireContext(), "error: listen failed", Toast.LENGTH_LONG).show();
                return;
            }
            if (value != null && value.exists()) {
                game = value.toObject(BoardGame.class);
                setReviewsBar();
                setUi();
            }
        });
        adapter.startListening();
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

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void setUi() {
        if (game.getAverageReviewScore() == 0) {
            return;
        }
        binding.totalReviewsNumber.setText(NumberFormat.getIntegerInstance().format(game.getTotalReviews()));
        binding.averageRatingScore.setText(new DecimalFormat("#.0").format(game.getAverageReviewScore()));
        binding.smallRatingBarInfo.setRating(game.getAverageReviewScore());
        binding.ratingReviews.setVisibility(View.VISIBLE);
        binding.totalReviewsNumber.setVisibility(View.VISIBLE);
        binding.averageRatingScore.setVisibility(View.VISIBLE);
        binding.divider2.setVisibility(View.VISIBLE);
        binding.smallRatingBarInfo.setVisibility(View.VISIBLE);
        binding.averageRatingScore.setVisibility(View.VISIBLE);
        binding.noReviewsLabel.setVisibility(View.GONE);
    }

    private void setReviewsBar() {
        float totalReviews = game.getTotalReviews();
        if (totalReviews != 0)
            raters = new int[]{(int) ((game.getTotalFiveStar() / totalReviews) * 100), (int) ((game.getTotalFourStar() / totalReviews) * 100), (int) ((game.getTotalThreeStar() / totalReviews) * 100), (int) ((game.getTotalTwoStar() / totalReviews) * 100), (int) ((game.getTotalOneStar() / totalReviews) * 100)};
        ratingReviews.clearAll();
        ratingReviews.createRatingBars(100, BarLabels.STYPE3, colors, raters);

    }


    private void showCustomDialog(View view, float rating) {
        if (auth.getCurrentUser() == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(LoginFragment.providers)
                            .setLogo(R.drawable.logo)
                            .setTheme(R.style.Theme_purple_firebase)
                            .build(),
                    LoginFragment.RC_SIGN_IN);
            return;
        }

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_add_review);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Glide.with(view)
                .load(user.getPhotoUrl())
                .error(R.drawable.outline_account_circle_24)
                .into((ImageView) dialog.findViewById(R.id.profile_img_view));
        TextView userName = dialog.findViewById(R.id.user_name);
        String userNameTxt = user.getDisplayName() != null ? !user.getDisplayName().isEmpty() ? user.getDisplayName() : "anonymous" : "anonymous";
        userName.setText(userNameTxt);
        final EditText et_post = dialog.findViewById(R.id.et_post);
        final MaterialRatingBar rating_bar = dialog.findViewById(R.id.rating_bar);
        rating_bar.setRating(rating);
        dialog.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button b = dialog.findViewById(R.id.bt_submit);

        rating_bar.setOnRatingBarChangeListener((ratingBar, rating2, fromUser) -> {
            if (fromUser) {
                float ratingRound = (float) Math.ceil(rating2);
                ratingBar.setRating(ratingRound);
            }
        });

        dialog.findViewById(R.id.bt_submit).setOnClickListener(v -> {
            String reviewDesc = et_post.getText().toString().trim();
            int stars = (int) rating_bar.getRating();
            String photoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "";
            Review review = new Review(userNameTxt, photoUrl, stars, reviewDesc, new Date());
            setGameStatistics(stars, review);
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void setGameStatistics(int stars, Review review) {
        db.collection("games").document(gameID).collection("reviews").document(user.getUid())
                .get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                int oldStars = documentSnapshot.getDouble("starNum").intValue();
                removeFromTotalStars(oldStars);
                float averageExcluding;
                if ((game.getTotalReviews() - 1) == 0)
                    averageExcluding = 0;
                else
                    averageExcluding = (game.getAverageReviewScore() * game.getTotalReviews() - oldStars) / (game.getTotalReviews() - 1);
                db.collection("games").document(gameID).update("averageReviewScore", (averageExcluding * (game.getTotalReviews() - 1) + stars) / (game.getTotalReviews()));
                db.collection("games").document(gameID).collection("reviews").document(user.getUid()).set(review)
                        .addOnSuccessListener(runnable -> Toast.makeText(requireContext(), "your review was updated", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(requireContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show());

            } else {
                db.collection("games").document(gameID).update("totalReviews", game.getTotalReviews() + 1, "averageReviewScore", (game.getAverageReviewScore() * game.getTotalReviews() + stars) / (game.getTotalReviews() + 1));
                db.collection("games").document(gameID).collection("reviews").document(user.getUid()).set(review)
                        .addOnSuccessListener(runnable -> Toast.makeText(requireContext(), "Submitted", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(requireContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
            addToTotalStars(stars);
        });
    }

    private void removeFromTotalStars(int oldStars) {
        switch (oldStars) {
            case 1:
                db.collection("games").document(gameID).update("totalOneStar", game.getTotalOneStar() - 1);
                break;
            case 2:
                db.collection("games").document(gameID).update("totalTwoStar", game.getTotalTwoStar() - 1);
                break;
            case 3:
                db.collection("games").document(gameID).update("totalThreeStar", game.getTotalThreeStar() - 1);
                break;
            case 4:
                db.collection("games").document(gameID).update("totalFourStar", game.getTotalFourStar() - 1);
                break;
            case 5:
                db.collection("games").document(gameID).update("totalFiveStar", game.getTotalFiveStar() - 1);
                break;
        }
    }

    private void addToTotalStars(int stars) {
        switch (stars) {
            case 1:
                db.collection("games").document(gameID).update("totalOneStar", game.getTotalOneStar() + 1);
                break;
            case 2:
                db.collection("games").document(gameID).update("totalTwoStar", game.getTotalTwoStar() + 1);
                break;
            case 3:
                db.collection("games").document(gameID).update("totalThreeStar", game.getTotalThreeStar() + 1);
                break;
            case 4:
                db.collection("games").document(gameID).update("totalFourStar", game.getTotalFourStar() + 1);
                break;
            case 5:
                db.collection("games").document(gameID).update("totalFiveStar", game.getTotalFiveStar() + 1);
                break;
        }
    }
}