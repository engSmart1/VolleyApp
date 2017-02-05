package Util;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Hytham on 2/4/2017.
 */

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLouder;

    private static AppController mInstance;

    public void onCreate(){
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance(){
        return mInstance;
    }
    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        return mRequestQueue;
    }
    public ImageLoader getImageLouder(){
        getRequestQueue();
        if (mImageLouder == null){
            mImageLouder = new ImageLoader(this.mRequestQueue ,new LruBitmapCache());
        }
        return this.mImageLouder;
    }
    public <T> void addToRequestQueue(Request<T> req ,String tag){
        req.setTag(TextUtils.isEmpty(tag) ?  TAG : tag);
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    public void cancelPendingRequest(Object tag){
        if (mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }
}

