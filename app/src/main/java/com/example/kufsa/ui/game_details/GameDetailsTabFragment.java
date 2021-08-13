package com.example.kufsa.ui.game_details;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.kufsa.R;
import com.example.kufsa.data.BoardGame;
import com.example.kufsa.databinding.FragmentGameDetailsTabBinding;
import com.example.kufsa.ui.login.LoginFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GameDetailsTabFragment extends Fragment {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference gamesCollection = db.collection("games");
    String gameID;
    BoardGame game;
    boolean isFavorite = false;
    private FragmentGameDetailsTabBinding binding;
    //Menu menu;

    public GameDetailsTabFragment() {
        super(R.layout.fragment_game_details_tab);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gameID = getArguments().getString("gameID");
        binding = FragmentGameDetailsTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gamesCollection.document(gameID).get().addOnSuccessListener(documentSnapshot -> {
            game = documentSnapshot.toObject(BoardGame.class);
            if (game == null) {
                Toast.makeText(requireContext(), "error: game not found", Toast.LENGTH_LONG).show();
                return;
            }
            game.setId(documentSnapshot.getId());
            setUI(view);
        }).addOnFailureListener(e -> Toast.makeText(requireContext(), "error: game not found", Toast.LENGTH_LONG).show());


        setFavoriteButton();
        binding.favoriteFabButton.setOnClickListener(view1 -> {
            if (isFavorite) {
                removeFromFavorites();
            } else {
                addToFavorites();
            }
        });
    }

    private void setFavoriteButton() {
        if (auth.getCurrentUser() != null) {
            DocumentReference favoriteGame = db.collection("users").document(auth.getUid()).collection("favorites").document(gameID);
            favoriteGame.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    isFavorite = true;
                    binding.favoriteFabButton.setRippleColor(getResources().getColor(R.color.fui_transparent));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.favoriteFabButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    }
                }
            });
        }
    }

    private void setUI(View view) {
        String description = "Description";
        String releaseYear = "Release Year: " + game.getReleaseYear();
        String publisher = "Publisher: " + game.getPublisher();
        String Age = "Age: " + game.getMinAge() + "+";
        String NumOfPlayers = "Number of players: " + game.getMinNumOfPlayers() + "-" + game.getMaxNumOfPlayers();
        String gameDetailsPlayingTime = "Playing time: " + game.getPlayingTime() + "+";
        String difficulty = "Difficulty: " + game.getDifficulty() + "/5";

        binding.gameDetailsTitle.setText(game.getName());
        binding.gameDetailsDescriptionHeadline.setText(description);
        binding.gameDetailsDescription.setText(game.getDescription());
        binding.gameDetailsReleaseYear.setText(releaseYear);
        binding.gameDetailsPublisher.setText(publisher);
        binding.gameDetailsAge.setText(Age);
        binding.gameDetailsNumOfPlayers.setText(NumOfPlayers);
        binding.gameDetailsPlayingTime.setText(gameDetailsPlayingTime);
        binding.gameDetailsDifficulty.setText(difficulty);

        Glide.with(view)
                .load(game.getImg())
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(binding.gameDetailsImageView);
        binding.gameDetailsProgressBar.setVisibility(View.GONE);
        binding.detailsContainerLayout.setVisibility(View.VISIBLE);
    }

    private void removeFromFavorites() {
        if (auth.getCurrentUser() != null) {
            String userID = auth.getUid();
            DocumentReference favoritesRef = db.collection("users").document(userID).collection("favorites").document(gameID);
            favoritesRef.delete()
                    .addOnSuccessListener(unused -> {
                        isFavorite = false;
                        binding.favoriteFabButton.setRippleColor(getResources().getColor(R.color.colorAccent));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            binding.favoriteFabButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                        }

                        Toast.makeText(requireContext(), "removed from favorites", Toast.LENGTH_LONG).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    });
        } else {
            Toast.makeText(requireContext(), "error: you are not sign in", Toast.LENGTH_LONG).show();
        }
    }

    private void addToFavorites() {
        if (auth.getCurrentUser() != null) {
            String userID = auth.getUid();
            DocumentReference favoritesRef = db.collection("users").document(userID).collection("favorites").document(game.getId());
            favoritesRef.get().addOnSuccessListener(documentSnapshot -> {
                if (!documentSnapshot.exists()) {
                    Map<String, Object> favoriteGame = new HashMap<>();
                    favoriteGame.put("name", game.getName());
                    favoriteGame.put("img", game.getImg());
                    favoritesRef.set(favoriteGame)
                            .addOnSuccessListener(unused -> {
                                isFavorite = true;
                                binding.favoriteFabButton.setRippleColor(getResources().getColor(R.color.fui_transparent));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    binding.favoriteFabButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                                }
                                Toast.makeText(requireContext(), "added to favorites", Toast.LENGTH_LONG).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_LONG).show();
                            });
                }
            });
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(LoginFragment.providers)
                            .setLogo(R.drawable.logo)
                            .setTheme(R.style.Theme_purple_firebase)
                            .build(),
                    LoginFragment.RC_SIGN_IN);
            //  Toast.makeText(requireContext(), "please sign in first", Toast.LENGTH_LONG).show();
        }
    }
}

