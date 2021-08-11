package com.example.kufsa.ui.account_settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.kufsa.R;
import com.example.kufsa.databinding.FragmentAccountSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class AccountSettingsFragment extends Fragment {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user != null ? user.getEmail() : null;
    // creating an auth listener for our Firebase auth
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private FragmentAccountSettingsBinding binding;

    public AccountSettingsFragment() {
        super(R.layout.fragment_account_settings);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpPasswordReset();
        setUpDarkMode();
        setUpReport();
    }

    private void setUpPasswordReset() {
        // Reset password settings
        if (binding.resetPasswordButton != null) {
            binding.resetPasswordButton.setOnClickListener(view1 -> FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Password Reset Email Sent!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }));
        }
    }

    private void setUpDarkMode() {
        // Dark mode settings
        // Saving state of our app
        // using SharedPreferences
        SharedPreferences sharedPreferences
                = this.requireContext().getSharedPreferences(
                "sharedPrefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        if (binding.toggleDarkButton != null) {
            binding.toggleDarkButton.setOnClickListener(view12 -> {
                // When user taps the enable/disable
                // dark mode button
                if (isDarkModeOn) {

                    // if dark mode is on it
                    // will turn it off
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_NO);
                    // it will set isDarkModeOn
                    // boolean to false
                    editor.putBoolean(
                            "isDarkModeOn", false);
                    editor.apply();

                    // change text of Button
                    binding.darkModeDescription.setText(
                            getString(R.string.enable_dark_mode));
                } else {

                    // if dark mode is off
                    // it will turn it on
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_YES);

                    // it will set isDarkModeOn
                    // boolean to true
                    editor.putBoolean(
                            "isDarkModeOn", true);
                    editor.apply();

                    // change text of Button
                    binding.darkModeDescription.setText(
                            getString(R.string.disable_dark_mode));
                }
            });
        }
    }

    private void setUpReport() {
        //report form button features
        if (binding.reportButton != null) {
            binding.reportButton.setOnClickListener(view12 -> {
                // Contact developers
                Toast.makeText(getActivity(), "Please send your complaint to: eldar101@gmail.com", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).navigate(R.id.action_AccountSettingsFragment_to_SendReportFragment);
            });
        }

    }
}