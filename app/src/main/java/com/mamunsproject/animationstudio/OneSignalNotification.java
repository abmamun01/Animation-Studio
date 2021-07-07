package com.mamunsproject.animationstudio;

import android.app.Application;

import com.onesignal.OneSignal;

public class OneSignalNotification extends Application {

    private static final String ONESIGNAL_APP_ID = "ffc7bbb6-0881-4fad-9192-f84f2549636c";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }


}
