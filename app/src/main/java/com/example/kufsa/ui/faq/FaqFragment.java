package com.example.kufsa.ui.faq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kufsa.R;

/**
 * This Fragment defines the app's page of Frequently asked questions by instantiating the created XML layout.
 */
public class FaqFragment extends Fragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_faq, container, false);

    }
}