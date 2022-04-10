package com.example.sahneovevi.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sahneovevi.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddSongFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView name, chords;
    Button button;
    View view;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    int songCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_song, container, false);
        define();
        fetchSongs();
        action();
        return view;
    }

    public void define(){
        firebaseDatabase = FirebaseDatabase.getInstance("https://sahne-sov-evi-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        name = view.findViewById(R.id.editTextSongName);
        chords = view.findViewById(R.id.editTextChords);
        button = view.findViewById(R.id.addSongButton);
        spinner1 = view.findViewById(R.id.attributeSpinner1);
        spinner2 = view.findViewById(R.id.attributeSpinner2);
        spinner3 = view.findViewById(R.id.attribute3);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.attributes1, R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.attributes2, R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getContext(),R.array.attributes3, R.layout.spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        songCount = 0;
    }
    public void action(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ksjs",name.getText().toString()+"LA");
                if(!name.getText().toString().equals("")){
                    Map map = new HashMap();
                    map.put("name", name.getText().toString());
                    map.put("tone",spinner3.getSelectedItem().toString());
                    map.put("chords", chords.getText().toString());
                    map.put("attribute1", spinner1.getSelectedItem().toString());
                    map.put("attribute2", spinner2.getSelectedItem().toString());
                    map.put("lowercase", name.getText().toString().toLowerCase());
                    map.put("list1pos",songCount);
                    map.put("list2pos",songCount);
                    map.put("list3pos",songCount);
                    databaseReference.child("Songs").child(name.getText().toString().toLowerCase()).setValue(map);
                }
            }
        });
    }
    public void fetchSongs(){
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(songCount< (int)snapshot.getChildrenCount()) songCount = (int)snapshot.getChildrenCount();
                Log.i("CHILDCOUNT:", songCount+"");
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


}
