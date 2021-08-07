package com.example.kufsa.ui.game_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.kufsa.R;

public class GameReviewsTabFragment extends Fragment {

    public GameReviewsTabFragment() {
        super(R.layout.fragment_game_reviews_tab);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_reviews_tab, container, false);
    }
}
