package com.example.kufsa.ui.game_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kufsa.R;
import com.example.kufsa.databinding.FragmentGameMarketplaceTabBinding;

/**
 * This fragment instantiates the game marketplace tab
 */
public class GameMarketplaceTabFragment extends Fragment {

    FragmentGameMarketplaceTabBinding binding;

    /**
     * This method initializes the layout for the page from an XML file.
     */
    public GameMarketplaceTabFragment() {
        super(R.layout.fragment_game_marketplace_tab);
    }

    /**
     * @param inflater           Instantiates a layout XML file into its corresponding View objects.
     * @param container          special view that can contain child views.
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     * @return outermost view.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameMarketplaceTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
