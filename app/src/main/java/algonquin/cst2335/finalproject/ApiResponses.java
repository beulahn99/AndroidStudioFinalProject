/**
 * Name: Diya Valand
 * Final Project: Deezer Song Search API
 * Due Date: 5th April
 */
/**
 * This class handles the parsing of API responses into a list of {@link Song} objects.
 */
package algonquin.cst2335.finalproject;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * ApiResponses provides static methods to parse JSON objects retrieved from the Deezer API.
 */
public class ApiResponses {
    private static final String TAG = ApiResponses.class.getSimpleName();

    /**
     * Parses a JSON response object containing an array of song information and creates a list of {@link Song} objects.
     * Each song object is instantiated with the title, artist name, album name, cover URL, and duration.
     * If parsing fails, an error is logged and an empty list is returned.
     *
     * @param jsonResponse The JSON object containing the songs information.
     * @return A list of {@link Song} objects parsed from the JSON response.
     */
    public static List<Song> parseSongsFromJson(JSONObject jsonResponse) {
        List<Song> songsList = new ArrayList<>();

        try {
            JSONArray data = jsonResponse.getJSONArray("data"); // Assuming 'data' is a JSONArray

            for (int i = 0; i < data.length(); i++) {
                JSONObject songJson = data.getJSONObject(i);
                String title = songJson.getString("title");
                String artistName = songJson.getJSONObject("artist").getString("name");
                String albumName = songJson.getJSONObject("album").getString("title");
                String coverUrl = songJson.getJSONObject("album").getString("cover");
                int duration = songJson.getInt("duration");

                Song song = new Song(title, artistName, albumName, coverUrl, duration);
                songsList.add(song);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
        }

        return songsList;
    }
}
