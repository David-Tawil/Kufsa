/*
  @authors Aaron David Tawil & Eldar Weiss
*/

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

/**
 * This fragment instantiates the signed in fragment used in the app
 */
public class SignedInAccountFragment extends Fragment {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String displayName;
    private FragmentSignedInAccountBinding binding;

    /**
     * This method initializes the layout for the page from an XML file.
     */
    public SignedInAccountFragment() {
        super(R.layout.fragment_signed_in_account);
    }


    /**
     * @param inflater           Instantiates a layout XML file into its corresponding View objects.
     * @param container          special view that can contain child views.
     * @param savedInstanceState A mapping from String keys to various Parcelable values..
     * @return outermost view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignedInAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpLogin(view);
    }

    /**
     * This method sets up the funcionality of all the buttons shown in the signed in page
     *
     * @param view the view used for entering login
     */
    private void setUpLogin(View view) {
        //checked if not logged in
        if (auth.getCurrentUser() != null) {
            String helloText = getString(R.string.hi) + auth.getCurrentUser().getDisplayName();
            binding.welcomeTextView.setText(helloText);
            Glide.with(view)
                    .load(auth.getCurrentUser().getPhotoUrl())
                    .error(R.drawable.outline_account_circle_24)
                    .into(binding.profileImgView);
        } else {
            Toast.makeText(requireContext(), getString(R.string.error_user_not_signed_in), Toast.LENGTH_LONG).show();
        }
        binding.accountSettingsButton.setOnClickListener(THIS -> NavHostFragment.findNavController(this).navigate(R.id.action_SignedInAccountFragment_to_AccountSettingsFragment));


        binding.signOutButton.setOnClickListener(view1 -> AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener(task -> {
                    Snackbar.make(requireView(), getString(R.string.you_have_signed_in), Snackbar.LENGTH_LONG).show();
                    NavHostFragment.findNavController(this).navigate(SignedInAccountFragmentDirections.actionSignedInAccountFragmentToMyAccountFragment());
                }));
        setUpDisplayNameInSidebar();
    }

    /**
     * This method makes sure the user's name will be display in the nav bar after logging in
     */
    private void setUpDisplayNameInSidebar() {
        // set up display name in side bar
        NavigationView navView = binding.getRoot().getRootView().findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        TextView navDisplayName = headerView.findViewById(R.id.EmailView);
        // Show current displayname, empty otherwise
        if (auth.getCurrentUser() == null)
            displayName = "";
        else
            displayName = auth.getCurrentUser().getDisplayName();
        navDisplayName.setText(displayName);
    }
}

