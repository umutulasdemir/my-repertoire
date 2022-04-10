package com.example.sahneovevi.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class TabFragment1 extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> songList;
    RecyclerView recyclerView;
    TabAdapter1 tabAdapter1;
    String lastFragment;
    String filter1;
    String filter2;
    Spinner spinner1;
    Spinner spinner2;
    ArrayAdapter<CharSequence> filterAdapter1;
    ArrayAdapter<CharSequence> filterAdapter2;
    int a = 0;
    int b = 0;

    ItemTouchHelper itemTouchHelper;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab1, container, false);
        define();
        fetchFilters();
        databaseReference.child("ListFilters").child("Filter").child("positionBy").setValue("list1pos");
        action();
        return view;
    }
    public void define(){
        firebaseDatabase = FirebaseDatabase.getInstance("https://sahne-sov-evi-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        songList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.songRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        tabAdapter1 = new TabAdapter1(songList, getActivity(),getContext());
        ItemTouchHelper.Callback callback = new MyItemTouchHelper(tabAdapter1);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        tabAdapter1.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(tabAdapter1);
        spinner1 = view.findViewById(R.id.filter1Spinner1);
        spinner2 = view.findViewById(R.id.filter1Spinner2);
        filterAdapter1 = ArrayAdapter.createFromResource(getContext(),R.array.filterAttributes1, R.layout.spinner_item);
        filterAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(filterAdapter1);
        filterAdapter2 = ArrayAdapter.createFromResource(getContext(),R.array.filterAttributes2, R.layout.spinner_item);
        filterAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(filterAdapter1);
        spinner2.setAdapter(filterAdapter2);
    }

    public void fetchSongs(){
        databaseReference.child("Songs").orderByChild("list1pos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.i("CONTROL5","CONTROL55"+ snapshot.getKey());
                if (!snapshot.child("list1pos").getValue().toString().equals("-1")){
                    Log.i("HEAR999","HEAR555"+filter1 + snapshot.child("attribute1")+ filter2+ snapshot.child("attribute2"));
                    if ((filter1.equals(snapshot.child("attribute1").getValue().toString()) || filter1.equals("None")) && (filter2.equals(snapshot.child("attribute2").getValue().toString())|| filter2.equals("None"))){
                        Log.i("HEAR999","HEAR999");
                        songList.add(snapshot.getKey());
                    }
                };
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
        });/*
        // check and update the token.
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if ((task.isSuccessful())){
                    updateToken(task.getResult());
                }
            }
        });*/

    }

    public void hideKeyboard() {
        View view2 = getActivity().getCurrentFocus();
        if (view2 != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view2.getWindowToken(),0);
        }
    }
    public void action(){
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                databaseReference.child("ListFilters").child("Filter").child("filter1").setValue(spinner1.getSelectedItem().toString());
                if (a == 1){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameTabLayout
                            , new TabFragment1()).commit();
                }
                a++;
                Log.i("COUNT:", a+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                databaseReference.child("ListFilters").child("Filter").child("filter2").setValue(spinner2.getSelectedItem().toString());
                if (b==1){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameTabLayout
                            , new TabFragment1()).commit();
                }
                b++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void fetchFilters(){
        databaseReference.child("ListFilters").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    Log.i("position:",snapshot.child("positionBy").getValue().toString());
                    lastFragment = snapshot.child("positionBy").getValue().toString();
                    filter1 = snapshot.child("filter1").getValue().toString();
                    filter2 = snapshot.child("filter2").getValue().toString();
                    Log.i("SELECTION",filter1 + filter2);
                    for(int i=0; i < filterAdapter1.getCount(); i++) {
                        if (filter1.equals(filterAdapter1.getItem(i).toString())) {
                            spinner1.setSelection(i);
                            break;
                        }
                    }
                    for(int i=0; i < filterAdapter2.getCount(); i++) {
                        if (filter2.equals(filterAdapter2.getItem(i).toString())) {
                            spinner2.setSelection(i);
                            break;
                        }
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
