package com.example.kufsa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.kufsa.ui.catalog.CatalogAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FirestoreSearchActivity";
    private static final CollectionReference gamesCollection =
            FirebaseFirestore.getInstance().collection("games");
    private AppBarConfiguration appBarConfiguration;
    private CatalogAdapter adapter;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.;
        AppBarConfiguration.Builder builder = new AppBarConfiguration.Builder(R.id.marketplace_fragment, R.id.my_games_fragment, R.id.my_account_fragment, R.id.SignedInAccountFragment, R.id.faq_fragment);
        builder.setOpenableLayout(drawer);
        appBarConfiguration = builder
                .build();
        NavigationView navView = findViewById(R.id.nav_view);


        setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}