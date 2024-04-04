/**
 * Name: Diya Valand
 * Final Project: Deezer Song Search API
 * Due Date: 5th April
 */
/**
 * ApiServices provides a set of methods to interact with the Deezer API.
 * It is responsible for making network requests and fetching data from the API.
 */
package algonquin.cst2335.finalproject;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

/**
 * Handles API requests for the Deezer service using the Volley library for network operations.
 */
public class ApiServices {
    private static final String TAG = ApiServices.class.getSimpleName();
    private static final String BASE_URL = "https://api.deezer.com/";

    private RequestQueue requestQueue;
    private Context context;

    /**
     * Constructor to instantiate an ApiServices object with application context.
     *
     * @param context The application context used for creating the request queue.
     */
    public ApiServices(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Searches for an artist in the Deezer API and returns the result to the ApiResponseListener.
     *
     * @param artistQuery The artist's name to search for.
     * @param listener    The listener that handles the response or error.
     */
    public void searchArtist(String artistQuery, final ApiResponseListener listener) {
        String url = BASE_URL + "search?q=" + artistQuery;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error in API request: " + error.getMessage());
                        listener.onError("Error in API request");
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Fetches the songs from a Deezer tracklist URL and returns the result to the ApiResponseListener.
     *
     * @param tracklistUrl The URL from which to fetch the tracklist.
     * @param listener     The listener that handles the response or error.
     */
    public void fetchSongsFromTracklist(String tracklistUrl, final ApiResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                tracklistUrl,
                null,
                response -> listener.onSuccess(response),
                error -> {
                    Log.e(TAG, "Error in API request: " + error.getMessage());
                    listener.onError("Error in API request");
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Interface definition for callbacks to be invoked when API requests are completed.
     */
    public interface ApiResponseListener {
        /**
         * Called when the API request completes successfully.
         *
         * @param data The JSON response from the API.
         */
        void onSuccess(JSONObject data);

        /**
         * Called when there is an error during the API request.
         *
         * @param errorMessage The error message describing the failure.
         */
        void onError(String errorMessage);
    }
}
