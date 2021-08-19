package com.example.kufsa.ui.account_settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Objects;

/**
 * Fragment that initializes an account settings page in the app.
 */
public class AccountSettingsFragment extends Fragment {

    // Creating an auth listener for our Firebase auth
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    // A user's email is retreived from firebase, otherwise empty
    String email = user != null ? user.getEmail() : null;
    private FragmentAccountSettingsBinding binding;

    /**
     * This method initializes the layout for the page from an XML file.
     */
    public AccountSettingsFragment() {
        super(R.layout.fragment_account_settings);
    }

    /**
     * @param inflater           Instantiates a layout XML file into its corresponding View objects.
     * @param container          special view that can contain child views.
     * @param savedInstanceState mapping for parcel values.
     * @return outermost view.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * @param view               The view used, in this case account settings.
     * @param savedInstanceState mapping for parcel values.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setting up all the page's functionality
        setUpPasswordReset();
        setUpDarkMode();
        setUpDeleteAccount();
        setUpReport();
    }

    /**
     * This method sets up a password reset button.
     * The button will use firebase API to send a password reset mail
     */
    private void setUpPasswordReset() {
        // Reset password settings
        Objects.requireNonNull(user).updatePassword("1234");
        binding.resetPasswordButton.setOnClickListener(view1 -> auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    // Updating password in case you logged in by gmail but don't have a password...then the reset will work

                    if (!(task.isSuccessful()) && (auth.getCurrentUser().getPhoneNumber() != "")) {
                        Toast.makeText(getActivity(), getString(R.string.error_login_by_phone), Toast.LENGTH_SHORT).show();
                    }
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), getString(R.string.password_reset_email_sent), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }));

    }

    /**
     * This method sets up a button that toggles light and dark mode for the app's design.
     * We use shared preferences and appcompat API to switch between set colors and preferences.
     */
    private void setUpDarkMode() {
        // Dark mode settings
        // Saving state of our app
        // using SharedPreferences
        SharedPreferences sharedPreferences
                = this.requireContext().getSharedPreferences(
                getString(R.string.shared_prefs), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        getString(R.string.is_dark_mode_on), false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        } else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
        }

        binding.toggleDarkButton.setOnClickListener(view1 -> {
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
                        getString(R.string.is_dark_mode_on), false);
                editor.apply();
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
                        getString(R.string.is_dark_mode_on), true);
                editor.apply();
            }
        });
    }

    /**
     * This method sets up a button that deletes a user's account from the app and firebase DB.
     * We use Firebase API To simply delete the user from the DB.
     */
    private void setUpDeleteAccount() {
        //report form button features
        binding.deleteButton.setOnClickListener(view12 -> {
            // Delete user and navigate back
            Objects.requireNonNull(auth.getCurrentUser()).delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("Delete account", "Deletion Success");
                }
            });
            auth.signOut();
            Toast.makeText(getActivity(), getString(R.string.account_deleted), Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this).navigate(R.id.action_AccountSettingsFragment_to_marketplace_fragment);
        });

    }

    /**
     * This method sets up button that switches to a complaint report.
     * The button will simply switch to the SendReportFragment.
     */
    private void setUpReport() {
        //report form button features
        binding.reportButton.setOnClickListener(view12 -> {
            // Contact developers
            NavHostFragment.findNavController(this).navigate(R.id.action_AccountSettingsFragment_to_SendReportFragment);
        });

    }
}