package com.example.kufsa.ui.my_games;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class MyGamesFragment extends Fragment {

    private static final CollectionReference gamesCollection =
            FirebaseFirestore.getInstance().collection("games");

    private static final String TAG = "FirestoreSearchActivity";
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
            // BoardGame game = documentSnapshot.toObject(BoardGame.class);
            String id = documentSnapshot.getId();
           /* if (game == null) {
                Snackbar.make(requireView(), "error: game is null", Snackbar.LENGTH_LONG).show();
                return;
            }
            game.setId(id);*/
            NavHostFragment.findNavController(this).navigate(MyGamesFragmentDirections.actionMyGamesFragmentToGameDetailsFragment(id));
        });

        binding.favoritesRecyclerView.setHasFixedSize(true);
        binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.favoritesRecyclerView.setAdapter(adapter);

        // Search box
        EditText searchbox = binding.getRoot().getRootView().findViewById(R.id.my_games_searchbox);

        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // query that draws from the database and shows the results of searching in the search box
            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "Searchbox has changed to: " + s.toString());
                if (s.toString().isEmpty()) {
                    Query query = gamesCollection
                            .orderBy("name", Query.Direction.DESCENDING);
                    FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                            .setQuery(query, BoardGame.class)
                            .setLifecycleOwner(getViewLifecycleOwner())
                            .build();
                    adapter.updateOptions(options);
                } else {
                    Query query =
                            gamesCollection.whereEqualTo("name", s.toString()).orderBy("name");
                    FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                            .setQuery(query, BoardGame.class)
                            .setLifecycleOwner(getViewLifecycleOwner())
                            .build();
                    adapter.updateOptions(options);
                }
            }
        });
    }

}


