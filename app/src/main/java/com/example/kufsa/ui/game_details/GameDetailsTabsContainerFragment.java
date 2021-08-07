package com.example.kufsa.ui.game_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kufsa.R;
import com.example.kufsa.databinding.FragmentGameDetailsTabsContainerBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class GameDetailsTabsContainerFragment extends Fragment {
    //FirebaseAuth auth = FirebaseAuth.getInstance();
    //  FirebaseFirestore db = FirebaseFirestore.getInstance();
    //CollectionReference gamesCollection = db.collection("games");
    private static final int[] tabsText = {R.string.details, R.string.marketplace, R.string.reviews};
    //  Menu menu;
    //  String gameName;
    String gameID;
    FragmentGameDetailsTabsContainerBinding biding;
    ViewPager2 viewPager;


    public GameDetailsTabsContainerFragment() {
        super(R.layout.fragment_game_details_tabs_container);
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        biding = FragmentGameDetailsTabsContainerBinding.inflate(inflater, container, false);
        gameID = GameDetailsTabsContainerFragmentArgs.fromBundle(getArguments()).getGameID();
        //  gameName = GameDetailsTabsContainerFragmentArgs.fromBundle(getArguments()).getGameTitle();
        //setHasOptionsMenu(true);
        return biding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = biding.pager;
        TabLayout tabLayout = biding.tabLayout;
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @NotNull
            @Override
            public Fragment createFragment(int position) {
                Bundle bundle = new Bundle();
                //bundle.putParcelable("ggg",new BoardGame());
                bundle.putString("gameID", gameID);
                switch (position) {
                    case 0:
                        Fragment detailsTabFragment = new GameDetailsTabFragment();
                        detailsTabFragment.setArguments(bundle);
                        return detailsTabFragment;
                    case 1: {
                        return new GameMarketplaceTabFragment();
                    }
                    case 2:
                        return new GameReviewsTabFragment();
                }
                return new GameReviewsTabFragment();
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabsText[position])).attach();
    }


   /* @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        inflater.inflate(R.menu.game_details_menu, menu);
        CheckBox checkBox = (CheckBox) menu.findItem(R.id.favorite_menu_item).getActionView();
        checkBox.setButtonDrawable(R.drawable.favorite_checkbox);//set the icon


        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                removeFromFavorites();
            } else {
                addToFavorites();
            }
        });

        if (auth.getCurrentUser() != null) {
            DocumentReference favoriteGame = db.collection("users").document(auth.getUid()).collection("favorites").document(gameID);
            favoriteGame.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) checkBox.setChecked(true);
            });
        }
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
            DocumentReference favoritesRef = db.collection("users").document(userID).collection("favorites").document(gameID);
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
            DocumentReference favoritesRef = db.collection("users").document(userID).collection("favorites").document(gameID);
            favoritesRef.get().addOnSuccessListener(documentSnapshot -> {
                if (!documentSnapshot.exists()) {
                    Map<String, Object> favoriteGame = new HashMap<>();
                    favoriteGame.put("name", gameName);
                    favoriteGame.put("img", gameID);
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
        }*/
}

