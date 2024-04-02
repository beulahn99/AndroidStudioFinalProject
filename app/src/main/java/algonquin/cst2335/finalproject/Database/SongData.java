/**
 * Name: Diya Valand
 * Final Project: Deezer Song Search API
 * Due Date: 5th April
 */
package algonquin.cst2335.finalproject.Database;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public SongData(String title, String artistName, String albumName, int duration, String coverUrl) {
        this.title = title;
        this.artistName = artistName;
        this.albumName = albumName;
        this.duration = duration;
        this.coverUrl = coverUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    // Constructor, getters, and setters
}