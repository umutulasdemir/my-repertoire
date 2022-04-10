package com.example.sahneovevi.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sahneovevi.Adapters.SongAdapter;
import com.example.sahneovevi.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class HomeFragment extends Fragment{

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> songList;
    LinearLayout layoutSearch;
    EditText editText;
    ViewPager2 viewPager2;
    SongAdapter songAdapter;
    int currentPagerPos;
    String lastFragment = "list3pos";
    String filter1;
    String filter2;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        define();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editText.setCursorVisible(false);
                hideKeyboard();
                return true;
            }
        });
        fetchFilters();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setCursorVisible(true);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Bundle bundle = new Bundle();
                Log.i("text changed: ",editText.getText().toString());
                bundle.putString("starter",editText.getText().toString());
                bundle.putStringArrayList("songList", songList);
                Fragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);
                FragmentTransaction transaction;
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragmentLayout,searchFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null); transaction.commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.i("SONG COUNT", songList.size()+"");
        return view;
    }
    public void define(){
        firebaseDatabase = FirebaseDatabase.getInstance("https://sahne-sov-evi-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        layoutSearch = view.findViewById(R.id.layoutSearch);
        editText = view.findViewById(R.id.editTextSearch);
        songList = new ArrayList<>();
        viewPager2 = view.findViewById(R.id.pager);
        songAdapter = new SongAdapter(songList,getActivity(),getContext());
        viewPager2.setAdapter(songAdapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        currentPagerPos = 0;
        try {
            Log.i("fetching!","fetching process"+getArguments().getInt("case1"));
            currentPagerPos = getArguments().getInt("case1");
            hideKeyboard();
        }catch (NullPointerException e){
            Log.i("no fetching","No fetching!");
        }
        Log.i("currentPos",  currentPagerPos+"");
        viewPager2.setCurrentItem(currentPagerPos, true);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        viewPager2.setCurrentItem(currentPagerPos,false);
                    }
                }, 100);
    }

    public void fetchSongs(){
        databaseReference.child("Songs").orderByChild(lastFragment).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.i("key: ",snapshot.getKey());
                if (!snapshot.child(lastFragment).getValue().toString().equals("-1")){
                    if ((filter1.equals(snapshot.child("attribute1").getValue().toString()) || filter1.equals("None")) && (filter2.equals(snapshot.child("attribute2").getValue().toString())|| filter2.equals("None"))){
                        songList.add(snapshot.getKey());
                    }
                }
                Log.i("song list size: ",songList.size()+"");
                songAdapter.notifyDataSetChanged();
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

    public void hideKeyboard() {
        View view2 = getActivity().getCurrentFocus();
        if (view2 != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view2.getWindowToken(),0);
        }
    }

    public void fetchFilters(){
        Log.i("nedn amk","s");
        databaseReference.child("ListFilters").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    Log.i("position: ",snapshot.child("positionBy").getValue().toString()+"ZAA");
                    lastFragment = snapshot.child("positionBy").getValue().toString();
                    if (lastFragment.equals("list1pos")){
                        filter1 = snapshot.child("filter1").getValue().toString();
                        filter2 = snapshot.child("filter2").getValue().toString();
                    }
                    else if (lastFragment.equals("list2pos")){
                        filter1 = snapshot.child("filter21").getValue().toString();
                        filter2 = snapshot.child("filter22").getValue().toString();
                    }
                    else if (lastFragment.equals("list3pos")){
                        filter1 = snapshot.child("filter31").getValue().toString();
                        filter2 = snapshot.child("filter32").getValue().toString();
                    }
                    fetchSongs();
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
