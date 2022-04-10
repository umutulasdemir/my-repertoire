package com.example.sahneovevi.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahneovevi.Adapters.DeletedTab1Adapter;
import com.example.sahneovevi.Adapters.DeletedTab2Adapter;
import com.example.sahneovevi.Adapters.SongAdapter2;
import com.example.sahneovevi.Adapters.TabAdapter1;
import com.example.sahneovevi.R;
import com.example.sahneovevi.Utility.MyItemTouchHelper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeletedTab2Fragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> songList;
    RecyclerView recyclerView;
    DeletedTab2Adapter tabAdapter1;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_deleted2, container, false);
        define();
        fetchSongs();
        return view;
    }
    public void define(){
        firebaseDatabase = FirebaseDatabase.getInstance("https://sahne-sov-evi-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        songList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.songRecyclerView5);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        tabAdapter1 = new DeletedTab2Adapter(songList, getActivity(),getContext());
        ItemTouchHelper.Callback callback = new MyItemTouchHelper(tabAdapter1);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        tabAdapter1.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(tabAdapter1);
    }

    public void fetchSongs(){
        databaseReference.child("Songs").orderByChild("list2pos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.i("key",snapshot.getKey());
                if (snapshot.child("list2pos").getValue().toString().equals("-1")) songList.add(snapshot.getKey());
                Log.i("song list size: ",songList.size()+"");
                tabAdapter1.notifyDataSetChanged();
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
}
