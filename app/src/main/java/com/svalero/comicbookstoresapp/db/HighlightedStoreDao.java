package com.svalero.comicbookstoresapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Upsert;
import java.util.List;

@Dao
public interface HighlightedStoreDao {
    @Query("SELECT * FROM highlighted_store")
    List<HighlightedStore> getAll();

    // Insert / Update
    @Upsert
    void upsert(HighlightedStore highlightedStore);

    @Delete
    void delete(HighlightedStore highlightedStore);

    @Query("SELECT EXISTS(SELECT 1 FROM highlighted_store WHERE id = :id LIMIT 1)")
    boolean existsById(Long id);
}
