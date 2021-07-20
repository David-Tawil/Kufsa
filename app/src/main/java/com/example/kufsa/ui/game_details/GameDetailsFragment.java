package com.example.kufsa.ui.game_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.kufsa.R;
import com.example.kufsa.data.BoardGame;
import com.example.kufsa.databinding.FragmentGameDetailsBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;


public class GameDetailsFragment extends Fragment {
    private final CollectionReference gamesCollection = FirebaseFirestore.getInstance().collection("games");
    private FragmentGameDetailsBinding binding;


    public GameDetailsFragment() {
        super(R.layout.fragment_game_details);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BoardGame game = GameDetailsFragmentArgs.fromBundle(getArguments()).getGame();

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
    }
}