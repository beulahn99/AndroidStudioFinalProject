/**
 * Name: Diya Valand
 * Final Project: Deezer Song Search API
 * Due Date: 5th April
 */
package algonquin.cst2335.finalproject.SongData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SearchHistoryDao {
    @Insert
    void insert(History history);

    @Query("SELECT * FROM History ORDER BY id DESC")
    List<History> getAllSearchTerms();

    @Query("DELETE FROM History")
    void deleteAll();
}