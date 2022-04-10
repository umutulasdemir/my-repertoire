package com.example.sahneovevi.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sahneovevi.Fragments.DeletedFragment;
import com.example.sahneovevi.Fragments.HomeFragment;
import com.example.sahneovevi.Fragments.HomeFragment2;
import com.example.sahneovevi.Fragments.AddSongFragment;
import com.example.sahneovevi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    BottomNavigationView navigationView;
    EditText editText;
    ImageButton fragmentChangeButton;
    ImageButton fragmentChangeButton2;
    int currentPagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        define();
        action();
        // Fragment change process.
        navigationView = findViewById(R.id.navBottom);
        navigationView.setOnNavigationItemSelectedListener(navListener);

        Bundle bundle = new Bundle();
        bundle.putInt("case1",currentPagePosition);
        Fragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout
                , homeFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {

                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_notifications:
                    selectedFragment = new AddSongFragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout
                    , selectedFragment).commit();

            return true;
        }
    };

    public void define(){
        editText = findViewById(R.id.editTextSearch);
        fragmentChangeButton = findViewById(R.id.fragmentChangeButton);
        fragmentChangeButton2 = findViewById(R.id.fragmentChangeButton2);
        currentPagePosition = 0;
        try {
            Log.i("fetching!","fetching process!");
            currentPagePosition = getIntent().getIntExtra("pos",0);
        }catch (NullPointerException e){
            Log.i("no fetching!","no fetching!");
        }
    }
    public void action(){
        fragmentChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout
                        , new HomeFragment2()).commit();
            }
        });
        fragmentChangeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
    }

    // For the current user,
    // Take the online/offline status.
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public void showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.item_menu2);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.deletedMenuItem1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout
                        , new DeletedFragment()).commit();
                return true;
            default:
                return false;
        }
    }
}