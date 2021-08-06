package com.example.kufsa.ui.login;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.kufsa.R;

public class AccountSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}