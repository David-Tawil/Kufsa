package com.example.kufsa.ui.catalog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


public class CatalogFragment extends Fragment {

    private static final String TAG = "FirestoreSearchActivity";
    private static final CollectionReference gamesCollection =
            FirebaseFirestore.getInstance().collection("games");
    Menu menu;
    private CatalogAdapter adapter;
    private FragmentGameCatalogBinding binding;

    public CatalogFragment() {
        super(R.layout.fragment_game_catalog);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            String gameTitle = documentSnapshot.getString("name");
            if (gameTitle != null) {
                NavHostFragment.findNavController(this).navigate(CatalogFragmentDirections.actionMarketplaceFragmentToGameDetailsTabsContainerFragment(id, gameTitle));
            }

        });

        binding.catalogRecyclerView.setHasFixedSize(true);
        binding.catalogRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.catalogRecyclerView.setAdapter(adapter);
        binding.catalogRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.catalogRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

    }

    private void setUpSearchBar() {

        // Search box
        EditText searchBox = binding.getRoot().getRootView().findViewById(R.id.catalog_searchBox);

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
                Log.d(TAG, "SearchBox has changed to: " + s.toString());
                if (s.toString().isEmpty()) {
                    Query query = gamesCollection
                            .orderBy("name");
                    boardGameQueryFilterCall(query);
                } else {
                    if (s.toString().equals(s.toString().toLowerCase())) {
                        Query query = gamesCollection.whereEqualTo("name_lowercase", s.toString()).orderBy("name_lowercase");
                        boardGameQueryFilterCall(query);
                    } else if (s.toString().equals(s.toString().toUpperCase())) {
                        Query query = gamesCollection.whereEqualTo("name_uppercase", s.toString()).orderBy("name_uppercase");
                        boardGameQueryFilterCall(query);
                    } else {
                        Query query = gamesCollection.whereEqualTo("name", s.toString()).orderBy("name");
                        boardGameQueryFilterCall(query);
                    }

                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_aToZ) {// Sort from A to Z
            boardGameQueryCall("name", Query.Direction.ASCENDING);
        } else if (itemId == R.id.menu_ZtoA) {// Sort from Z to A
            boardGameQueryCall("name", Query.Direction.DESCENDING);
        } else if (itemId == R.id.menu_diff_asc) {// Sort the game's difficulty ascending
            boardGameQueryCall("difficulty", Query.Direction.ASCENDING);
        } else if (itemId == R.id.menu_diff_desc) {// Sort the game's difficulty descending
            boardGameQueryCall("difficulty", Query.Direction.DESCENDING);
        } else if (itemId == R.id.menu_play_time_asc) {// Sort the game's playing time ascending
            boardGameQueryCall("playingTime", Query.Direction.ASCENDING);
        } else if (itemId == R.id.menu_play_time_desc) {// Sort the game's playing time descending
            boardGameQueryCall("playingTime", Query.Direction.DESCENDING);
        } else if (itemId == R.id.menu_year_asc) {// Sort the game's release year ascending
            boardGameQueryCall("releaseYear", Query.Direction.ASCENDING);
        } else if (itemId == R.id.menu_year_desc) {// Sort the game's release year descending
            boardGameQueryCall("releaseYear", Query.Direction.DESCENDING);
        } else if (itemId == R.id.menu_min_age_asc) {// Sort the game's age requirement asecnding
            boardGameQueryCall("minAge", Query.Direction.ASCENDING);
        } else if (itemId == R.id.menu_min_age_desc) {/// Sort the game's age requirement descending
            boardGameQueryCall("minAge", Query.Direction.DESCENDING);
        }
        return super.onOptionsItemSelected(item);
    }

    private void boardGameQueryCall(String field, Query.Direction direction) {
        Query query = gamesCollection.orderBy(field, direction);
        boardGameQueryFilterCall(query);
    }

    private void boardGameQueryFilterCall(Query query) {
        FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                .setQuery(query, BoardGame.class)
                .setLifecycleOwner(getViewLifecycleOwner())
                .build();
        adapter.updateOptions(options);
    }

}
