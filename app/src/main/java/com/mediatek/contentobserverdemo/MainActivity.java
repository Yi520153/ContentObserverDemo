package com.mediatek.contentobserverdemo;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mediatek.contentobserverdemo.observers.FlightModeContentObserver;
import com.mediatek.contentobserverdemo.observers.TimeContentObserver;
import com.mediatek.contentobserverdemo.observers.WifiContentObserver;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FlightModeContentObserver flightModeContentObserver;
    private TimeContentObserver timeContentObserver;
    private WifiContentObserver wifiContentObserver;

    private TextView flightModeTip;
    private TextView timeModeTip;
    private TextView wifiTip;

    private Handler mHandler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FlightModeContentObserver.MSG_FLIGHT_MODE:
                    int isFlightModeOpen = (int) msg.obj;
                    if (1 == isFlightModeOpen) {
                        flightModeTip.setText("当前飞行模式：已开启");
                    } else {
                        flightModeTip.setText("当前飞行模式：已关闭");
                    }
                    break;
                case TimeContentObserver.MSG_TIME_12_24:
                    int is12Or24Hour = (Integer) msg.obj;
                    if (is12Or24Hour == 12) {
                        timeModeTip.setText("当前时间格式：12小时制度");
                    } else {
                        timeModeTip.setText("当前时间格式：24小时制度");
                    }
                    break;
                case WifiContentObserver.WIFI_ON:
                    int isWifiOn = (Integer) msg.obj;
                    if (isWifiOn == 2) {
                        wifiTip.setText("当前Wifi：已开启");
                    } else {
                        wifiTip.setText("当前Wifi：已关闭");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建观察者
        flightModeContentObserver = new FlightModeContentObserver(mHandler);
        timeContentObserver = new TimeContentObserver(mHandler);
        wifiContentObserver = new WifiContentObserver(mHandler);

        Button flightModeOpen = findViewById(R.id.flightModeOpen);
        flightModeTip = findViewById(R.id.flightModeTip);
        Button timeModeOpen = findViewById(R.id.timeModeOpen);
        timeModeTip = findViewById(R.id.timeModeTip);
        Button wifiOpen = findViewById(R.id.wifiOpen);
        wifiTip = findViewById(R.id.wifiTip);


        flightModeOpen.setOnClickListener(v -> startFlightModeRegister());

        timeModeOpen.setOnClickListener(v -> startTimeRegister());

        wifiOpen.setOnClickListener(v -> startWifiOnRegister());

    }


    private void startTimeRegister() {
        Uri timeUri = Settings.System.getUriFor(Settings.System.TIME_12_24);
        Log.e(TAG, "timeUri=" + timeUri);
        ContentResolver contentResolver = getContentResolver();
        contentResolver.registerContentObserver(timeUri, false, timeContentObserver);
    }

    private void startFlightModeRegister() {
        Uri flightModeUri = Settings.Global.getUriFor(Settings.Global.AIRPLANE_MODE_ON);
        Log.e(TAG, "flightModeUri=" + flightModeUri);
        ContentResolver contentResolver = getContentResolver();
        contentResolver.registerContentObserver(flightModeUri, false, flightModeContentObserver);
    }

    private void startWifiOnRegister() {
        Uri wifiOnUri = Settings.Global.getUriFor(Settings.Global.WIFI_ON);
        Log.e(TAG, "wifiOnUri=" + wifiOnUri);
        ContentResolver contentResolver = getContentResolver();
        contentResolver.registerContentObserver(wifiOnUri, false, wifiContentObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(timeContentObserver);
        getContentResolver().unregisterContentObserver(flightModeContentObserver);
        getContentResolver().unregisterContentObserver(wifiContentObserver);
    }
}