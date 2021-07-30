package com.example.kufsa.ui.my_games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kufsa.R;
import com.example.kufsa.data.BoardGame;
import com.example.kufsa.databinding.MyGamesLayoutBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class MyGamesFragment extends Fragment {

    private static final CollectionReference gamesCollection =
            FirebaseFirestore.getInstance().collection("games");


    private FavoritesAdapter adapter;
    private MyGamesLayoutBinding binding;


    public MyGamesFragment() {
        super(R.layout.my_games_layout);
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = MyGamesLayoutBinding.inflate(inflater, container, false);
        setUpRecyclerView();

        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        Query query =
                gamesCollection.orderBy("name");
        // Configure recycler adapter options:
        //  options instructs the adapter to convert each DocumentSnapshot to a BoardGame object
        FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                .setQuery(query, BoardGame.class)
                .setLifecycleOwner(this.getViewLifecycleOwner())
                .build();
        adapter = new FavoritesAdapter(options);

        adapter.setOnItemClickListener((documentSnapshot, position) -> {
            BoardGame game = documentSnapshot.toObject(BoardGame.class);
            String id = documentSnapshot.getId();
            if (game == null) {
                Snackbar.make(requireView(), "error: game is null", Snackbar.LENGTH_LONG).show();
                return;
            }
            game.setId(id);
            NavHostFragment.findNavController(this).navigate(MyGamesFragmentDirections.actionMyGamesFragmentToGameDetailsFragment(game));
        });

        binding.favoritesRecyclerView.setHasFixedSize(true);
        binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.favoritesRecyclerView.setAdapter(adapter);
    }

}


