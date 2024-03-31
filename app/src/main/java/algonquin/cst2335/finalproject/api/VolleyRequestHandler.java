package algonquin.cst2335.finalproject.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Purpose: Handles making HTTP GET requests using Volley library and provides callback interfaces for success and error responses.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class VolleyRequestHandler {

    /**
     * The context used to make Volley requests.
     */
    private Context context;

    /**
     * Constructs a VolleyRequestHandler with the specified context.
     *
     * @param context The context used to make Volley requests.
     */
    public VolleyRequestHandler(Context context) {
        this.context = context;
    }

    /**
     * Makes a GET request to the specified URL and provides a callback for success and error responses.
     *
     * @param url      The URL to make the GET request to.
     * @param listener The listener to handle success and error responses.
     */
    public void getData(String url, final VolleyResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                });
        // Add the request to the Volley request queue
        VolleyRequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Callback interface for handling successful responses from Volley requests.
     */
    public interface VolleyResponseListener {

        /**
         * Called when a successful response is received from the Volley request.
         *
         * @param response The JSON response received from the request.
         */
        void onSuccess(JSONObject response);

        /**
         * Called when an error response is received from the Volley request.
         *
         * @param errorMessage The error message describing the cause of the error.
         */
        void onError(String errorMessage);
    }
}
