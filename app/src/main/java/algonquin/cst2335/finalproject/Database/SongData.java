/**
 * Name: Diya Valand
 * Final Project: Deezer Song Search API
 * Due Date: 5th April
 */
package algonquin.cst2335.finalproject.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a song stored in the database.
 * Contains information such as title, artist name, album name, duration, and cover URL.
 */
@Entity(tableName = "songs")
public class SongData {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "artist")
    public String artistName;

    @ColumnInfo(name = "album")
    public String albumName;

    @ColumnInfo(name = "duration")
    public int duration;

    @ColumnInfo(name = "cover_url")
    public String coverUrl;

    /**
     * Constructs a SongData object with the specified parameters.
     *
     * @param title      The title of the song.
     * @param artistName The name of the artist.
     * @param albumName  The name of the album.
     * @param duration   The duration of the song.
     * @param coverUrl   The URL of the song's cover image.
     */
    public SongData(String title, String artistName, String albumName, int duration, String coverUrl) {
        this.title = title;
        this.artistName = artistName;
        this.albumName = albumName;
        this.duration = duration;
        this.coverUrl = coverUrl;
    }

    /**
     * Gets the ID of the song.
     *
     * @return The ID of the song.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the song.
     *
     * @param id The ID of the song.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of the song.
     *
     * @return The title of the song.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the song.
     *
     * @param title The title of the song.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the name of the artist.
     *
     * @return The name of the artist.
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Sets the name of the artist.
     *
     * @param artistName The name of the artist.
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     * Gets the name of the album.
     *
     * @return The name of the album.
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * Sets the name of the album.
     *
     * @param albumName The name of the album.
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * Gets the duration of the song.
     *
     * @return The duration of the song.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the song.
     *
     * @param duration The duration of the song.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the cover URL of the song.
     *
     * @return The cover URL of the song.
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * Sets the cover URL of the song.
     *
     * @param coverUrl The cover URL of the song.
     */
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
