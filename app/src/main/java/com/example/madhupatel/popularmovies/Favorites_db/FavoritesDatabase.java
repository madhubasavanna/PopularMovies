package com.example.madhupatel.popularmovies.Favorites_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomOpenHelper;
import android.content.Context;
import android.util.Log;

@Database(entities = Favorites.class, version = 1, exportSchema = false)
public abstract class FavoritesDatabase extends RoomDatabase {

    private static final String LOG_TAG = FavoritesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "Favorites";
    private static FavoritesDatabase sInstance;

    public static FavoritesDatabase getsInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG,"Creating new database");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavoritesDatabase.class, FavoritesDatabase.DATABASE_NAME)
                        // Remove this once done with testing
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG,"Getting the database instance");
        return sInstance;
    }

    public abstract FavoritesDao favoritesDao();
}
