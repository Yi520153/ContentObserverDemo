package com.mediatek.contentobserverdemo.observers;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

public class FlightModeContentObserver extends BaseContentObserver {

    public static final int MSG_FLIGHT_MODE = 1;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public FlightModeContentObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        //Settings.System.AIRPLANE_MODE_ON 已过时
        try {
            int isFlightModeOpen = Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON);
            Log.i(TAG, " isFlightModeOpen=" + isFlightModeOpen);
            mHandler.obtainMessage(MSG_FLIGHT_MODE,isFlightModeOpen).sendToTarget();
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }
}
