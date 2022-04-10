package com.example.sahneovevi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sahneovevi.Activities.MainActivity;
import com.example.sahneovevi.Fragments.HomeFragment;
import com.example.sahneovevi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TabAdapter1 extends RecyclerView.Adapter<TabAdapter1.ViewHolder> implements ItemTouchHelperAdapter {
    List<String> songList;
    Activity activity;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ItemTouchHelper mTouchHelper;

    public TabAdapter1(List<String> songList, Activity activity, Context context) {
        this.songList = songList;
        this.activity = activity;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance("https://sahne-sov-evi-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();
    }

    @NonNull
    @Override
    public TabAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_design5, parent, false);
        return new TabAdapter1.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabAdapter1.ViewHolder holder, int position) {
        databaseReference.child("Songs").child(songList.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    Log.i("names: ", snapshot.child("name").getValue().toString());
                    holder.name.setText(snapshot.child("name").getValue().toString());
                    holder.tone.setText(snapshot.child("tone").getValue().toString());
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

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        DatabaseReference ref = databaseReference.child("Songs");
        String removedOne = songList.get(fromPosition);
        songList.remove(fromPosition);
        songList.add(toPosition,removedOne);
        for(int i = 0;i<songList.size();i++) ref.child(songList.get(i)).child("list1pos").setValue(i);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        DatabaseReference ref2 = databaseReference.child("Songs");
        String removedOne = songList.get(position);
        songList.remove(position);
        databaseReference.child("Songs").child(removedOne).child("list1pos").setValue(-1);
        for(int i = 0;i<songList.size();i++) ref2.child(songList.get(i)).child("list1pos").setValue(i);
        notifyItemRemoved(position);
    }
    public void setTouchHelper(ItemTouchHelper itemTouchHelper){
        this.mTouchHelper = itemTouchHelper;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener, GestureDetector.OnGestureListener {

        TextView name;
        TextView tone;
        GestureDetector mGestureDetector;

        public ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.songNameZone2);
            tone = itemView.findViewById(R.id.toneZone2);
            mGestureDetector = new GestureDetector(itemView.getContext(),this);
            itemView.setOnTouchListener(this);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("pos", getBindingAdapterPosition());
            context.startActivity(intent);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mTouchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mGestureDetector.onTouchEvent(event);
            return true;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("pos", getBindingAdapterPosition());
            context.startActivity(intent);
        }
    }
}
