package com.example.kufsa.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.kufsa.R;
import com.example.kufsa.databinding.FragmentSignedInAccountBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class signedInAccountFragment extends Fragment {
    private FragmentSignedInAccountBinding binding;

    public signedInAccountFragment() {
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
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            binding.welcomeTextView.setText("Hi, " + auth.getCurrentUser().getDisplayName());
            Glide.with(view)
                    .load(auth.getCurrentUser().getPhotoUrl())
                    .error(R.drawable.outline_account_circle_24)
                    .into(binding.profileImgView);
        } else {
            Toast.makeText(requireContext(), "error: user is not signed in", Toast.LENGTH_LONG).show();
        }
        binding.signOutButton.setOnClickListener(view1 -> AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener(task -> {
                    Snackbar.make(requireView(), "You sign out successfully, see you soon...", Snackbar.LENGTH_LONG).show();
                    NavHostFragment.findNavController(this).navigate(signedInAccountFragmentDirections.actionSignedInAccountFragmentToMyAccountFragment());
                }));
    }
}

