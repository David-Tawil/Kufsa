package com.example.kufsa.ui.catalog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kufsa.R;
import com.example.kufsa.data.BoardGame;
import com.example.kufsa.databinding.FragmentGameCatalogBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class CatalogFragment extends Fragment {

    private static final CollectionReference gamesCollection =
            FirebaseFirestore.getInstance().collection("games");
    private CatalogAdapter adapter;
    private FragmentGameCatalogBinding binding;


    public CatalogFragment() {
        super(R.layout.fragment_game_catalog);
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentGameCatalogBinding.inflate(inflater, container, false);
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
        adapter = new CatalogAdapter(options);

        adapter.setOnItemClickListener((documentSnapshot, position) -> {
            BoardGame game = documentSnapshot.toObject(BoardGame.class);
            String id = documentSnapshot.getId();
            if (game == null) {
                Snackbar.make(requireView(), "error: game is null", Snackbar.LENGTH_LONG).show();
                return;
            }
            game.setId(id);
            NavHostFragment.findNavController(this).navigate(CatalogFragmentDirections.actionCatalogFragmentToGameDetailsFragment(game));
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

    }

}
