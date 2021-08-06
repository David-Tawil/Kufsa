package com.example.kufsa.ui.login;

import android.os.Bundle;
import com.example.kufsa.R;
import androidx.preference.PreferenceFragmentCompat;

public class AccountSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}