package com.example.sahneovevi.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahneovevi.Models.SongItem;
import com.example.sahneovevi.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class SearchFragment extends Fragment {


    View view;

    ImageView searchImage;
    LinearLayout cancelLayout;
    EditText editText;
    RecyclerView resultList;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> songList;
    String selectedSong;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //getActivity().getSupportFragmentManager().popBackStack();

        view = inflater.inflate(R.layout.fragment_search, container, false);
        define();
        action();
        return view;
    }

    public void define(){
        try {
            songList = getArguments().getStringArrayList("songList");
        }catch (NullPointerException e){Log.i("null","null warning");}
        resultList = view.findViewById(R.id.tagSearchRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        resultList.setLayoutManager(layoutManager);
        resultList.setHasFixedSize(true);
        cancelLayout = view.findViewById(R.id.cancel_layout);
        searchImage = view.findViewById(R.id.searchImageForFragment);
        editText = view.findViewById(R.id.editTextSearchForFragment);
        editText.setText( getArguments().getString("starter"));
        editText.setCursorVisible(true);
        editText.setSelection(1);
        firebaseDatabase = FirebaseDatabase.getInstance("https://sahne-sov-evi-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        searchTag(editText.getText().toString());

        cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout
                             , new HomeFragment()).commit();
            }
        });
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTag(editText.getText().toString());
            }
        });
    }
    public void action(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchTag(editText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    songList.add(editText.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putInt("case1",0);
                    Fragment homeFragment = new HomeFragment();
                    homeFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout
                            , homeFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }
    public void searchTag(String targetText){
        Log.i("song list: ",songList.toString());
        Query query = databaseReference.child("Songs").orderByChild("lowercase").startAt(targetText.toLowerCase()).endAt(targetText.toLowerCase() + "\uf8ff");
        FirebaseRecyclerOptions<SongItem> options = new FirebaseRecyclerOptions.Builder<SongItem>()
                .setQuery(query,SongItem.class)
                .build();
        FirebaseRecyclerAdapter<SongItem,BasicTagsViewHolder> searchingRecyclerAdapter = new FirebaseRecyclerAdapter<SongItem, BasicTagsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BasicTagsViewHolder basicTagsViewHolder, int i, @NonNull SongItem songItem) {
                try {
                    basicTagsViewHolder.context.setText(songItem.getName());
                    basicTagsViewHolder.count.setText(songItem.getTone());
                }catch (NullPointerException e){
                    Log.i("null","null warning");
                }
                basicTagsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        Log.i("songlist: ", songList.toString());
                        Log.i("lowercase: ",songList.indexOf(songItem.getLowercase().toString())+"");
                        bundle.putInt("case1",songList.indexOf(songItem.getLowercase().toString()));
                        Fragment homeFragment = new HomeFragment();
                        homeFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout
                                , homeFragment).commit();
                    }
                });
            }

            @NonNull
            @Override
            public BasicTagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design2,parent,false);
                BasicTagsViewHolder viewHolder = new BasicTagsViewHolder(view);
                return viewHolder;
            }
        };
        resultList.setAdapter(searchingRecyclerAdapter);
        searchingRecyclerAdapter.startListening();
    }
    public class BasicTagsViewHolder extends RecyclerView.ViewHolder{
        TextView context,count;
        public BasicTagsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.findViewById(R.id.contextZone);
            count = itemView.findViewById(R.id.peopleCount);
        }
    }
}