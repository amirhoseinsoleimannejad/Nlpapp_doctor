package com.example.amhso.nlpapp_doctor.service;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.amhso.nlpapp_doctor.ChatallActivity;
import com.example.amhso.nlpapp_doctor.R;
import com.example.amhso.nlpapp_doctor.otherclass.G;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;


public class MessageService extends Service {


    public static Boolean start=true;
    public static WebSocketClient mWebSocketClient;
    public String id_user="bbbb";
//    public Boolean first=true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }






    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        connectWebSocket();





        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        mWebSocketClient.close();
//        start=false;

    }















    private void connectWebSocket() {
        URI uri;


        try {
            uri = new URI(G.Service);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Open" );

                JSONObject jsonObject= new JSONObject();

                try {
                    jsonObject.put("action", "setName");
                    jsonObject.put("name", 35);
                } catch (JSONException e) {
                    Log.i("sssss", "sssssss: "+e.toString());
                }

                mWebSocketClient.send(jsonObject.toString());

            }

            @Override
            public void onMessage(String s) {
                final String message = s;

                Log.i("message    ", s );



                if(ChatallActivity.MainIsStart()){
                    ChatallActivity.getMessage(message,message);
                }
                else{
                    addNotification_message();
                }

//                JSONObject jsonObject= new JSONObject();
//
//                try {
//                    jsonObject.put("action", "chat");
//                    jsonObject.put("message", "hello start");
//                } catch (JSONException e) {
//                    Log.i("sssss", "sssssss: "+e.toString());
//                }
//
//                mWebSocketClient.send(jsonObject.toString());

            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };



        try {
            mWebSocketClient.connect();

        }
        catch (Exception a){

        }


    }









    private void addNotification_message() {


        android.support.v4.app.NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.aaa)
                        .setContentTitle("پیام ")
                        .setContentText("پیامی از طرف درمانگر برای شما ارسال شده");


        Intent notificationIntent = new Intent(this, ChatallActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);



        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }




}


