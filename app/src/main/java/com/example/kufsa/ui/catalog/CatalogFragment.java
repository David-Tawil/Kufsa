package com.example.kufsa.ui.catalog;

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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.kufsa.R;
import com.example.kufsa.data.BoardGame;
import com.example.kufsa.databinding.FragmentGameCatalogBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class CatalogFragment extends Fragment {

    private static final String TAG = "FirestoreSearchActivity";
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
        setUpSearchBar();
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
            // BoardGame game = documentSnapshot.toObject(BoardGame.class);
            String id = documentSnapshot.getId();
            /*if (id == null) {
                Snackbar.make(requireView(), "error: game is null", Snackbar.LENGTH_LONG).show();
                return;
            }*/
            // game.setId(id);
            NavHostFragment.findNavController(this).navigate(CatalogFragmentDirections.actionCatalogFragmentToGameDetailsFragment(id));
        });

        binding.catalogRecyclerView.setHasFixedSize(true);
        binding.catalogRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.catalogRecyclerView.setAdapter(adapter);
        binding.catalogRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.catalogRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

    }

    private void setUpSearchBar() {

        // Search box
        EditText searchbox = binding.getRoot().getRootView().findViewById(R.id.catalog_searchbox);

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
                            .orderBy("name");
                    FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                            .setQuery(query, BoardGame.class)
                            .setLifecycleOwner(getViewLifecycleOwner())
                            .build();
                    adapter.updateOptions(options);
                } else {
                    if (s.toString().equals(s.toString().toLowerCase())) {
                        Query query = gamesCollection.whereEqualTo("name_lowercase", s.toString()).orderBy("name_lowercase");
                        FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                                .setQuery(query, BoardGame.class)
                                .setLifecycleOwner(getViewLifecycleOwner())
                                .build();
                        adapter.updateOptions(options);
                    } else if (s.toString().equals(s.toString().toUpperCase())) {
                        Query query = gamesCollection.whereEqualTo("name_uppercase", s.toString()).orderBy("name_uppercase");
                        FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                                .setQuery(query, BoardGame.class)
                                .setLifecycleOwner(getViewLifecycleOwner())
                                .build();
                        adapter.updateOptions(options);
                    } else {
                        Query query = gamesCollection.whereEqualTo("name", s.toString()).orderBy("name");
                        FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                                .setQuery(query, BoardGame.class)
                                .setLifecycleOwner(getViewLifecycleOwner())
                                .build();
                        adapter.updateOptions(options);
                    }

                }
            }
        });
    }

}
