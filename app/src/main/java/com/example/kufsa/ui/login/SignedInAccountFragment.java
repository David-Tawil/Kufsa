package com.example.kufsa.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.kufsa.R;
import com.example.kufsa.databinding.FragmentSignedInAccountBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class SignedInAccountFragment extends Fragment {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String email;
    private FragmentSignedInAccountBinding binding;
    private TextView navEmail;

    public SignedInAccountFragment() {
        super(R.layout.fragment_signed_in_account);
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentSignedInAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpLogin(view);
    }

    // Set up user account login
    private void setUpLogin(View view) {
        if (auth.getCurrentUser() != null) {
            binding.welcomeTextView.setText(getString(R.string.hi) + " " + auth.getCurrentUser().getDisplayName());
            Glide.with(view)
                    .load(auth.getCurrentUser().getPhotoUrl())
                    .error(R.drawable.outline_account_circle_24)
                    .into(binding.profileImgView);
        } else {
            Toast.makeText(requireContext(), "error: user is not signed in", Toast.LENGTH_LONG).show();
        }
        binding.accountSetingsButton.setOnClickListener(THIS -> NavHostFragment.findNavController(this).navigate(R.id.action_SignedInAccountFragment_to_AccountSettingsFragment));


        binding.signOutButton.setOnClickListener(view1 -> AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener(task -> {
                    Snackbar.make(requireView(), "You have signed out successfully, see you next time!", Snackbar.LENGTH_LONG).show();
                    NavHostFragment.findNavController(this).navigate(SignedInAccountFragmentDirections.actionSignedInAccountFragmentToMyAccountFragment());
                }));
        setUpEmailInSidebar();
    }

    private void setUpEmailInSidebar() {
        // Set up email in side bar
        NavigationView navView = binding.getRoot().getRootView().findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        navEmail = headerView.findViewById(R.id.EmailView);
        if (auth.getCurrentUser() == null)
            email = "";
        else
            email = auth.getCurrentUser().getEmail();
        navEmail.setText(email);
    }
}

