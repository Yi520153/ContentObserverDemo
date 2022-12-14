package com.mediatek.contentobserverdemo.observers;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

public class TimeContentObserver extends BaseContentObserver {

    public static final int MSG_TIME_12_24 = 2;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public TimeContentObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        try {
            int is12Or24Hour = Settings.System.getInt(mContext.getContentResolver(), Settings.System.TIME_12_24);
            Log.e(TAG, " is12Or24Hour=" + is12Or24Hour);
            mHandler.obtainMessage(MSG_TIME_12_24, is12Or24Hour).sendToTarget();
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

    }
}
