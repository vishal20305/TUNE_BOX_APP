package com.example.tunebox;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyForeService extends Service {


    public static final String SER_START = "ACTION_START_FOREGROUND_SERVICE";
    public static final String SER_STOP = "ACTION_STOP_FOREGROUND_SERVICE";

    public MyForeService() {//Service constructor
    }

    @Override
    public IBinder onBind(Intent intent) {//Ibinder
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    ArrayList<String> lst = new ArrayList<String>(); //new Arraylist of string type is taken to store each song of Raw folder
    Field[] f = R.raw.class.getFields();//it will store all files of raw folder
    MediaPlayer mediaplayer = new MediaPlayer();//New instance of Mediaplayer is taken as "mediaplayer" to play music and stop music
    int i = 0;//the value of i amd t will monitor the loop of songs
    int t = 1;

    @Override
    public void onCreate() {//onCreate
        super.onCreate();
        //My forefround service started
        for (int j = 0; j < f.length; j++) {//this will add each songs in Arraylist "lst" at each index
            lst.add(f[j].getName());
        }
        t = lst.size() - 1; //"t" is taken as length of list-1 to  monitor the loop i.e. when t become to size of lst then it will set as t=0 to again play from first song
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//this will triggered when service is started i.e. when user press at play button

        if (intent != null) {//if Intent is not not then
            String action = intent.getAction();//getting action i.e. user wants to start or stop service
            if (action != null)

                switch (action) { //by using switch cases particular action is triggered
                    case SER_START://start service
                        startForegroundService();
                        if (mediaplayer.isPlaying()) //if music is already playing then start it from start
                            mediaplayer.stop();//after stopping the previous music
                            if (i < t) {//if i<t then start next song
                                play(++i);
                            } else {//otherwise if i==t then start from first song to ensure loop
                                i = 0;
                                play(i);//play method taken to play song
                            }
                            mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {//on completion of song again play the next song
                                if (i < t) {//if i<t then start next song
                                    play(++i);
                                } else {//otherwise if i==t then start from first song to ensure loop
                                    i = 0;
                                    play(i);//play method taken to play song
                                }
                            }
                        });


                        Toast.makeText(getApplicationContext(), "FOREGROUND SERVICE STARTED", Toast.LENGTH_LONG).show();
                        break;
                    case SER_STOP:
                        stopForegroundService();
                        Toast.makeText(getApplicationContext(), "FOREGROUND SERVICE STOPPED", Toast.LENGTH_LONG).show();
                        break;
                }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void play(int track) {
        mediaplayer.reset();//reseting meadiaplayer
        int resId = getResources().getIdentifier(lst.get(track), "raw", getPackageName());//getting id of song
        mediaplayer = MediaPlayer.create(this, resId);//creating instance of mediaplayer by help of that id
        mediaplayer.setLooping(false);//this will be false because we dont want to play the same song again and again
        mediaplayer.start();//play the music
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName) {//creating notification channel
        Intent RES = new Intent(this, MainActivity.class);
        // Creating the TaskStackBuilder i.e stack builder and adding  the intent, which will inflates the back stack
        TaskStackBuilder stack = TaskStackBuilder.create(this);
        stack.addNextIntentWithParentStack(RES);
        PendingIntent resultPendingIntent =
                stack.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);//pending intent help to active only one service

        NotificationChannel c = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);//instanse of notiffication channel created
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(c);//creating notification channel

        NotificationCompat.Builder no_build = new NotificationCompat.Builder(this, channelId);
        Notification notification = no_build.setOngoing(true)

                .setCategory(Notification.CATEGORY_SERVICE)//setting category of the notification
                .setContentTitle("TuneBox is Running")//setting title or message shown in the notification
                .setPriority(NotificationManager.IMPORTANCE_MIN)//setting priority
                .setSmallIcon(R.drawable.play)//setting the icon shown in the notification, here taken the same icon which is used as play button
                .setContentIntent(resultPendingIntent) //setting content of intent we are taking pending intent,this is not create multiple services when one is active
                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, no_build.build());
        startForeground(1, notification);
    }

    //Building and starting foreground service
    private void startForegroundService() {//starting fore ground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//checking api version
            createNotificationChannel("my_service", "My Service");
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);// Creating  notification builder.
            Notification notification = builder.build();// Building  notification.
            startForeground(1, notification); // Starting  foreground service.
        }
    }

    //stop foreground service by using this method
    private void stopForegroundService() {
        if (mediaplayer.isPlaying()) {
            mediaplayer.stop();
            // Stop foreground service and remove the notification.
            stopForeground(true);

            // Stop the foreground service.
            stopSelf();
        }
    }
}

