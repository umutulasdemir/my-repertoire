package com.example.sahneovevi.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sahneovevi.Adapters.ItemTouchHelperAdapter;
import com.example.sahneovevi.Adapters.SongAdapter;
import com.example.sahneovevi.Adapters.SongAdapter2;
import com.example.sahneovevi.R;
import com.example.sahneovevi.Utility.MyItemTouchHelper;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeletedFragment extends Fragment{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TabLayout tabLayout;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_deleted, container, false);
        define();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameTabLayout2
                , new DeletedTab1Fragment()).commit();
        return view;
    }
    public void define(){
        firebaseDatabase = FirebaseDatabase.getInstance("https://sahne-sov-evi-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        tabLayout = view.findViewById(R.id.tabLayout2);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new DeletedTab1Fragment();
                        break;
                    case 1:
                        fragment = new DeletedTab2Fragment();
                        break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameTabLayout2
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
}
