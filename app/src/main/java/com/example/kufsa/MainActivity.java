package com.example.kufsa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class  MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.;
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.marketplace_fragment, R.id.my_games_fragment, R.id.my_account_fragment, R.id.signedInAccountFragment)
                .setDrawerLayout(drawer)
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