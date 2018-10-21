package com.example.amhso.nlpapp_doctor.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class autostart extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent arg1)
    {
//        /****** For Start Activity *****/
//        Intent i = new Intent(context, MainActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);

        /***** For start Service  ****/
        Intent myIntent = new Intent(context, MessageService.class);
        context.startService(myIntent);
    }
}
