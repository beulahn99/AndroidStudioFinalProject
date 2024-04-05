/**
 * Name: Diya Valand
 * Final Project: Deezer Song Search API
 * Due Date: 5th April
 */
package algonquin.cst2335.finalproject;

/**
 * The {@code DeezerSongActivity} class extends {@link AppCompatActivity} and serves as the main activity for a music search and display feature.
 * It allows users to search for songs via the Deezer API, view a list of results in a {@link RecyclerView}, and navigate to other activities
 * to view detailed information about a selected song or to see their favorite songs. This activity manages various UI components, handles user input,
 * and interacts with a local database and shared preferences for storing search history and preferences.
 *
*/

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.Database.SongsDatabase;
import algonquin.cst2335.finalproject.Database.History;
import algonquin.cst2335.finalproject.Database.SearchHistoryDao;

public class DeezerMainActivity extends AppCompatActivity implements SongHolder.OnItemClickListener {

    private RecyclerView recyclerView;
    private SongHolder songAdapter;
    private List<Song> songList;
    private ApiServices apiServices;
    private SharedPreferencesManager sharedPreferencesManager;
    private EditText searchEditText;
    private String searchQuery;
    private SharedPreferences sharedPreferences;
    private SongsDatabase db;
    private SearchHistoryDao searchHistoryDao;

    /**
     * Initializes the activity, including the toolbar, search functionality,
     * and RecyclerView for displaying song results. It restores any previously
     * entered search term and sets up listeners for user interactions.
     *
     * @param savedInstanceState Contains data supplied in onSaveInstanceState(Bundle) if the activity is re-initialized.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_activity_main);

        // Initialize RecyclerView, SongAdapter, ApiService, and SharedPreferencesManager
        recyclerView = findViewById(R.id.recycler_view_songs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        songList = new ArrayList<>();
        songAdapter = new SongHolder(songList, this);
        recyclerView.setAdapter(songAdapter);
        apiServices = new ApiServices(this);
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize search EditText
        searchEditText = findViewById(R.id.search_edit_text);
        // Initialize Room db
        db = Room.databaseBuilder(getApplicationContext(),
                SongsDatabase.class, "searchHistory").build();
        searchHistoryDao = db.searchHistoryDao();

        // Example usage:
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery = searchEditText.getText().toString();
                performSongSearch(searchQuery);
                saveSearchTerm(searchQuery);

            }
        });

        // Example of retrieving search term from SharedPreferences and setting it to the EditText
        String savedSearchTerm = sharedPreferencesManager.getSearchTerm();
        if (!savedSearchTerm.isEmpty()) {
            searchEditText.setText(savedSearchTerm);
        }
    }

    /**
     * Saves the search term to the local database in a background thread.
     *
     * @param searchTerm The search term to be saved.
     */
    private void saveSearchTerm(String searchTerm) {
        new Thread(() -> {
            searchHistoryDao.insert(new History(searchTerm));
        }).start();
    }

    /**
     * Performs a song search using the provided query string.
     *
     * @param query The search query.
     */
    private void performSongSearch(String query) {
        try {
            apiServices.searchArtist(query, new ApiServices.ApiResponseListener() {
                @Override
                public void onSuccess(JSONObject data) {
                    try {
                        List<Song> songs = ApiResponses.parseSongsFromJson(data);
                        if (songs != null) {
                            songList.clear();
                            songList.addAll(songs);
                            songAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("DeezerSongActivity", "Error parsing JSON response");
                            Toast.makeText(DeezerMainActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("DeezerSongActivity", "Error: " + e.getMessage());
                        Toast.makeText(DeezerMainActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("DeezerSongActivity", "Error: " + errorMessage);
                    Toast.makeText(DeezerMainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("DeezerSongActivity", "Error: " + e.getMessage());
            Toast.makeText(DeezerMainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_artists));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSongSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text changes if needed
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            showHelpDialog();
            return true;
        } else if (id == R.id.action_favorite_songs) {
            openFavoriteSongsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows a help dialog to provide assistance to the user.
     */
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage(R.string.help);
        builder.setPositiveButton(R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Opens the activity to display favorite songs.
     */
    private void openFavoriteSongsActivity() {
        Intent intent = new Intent(this, FavoriteSongsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        // Handle item click to open details activity
        Song selectedSong = songList.get(position);
        Intent intent = new Intent(DeezerMainActivity.this, FetchData.class);
        intent.putExtra("selectedSong", (Serializable) selectedSong);
        startActivity(intent);
        // Show a Snackbar message
        showSnackbar(getString(R.string.song_selected) + selectedSong.getTitle());
    }

    /**
     * Shows a Snackbar message with the given message.
     *
     * @param message The message to be shown in the Snackbar.
     */
    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}