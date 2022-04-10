package com.example.sahneovevi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahneovevi.Activities.MainActivity;
import com.example.sahneovevi.Models.SongItem;
import com.example.sahneovevi.R;
import com.example.sahneovevi.Utility.Date;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    List<String> songList;
    Activity activity;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String theLastMessage;
    String theTime;

    public SongAdapter(List<String> songList, Activity activity, Context context) {
        this.songList = songList;
        this.activity = activity;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance("https://sahne-sov-evi-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        databaseReference.child("Songs").child(songList.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    Log.i("names: ", snapshot.child("name").getValue().toString());
                    holder.name.setText(snapshot.child("name").getValue().toString());
                    holder.tone.setText(snapshot.child("tone").getValue().toString());
                    holder.chords.setText(snapshot.child("chords").getValue().toString());
                }catch (NullPointerException e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView tone;
        TextView chords;

        public ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.songNameZone);
            tone = itemView.findViewById(R.id.toneZone);
            chords = itemView.findViewById(R.id.chordsZone);
        }
    }
}