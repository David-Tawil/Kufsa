package com.example.kufsa.ui.game_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.kufsa.R;
import com.example.kufsa.data.BoardGame;
import com.example.kufsa.databinding.FragmentGameDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class GameDetailsFragment extends Fragment {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference gamesCollection = db.collection("games");

    private FragmentGameDetailsBinding binding;
    BoardGame game;
    Menu menu;

    public GameDetailsFragment() {
        super(R.layout.fragment_game_details);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameDetailsBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String gameID = GameDetailsFragmentArgs.fromBundle(getArguments()).getGameID();

        gamesCollection.document(gameID).get().addOnSuccessListener(documentSnapshot -> {
            game = documentSnapshot.toObject(BoardGame.class);
            if (game == null) {
                Toast.makeText(requireContext(), "error: game not found", Toast.LENGTH_LONG).show();
                return;
            }
            game.setId(documentSnapshot.getId());
            setUI(view);
        }).addOnFailureListener(e -> Toast.makeText(requireContext(), "error: game not found", Toast.LENGTH_LONG).show());
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

        CheckBox checkBox = (CheckBox) menu.findItem(R.id.favorite_menu_item).getActionView();

        if (auth.getCurrentUser() != null) {
            DocumentReference favoriteGame = db.collection("users").document(auth.getUid()).collection("favorites").document(game.getId());
            favoriteGame.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) checkBox.setChecked(true);
            });
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        inflater.inflate(R.menu.game_details_menu, menu);
        CheckBox checkBox = (CheckBox) menu.findItem(R.id.favorite_menu_item).getActionView();
        checkBox.setButtonDrawable(R.drawable.favorite_checkbox);//set the icon


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    removeFromFavorites();
                } else {
                    addToFavorites();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.favorite_menu_item) {
            //addToFavorite();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeFromFavorites() {
        if (auth.getCurrentUser() != null) {
            String userID = auth.getUid();
            DocumentReference favoritesRef = db.collection("users").document(userID).collection("favorites").document(game.getId());
            favoritesRef.delete()
                    .addOnSuccessListener(unused -> {

                        Toast.makeText(requireContext(), "removed from favorites", Toast.LENGTH_LONG).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    });
        } else {
            Toast.makeText(requireContext(), "please sign in first", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(requireContext(), "added to favorites", Toast.LENGTH_LONG).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_LONG).show();
                            });
                }
            });
        } else {
            Toast.makeText(requireContext(), "please sign in first", Toast.LENGTH_LONG).show();
        }
    }
}