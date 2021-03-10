package et.tv7.taevastv7;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import static et.tv7.taevastv7.helpers.Constants.LOG_TAG;
import static et.tv7.taevastv7.helpers.Constants.VOLLEY_CACHE;

/**
 * Application class. Implements volley request queue functionality.
 */
public class TaevasTv7 extends Application {

    private static final String TAG = TaevasTv7.class.getSimpleName();

    private RequestQueue requestQueue = null;
    private static TaevasTv7 taevasTv7 = null;
    private boolean connectedToNet = true;
    private Activity activity = null;

    /**
     * onCreate() - Android lifecycle method.
     */
    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            Log.d(LOG_TAG, "TaevasTv7.onCreate() called.");
        }

        super.onCreate();
        taevasTv7 = this;
    }

    /**
     * onTerminate() - Android lifecycle method.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (requestQueue != null) {
            requestQueue.stop();
        }
    }

    /**
     * Returns instance of this class.
     * @return
     */
    public static synchronized TaevasTv7 getInstance() {
        return taevasTv7;
    }

    /**
     * Returns volley request queue.
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (BuildConfig.DEBUG) {
            Log.d(LOG_TAG, "TaevasTv7.getRequestQueue() called.");
        }

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds request to volley request queue.
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        if (BuildConfig.DEBUG) {
            Log.d(LOG_TAG, "TaevasTv7.addToRequestQueue() called.");
        }

        req.setShouldCache(VOLLEY_CACHE);
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels volley pending request.
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    /**
     * Returns is connected to network value.
     * @return
     */
    public boolean getConnectedToNet() {
        return connectedToNet;
    }

    /**
     * Sets is connected to network value.
     * @param value
     */
    public void setConnectedToNet(boolean value) {
        connectedToNet = value;
    }

    /**
     * Returns activity.
     * @return
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Sets activity.
     * @param value
     */
    public void setActivity(Activity value) {
        activity = value;
    }
}
