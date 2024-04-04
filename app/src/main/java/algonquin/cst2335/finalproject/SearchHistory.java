/**
 * Name: Diya Valand
 * Final Project: Deezer Song Search API
 * Due Date: 5th April
 */
/**
 * SearchHistory is an activity class responsible for displaying the search history and handling its deletion from a local database.
 * It uses the Room persistence library to interact with a SQLite database.
 */
package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import algonquin.cst2335.finalproject.Database.SongDatabase;

/**
 * The activity that manages the search history. It allows the user to delete all search history records from the database.
 */
public class SearchHistory extends AppCompatActivity {

    private SongDatabase db;
    private Button deleteHistoryButton;

    /**
     * Called when the activity is starting. This method sets up the content view and database, and initializes UI components.
     * The 'Delete History' button is set up to clear the search history from the database when clicked.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        // Setup Room database to manage search history data.
        db = Room.databaseBuilder(getApplicationContext(), SongDatabase.class, "searchHistory")
                .allowMainThreadQueries()
                .build();

        // Initialize the 'Delete History' button.
        deleteHistoryButton = findViewById(R.id.Delete_History);
        deleteHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete all search history from the database.
                db.searchHistoryDao().deleteAll();
                // Optionally, refresh your RecyclerView or UI component to reflect the changes.
            }
        });
    }
}
