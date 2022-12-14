package com.mediatek.contentobserverdemo.observers;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

/**
 * wifi状态开关观察者
 */
public class WifiContentObserver extends BaseContentObserver {

    public static final int WIFI_ON = 3;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public WifiContentObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        try {
            int isWIfiOn = Settings.Global.getInt(mContext.getContentResolver(),
                    Settings.Global.WIFI_ON);
            Log.i(TAG, " isWIfiOn=" + isWIfiOn);
            mHandler.obtainMessage(WIFI_ON,isWIfiOn).sendToTarget();
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }
}
