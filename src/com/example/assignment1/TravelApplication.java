package com.example.assignment1;

import android.app.Application;
import android.content.Context;

public class TravelApplication extends Application {
    transient private static TravelClaimOwner owner = null;
    private static Context context;
    
    @Override
    public void onCreate() {
        super.onCreate();
        TravelApplication.context = getApplicationContext();
    }

    public static TravelClaimOwner getMainOwner() {
        if (owner == null) {
            PersistenceHandler saverLoader = new AndroidPersistence(context);
            owner = new TravelClaimOwner(saverLoader);
        }

        return owner;
    }
}
