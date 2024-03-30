package algonquin.cst2335.finalproject.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequestQueueSingleton {

    private static VolleyRequestQueueSingleton instance;
    /**

     Request queue for handling network requests.
     */
    private RequestQueue requestQueue;

    private static Context context;

    private VolleyRequestQueueSingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }


    public static synchronized VolleyRequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyRequestQueueSingleton(context);
        }
        return instance;
    }


    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {

            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
