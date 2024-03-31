package algonquin.cst2335.finalproject.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Purpose: Singleton class for managing the Volley request queue.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class VolleyRequestQueueSingleton {

    /**
     * Singleton instance of the VolleyRequestQueueSingleton class.
     */
    private static VolleyRequestQueueSingleton instance;

    /**
     * Request queue for handling network requests.
     */
    private RequestQueue requestQueue;

    /**
     * The context used for creating the request queue.
     */
    private static Context context;

    /**
     * Private constructor to create a new instance of the VolleyRequestQueueSingleton class with the specified context.
     *
     * @param context The context used for creating the request queue.
     */
    private VolleyRequestQueueSingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    /**
     * Returns the singleton instance of the VolleyRequestQueueSingleton class, creating it if necessary.
     *
     * @param context The context used for creating the request queue.
     * @return The singleton instance of the VolleyRequestQueueSingleton class.
     */
    public static synchronized VolleyRequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyRequestQueueSingleton(context);
        }
        return instance;
    }

    /**
     * Returns the request queue, creating it if necessary.
     *
     * @return The request queue.
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {

            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds the specified request to the request queue.
     *
     * @param req The request to add to the request queue.
     * @param <T> The type of the request.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
