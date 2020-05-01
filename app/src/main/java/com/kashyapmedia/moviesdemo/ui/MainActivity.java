package com.kashyapmedia.moviesdemo.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.kashyapmedia.moviesdemo.R;
import com.kashyapmedia.moviesdemo.databinding.ActivityMainBinding;
import com.kashyapmedia.moviesdemo.ui.fav.FavouritesFragment;
import com.kashyapmedia.moviesdemo.ui.home.HomeFragment;
import com.kashyapmedia.moviesdemo.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setup();

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    private void setup(){

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.drawer_open,R.string.drawer_closed);
        binding.drawerLayout.setDrawerListener(toggle);

        binding.navView.setNavigationItemSelectedListener(this);
        openHomePage();

        // TODO: 01-05-2020
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Log.d(TAG, "backstack changed: ");
            Fragment current = getSupportFragmentManager().findFragmentById(R.id.frame);
            if (current instanceof HomeFragment) {
                binding.navView.setCheckedItem(R.id.menu_home);
            } else if (current instanceof FavouritesFragment) {
                binding.navView.setCheckedItem(R.id.menu_favourite);
            } else if (current instanceof ProfileFragment) {
                binding.navView.setCheckedItem(R.id.menu_profile);
            }else{
                binding.navView.setCheckedItem(R.id.menu_home);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }
    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:{
                openHomePage();
                break;
            }
            case R.id.menu_favourite:{
                openFavPage();
                break;
            }
            case R.id.menu_profile:{
                openProfilePage();
                break;
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openHomePage(){
        binding.navView.setCheckedItem(R.id.menu_home);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,new HomeFragment())
                .commit();
    }
    private void openFavPage(){
        binding.navView.setCheckedItem(R.id.menu_favourite);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(FavouritesFragment.class.getName())
                .add(R.id.frame,new FavouritesFragment())
                .commit();
    }
    private void openProfilePage(){
        binding.navView.setCheckedItem(R.id.menu_profile);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(ProfileFragment.class.getName())
                .replace(R.id.frame,new ProfileFragment())
                .commit();
    }
}