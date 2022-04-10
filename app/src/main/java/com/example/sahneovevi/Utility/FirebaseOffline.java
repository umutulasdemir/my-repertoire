package com.example.sahneovevi.Utility;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseOffline extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance("https://saykomate-default-rtdb.europe-west1.firebasedatabase.app").setPersistenceEnabled(true);
    }
}
