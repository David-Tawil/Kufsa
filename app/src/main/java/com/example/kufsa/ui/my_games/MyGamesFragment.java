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
import com.example.kufsa.databinding.FragmentMyGamesBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;



public class MyGamesFragment extends Fragment {
    private static final String TAG = "MyGamesFragment";

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FavoritesAdapter adapter;
    private FragmentMyGamesBinding binding;


    public MyGamesFragment() {
        super(R.layout.fragment_my_games);
    }


    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyGamesBinding.inflate(inflater, container, false);
        setUpRecyclerView();
        setUpSearchBar();

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
                String gameName = documentSnapshot.getString("name");
                NavHostFragment.findNavController(this).navigate(MyGamesFragmentDirections.actionMyGamesFragmentToGameDetailsTabsContainerFragment(id, gameName));

            });

            binding.favoritesRecyclerView.setHasFixedSize(true);
            binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
            binding.favoritesRecyclerView.setAdapter(adapter);
        }
    }

    private void setUpSearchBar() {

        // Search box
        EditText searchBox = binding.getRoot().getRootView().findViewById(R.id.my_games_searchbox);

        searchBox.addTextChangedListener(new TextWatcher() {
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
                    String userID = auth.getUid();
                    Query query =
                            db.collection("users").document(userID).collection("favorites").orderBy("name");

                    FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                            .setQuery(query, BoardGame.class)
                            .setLifecycleOwner(getViewLifecycleOwner())
                            .build();
                    adapter.updateOptions(options);
                } else {
                    String userID = auth.getUid();
                    Query query =
                            db.collection("users").document(userID).collection("favorites").whereEqualTo("name", s.toString()).orderBy("name");
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

