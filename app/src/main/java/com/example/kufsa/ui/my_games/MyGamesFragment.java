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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class MyGamesFragment extends Fragment {


    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        if (auth.getCurrentUser() != null) {
            String userID = auth.getUid();
            Query query =
                    db.collection("users").document(userID).collection("favorites").orderBy("name");
            FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                    .setQuery(query, BoardGame.class)
                    .setLifecycleOwner(this.getViewLifecycleOwner())
                    .build();
            adapter = new FavoritesAdapter(options);

            adapter.setOnItemClickListener((documentSnapshot, position) -> {
                // BoardGame game = documentSnapshot.toObject(BoardGame.class);
                String id = documentSnapshot.getId();
                NavHostFragment.findNavController(this).navigate(MyGamesFragmentDirections.actionMyGamesFragmentToGameDetailsFragment(id));
            });

            binding.favoritesRecyclerView.setHasFixedSize(true);
            binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
            binding.favoritesRecyclerView.setAdapter(adapter);


        }
    }
}


