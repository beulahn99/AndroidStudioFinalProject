/**
 * Name: Diya Valand
 * Final Project: Deezer Song Search API
 * Due Date: 5th April
 */
/**
 * FetchData is an activity class responsible for displaying the details of a song and handling its storage in a local database.
 * It uses the Room persistence library to interact with a SQLite database and the Picasso library for image loading.
 */
package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.squareup.picasso.Picasso;
import algonquin.cst2335.finalproject.Database.SongDatabase;
import algonquin.cst2335.finalproject.Database.SongData;

public class FetchData extends AppCompatActivity {
    private ImageView albumCoverImageView;
    private TextView titleTextView, artistTextView, albumTextView, durationTextView;
    private SongDatabase db;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * It initializes the database and UI components and sets the content view to the activity's layout.
     * It also retrieves the Song object passed from the previous Activity and displays its details.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize Room database
        db = Room.databaseBuilder(getApplicationContext(), SongDatabase.class, "songs_database")
                .fallbackToDestructiveMigration()
                .build();

        // Initialize views
        albumCoverImageView = findViewById(R.id.album_cover_image);
        titleTextView = findViewById(R.id.song_title);
        artistTextView = findViewById(R.id.artist_name);
        albumTextView = findViewById(R.id.album_name);
        durationTextView = findViewById(R.id.song_duration);

        // Get the selected song from the intent
        final Song song = (Song) getIntent().getSerializableExtra("selectedSong");
        if (song != null) {
            displaySongDetails(song);
        }

        // Set up the listener for the save button
        findViewById(R.id.save_button).setOnClickListener(v -> {
            if (song != null) {
                saveSongToDatabase(song);
            }
        });
    }

    /**
     * Displays the details of the provided Song object in the UI.
     *
     * @param song The Song object whose details are to be displayed.
     */
    private void displaySongDetails(Song song) {
        titleTextView.setText(song.getTitle());
        artistTextView.setText(song.getArtistName());
        albumTextView.setText(song.getAlbumName());
        durationTextView.setText(song.getFormattedDuration());
        Picasso.get().load(song.getCoverUrl()).into(albumCoverImageView);
    }

    /**
     * Saves the provided Song object to the local Room database.
     * It performs the database operation in a separate thread and shows a toast upon completion.
     *
     * @param song The Song object to save to the database.
     */
    private void saveSongToDatabase(Song song) {
        SongData songData = new SongData(song.getTitle(), song.getArtistName(), song.getAlbumName(), song.getDuration(), song.getCoverUrl());
        new Thread(() -> {
            db.songDao().insert(songData);
            runOnUiThread(() -> Toast.makeText(FetchData.this, "Song saved to favorites", Toast.LENGTH_SHORT).show());
        }).start();
    }
}
