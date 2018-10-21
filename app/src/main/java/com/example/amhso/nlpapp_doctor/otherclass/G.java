package com.example.amhso.nlpapp_doctor.otherclass;



import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class G extends Application {

    public static Context context;
    public static Activity activity;

//    public static String urlserver="http://www.darmlneh.com/android/";
//    public static String ServerImg="http://www.darmlneh.com/uploads/";
//    public static String urlwebview="http://www.darmlneh.com/";
    public static String urlserver="http://darmalena.com/android/";
    public static String ServerImg="http://darmalena.com/uploads/";
    public static String urlwebview="http://darmalena.com/";
//    public static String Service=":9000";


//    public static String urlserver="http://192.168.1.9/android/";
//    public static String ServerImg="http://192.168.1.9/uploads/";
//    public static String urlwebview="http://192.168.1.9/";
    public static String Service="ws://192.168.1.9:9000";



    public static final String MyPREFERENCES = "MyPrefs";
    public static final String id_user = "id_user";





















    @Override
    public void onCreate() {

        context = getApplicationContext();
        super.onCreate();




    }









    public static boolean checknet() {
        ConnectivityManager conMgr;
        conMgr = (ConnectivityManager) G.activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            return   true;

        } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            return false;
        }
        return false;
    }



}