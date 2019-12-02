package com.example.tourmate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tourmate.R;
import com.example.tourmate.fragment.HomeFragment;
import com.example.tourmate.fragment.MemoriesFragment;
import com.example.tourmate.fragment.NearbyPlacesFragment;
import com.example.tourmate.fragment.TripsFragment;
import com.example.tourmate.fragment.WalletFragment;
import com.example.tourmate.fragment.WeatherFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();//default fragment

        init();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.homeDrawerItemId:
                setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                break;
            case R.id.tripsDrawerItemId:
                setTitle("Trips");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TripsFragment()).commit();
                break;
            case R.id.memoriesDrawerItemId:
                setTitle("Memories");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MemoriesFragment()).commit();
                break;
            case R.id.walletDrawerItemId:
                setTitle("Wallet");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WalletFragment()).commit();
                break;
            case R.id.nearbyPlacesDrawerItemId:
                setTitle("Nearby Places");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NearbyPlacesFragment()).commit();
                break;
            case R.id.weatherDrawerItemId:
                setTitle("Weather");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WeatherFragment()).commit();
                break;

            case R.id.action_logout:
                logoutUser();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private void init() {
        drawerLayout = findViewById(R.id.drawerLayoutId);
        firebaseAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));

        toggle.syncState();

        navigationView = findViewById(R.id.navigationViewId);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.settingMenuItemId:
                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.aboutMenuItemId:
                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        firebaseAuth.signOut();
        startActivity(new Intent(this,LoginActivity.class));
    }
}
