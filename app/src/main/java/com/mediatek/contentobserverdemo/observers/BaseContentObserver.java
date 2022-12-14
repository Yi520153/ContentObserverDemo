package com.mediatek.contentobserverdemo.observers;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

import com.mediatek.contentobserverdemo.MyApplication;

public class BaseContentObserver extends ContentObserver {

    protected static String TAG = BaseContentObserver.class.getSimpleName();

    protected Handler mHandler;
    protected Context mContext;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public BaseContentObserver(Handler handler) {
        super(handler);
        mHandler = handler;
        this.mContext = MyApplication.getApplication().getApplicationContext();
    }
}
