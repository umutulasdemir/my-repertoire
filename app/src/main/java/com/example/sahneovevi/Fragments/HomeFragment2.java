package com.example.sahneovevi.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sahneovevi.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment2 extends Fragment{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TabLayout tabLayout;
    String lastFragment;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home2, container, false);
        define();
        fetchFilters();
        action();
        return view;
    }
    public void start(){
        if(lastFragment!=null){
            TabLayout.Tab tab = null;
            switch (lastFragment) {
                case "list1pos":
                    tab = tabLayout.getTabAt(0);
                    Log.i("tab text", tab.getText().toString());
                    break;
                case "list2pos":
                    tab = tabLayout.getTabAt(1);
                    break;
                case "list3pos":
                    tab = tabLayout.getTabAt(2);
                    break;
            }
            tab.select();
            if (tab.getText().equals("LIST-1"))getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameTabLayout
                    , new TabFragment1()).commit();
        }
    }
    public void action(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("tab position: ",tab.getPosition()+"ZA");
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new TabFragment1();
                        break;
                    case 1:
                        fragment = new TabFragment2();
                        break;
                    case 2:
                        fragment = new TabFragment3();
                        break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameTabLayout
                        , fragment).commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    public void define(){
        firebaseDatabase = FirebaseDatabase.getInstance("https://sahne-sov-evi-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        tabLayout = view.findViewById(R.id.tabLayout);
    }
    public void fetchFilters(){
        databaseReference.child("ListFilters").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    Log.i("position: ",snapshot.child("positionBy").getValue().toString());
                    lastFragment = snapshot.child("positionBy").getValue().toString();
                    start();
                }catch (NullPointerException e){}
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}



