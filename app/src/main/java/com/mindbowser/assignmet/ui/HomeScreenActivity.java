package com.mindbowser.assignmet.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mindbowser.assignmet.R;
import com.mindbowser.assignmet.databinding.ActivityMainBinding;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    FragmentManager fragmentManager;
    private String Tag = "MainAct";
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        requestContactPermission();
    }

    @Override
    public void onBackPressed() {
        int backstack = getSupportFragmentManager().getBackStackEntryCount();
        Constants.log("MainActivity", "backstack-" + backstack);
        if (backstack == 1) {
            binding.buttonLayout.setVisibility(View.VISIBLE);
            binding.frame.setVisibility(View.GONE);
        } else if (backstack == 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
            Fragment fragmentName = getSupportFragmentManager().findFragmentById(R.id.frame);
        }
    }

    private void setFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.addToBackStack("" + fragment.getClass().getSimpleName());
            Constants.log(Tag, "backstack" + "" + fragment.getClass().getSimpleName());
            ft.commit();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.contacts) {
            binding.buttonLayout.setVisibility(View.GONE);
            binding.frame.setVisibility(View.VISIBLE);
            ContactScreen contactScreen = new ContactScreen();
            setFragment(contactScreen);
        } else if (id == R.id.delete) {
            binding.buttonLayout.setVisibility(View.GONE);
            binding.frame.setVisibility(View.VISIBLE);
            DeleteScreen contactScreen = new DeleteScreen();
            setFragment(contactScreen);
        } else if (id == R.id.favourites) {
            binding.buttonLayout.setVisibility(View.GONE);
            binding.frame.setVisibility(View.VISIBLE);
            FavouritesScreen contactScreen = new FavouritesScreen();
            setFragment(contactScreen);
        }


    }

    public void requestContactPermission() {
        if (ActivityCompat.checkSelfPermission(HomeScreenActivity.this,
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(HomeScreenActivity.this, new String[]{Manifest.permission.READ_CONTACTS},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            Constants.log("permission", "" + grantResults.length);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
            }
        }

    }


}