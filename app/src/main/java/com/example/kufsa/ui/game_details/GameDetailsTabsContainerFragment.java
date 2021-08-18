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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        biding = FragmentGameDetailsTabsContainerBinding.inflate(inflater, container, false);
        gameID = GameDetailsTabsContainerFragmentArgs.fromBundle(getArguments()).getGameID();
        //  gameName = GameDetailsTabsContainerFragmentArgs.fromBundle(getArguments()).getGameTitle();
        //setHasOptionsMenu(true);
        return biding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = biding.pager;
        TabLayout tabLayout = biding.tabLayout;
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull

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
                        Fragment reviewTabFragment = new GameReviewsTabFragment();
                        reviewTabFragment.setArguments(bundle);
                        return reviewTabFragment;
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
}

