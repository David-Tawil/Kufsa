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
import com.example.kufsa.data.MarketAd;
import com.example.kufsa.databinding.FragmentMyGamesBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This fragment instantiates the my games details page in the app.
 */
public class MyGamesFragment extends Fragment {
    private static final String TAG = "MyGamesFragment";

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FavoritesAdapter adapter;
    private FragmentMyGamesBinding binding;

    /**
     * This method initializes the layout for the page from an XML file.
     */
    public MyGamesFragment() {
        super(R.layout.fragment_my_games);
    }


    /**
     * This method handles the graphics part of the fragment
     *
     * @param inflater           Instantiates a layout XML file into its corresponding View objects.
     * @param container          special view that can contain child views.
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     * @return outermost view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyGamesBinding.inflate(inflater, container, false);
        setUpRecyclerView();
        setUpSearchBar();
        setUpAdsRecyclerView();

        return binding.getRoot();
    }

    /**
     * Set up the recyclerview for user active ads in market
     */
    private void setUpAdsRecyclerView() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userID = user.getUid();
            List<String> listingsIds = new ArrayList<>();
            listingsIds.add("");// firestore requires not empty list for query
            db.collection("users").document(userID).collection("userListing").get().addOnSuccessListener(queryDocumentSnapshots -> {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    listingsIds.add(documentSnapshot.getId());
                }

                Query query = db.collectionGroup("listing").whereIn("id", listingsIds).orderBy("publishDate", Query.Direction.DESCENDING);
                // check if user has no active ads in market and show info label accordingly
                query.addSnapshotListener((value, error) -> {
                    if (value != null && value.isEmpty())
                        binding.noAdsInMarketText.setVisibility(View.VISIBLE);
                    else
                        binding.noAdsInMarketText.setVisibility(View.GONE);
                });

                FirestoreRecyclerOptions<MarketAd> options = new FirestoreRecyclerOptions.Builder<MarketAd>()
                        .setQuery(query, MarketAd.class)
                        .setLifecycleOwner(this.getViewLifecycleOwner())
                        .build();
                MyAdsAdapter AdsAdapter = new MyAdsAdapter(options);
                // Recycler view preferences
                binding.adsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.adsRecyclerView.setAdapter(AdsAdapter);
            });
        } else {
            binding.noAdsInMarketText.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Set up the recyclerview for favorite games
     */
    private void setUpRecyclerView() {

        if (auth.getCurrentUser() != null) {
            String userID = auth.getUid();
            Query query =
                    db.collection("users").document(Objects.requireNonNull(userID)).collection("favorites").orderBy("name");
            // check if user have no favorite games and show info label
            query.addSnapshotListener((value, error) -> {
                if (value != null && value.isEmpty()) {
                    binding.noGamesInFavoritesText.setVisibility(View.VISIBLE);
                }
            });
            FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                    .setQuery(query, BoardGame.class)
                    .setLifecycleOwner(this.getViewLifecycleOwner())
                    .build();
            adapter = new FavoritesAdapter(options);
            // navigate to game details screen when item clicked
            adapter.setOnItemClickListener((documentSnapshot, position) -> {
                String id = documentSnapshot.getId();
                String gameName = documentSnapshot.getString("name");
                NavHostFragment.findNavController(this).navigate(MyGamesFragmentDirections.actionMyGamesFragmentToGameDetailsTabsContainerFragment(id, Objects.requireNonNull(gameName)));

            });
            // Recycler view preferences
            binding.favoritesRecyclerView.setHasFixedSize(true);
            binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
            binding.favoritesRecyclerView.setAdapter(adapter);
        } else {
            binding.noGamesInFavoritesText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method sets up a working search bar to find a game by typing it's name and returning a match
     * We use firebase API to query the DB, build a list of games that match said query, then build a recycler view from it
     * as the search's results.
     */
    private void setUpSearchBar() {

        // Search box
        EditText searchBox = binding.getRoot().getRootView().findViewById(R.id.my_games_searchBox);

        searchBox.addTextChangedListener(new TextWatcher() {

            /**
             * This method defines what happens before text is entered
             *
             * @param s     text input
             * @param start input at start
             * @param count count of chars entered
             * @param after count of chars after input
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            /**
             * This method defines what happens when text is changed
             *
             * @param s     text input
             * @param start input at start
             * @param count count of chars entered
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            /**
             * This method defines what happens after text is changed
             *
             * @param s text input
             */
            // query that draws from the database and shows the results of searching in the search box
            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "Search Box has changed to: " + s.toString());
                if (s.toString().isEmpty()) {
                    String userID = auth.getUid();
                    Query query =
                            db.collection("users").document(Objects.requireNonNull(userID)).collection("favorites").orderBy("name");

                    FirestoreRecyclerOptions<BoardGame> options = new FirestoreRecyclerOptions.Builder<BoardGame>()
                            .setQuery(query, BoardGame.class)
                            .setLifecycleOwner(getViewLifecycleOwner())
                            .build();
                    adapter.updateOptions(options);
                } else {
                    String userID = auth.getUid();
                    Query query =
                            db.collection("users").document(Objects.requireNonNull(userID)).collection("favorites").whereEqualTo("name", s.toString()).orderBy("name");
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

