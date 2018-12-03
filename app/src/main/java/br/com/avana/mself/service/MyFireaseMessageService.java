package br.com.avana.mself.service;


import android.content.Context;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireaseMessageService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        getSharedPreferences("_", Context.MODE_PRIVATE).edit().putString("newToken", s).apply();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

    public static String getToken(Context context){
        return context.getSharedPreferences("_", Context.MODE_PRIVATE).getString("newToken", "");
    }
}
