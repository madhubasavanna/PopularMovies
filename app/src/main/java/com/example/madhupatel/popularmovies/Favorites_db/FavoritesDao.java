package com.example.madhupatel.popularmovies.Favorites_db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM Favorites ORDER BY id")
    List<Favorites> loadFavorites();

    @Query("SELECT id FROM Favorites WHERE id =:fid")
    int isBookmarked(int fid);

    @Insert
    void addToFavorites(Favorites favorites);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorites(Favorites favorites);

    @Delete
    void removeFromFavorites(Favorites favorites);
}
