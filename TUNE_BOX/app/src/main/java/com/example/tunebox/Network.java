package com.example.tunebox;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import androidx.appcompat.app.AppCompatActivity;

public class Network extends AppCompatActivity {
    Button but,stop;

    TextView tv;
    int net;
    String s;
    MediaPlayer m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        but=findViewById(R.id.but);//getting the value of button by the help of findViewByid
        stop=findViewById(R.id.s);//getting the value of button by the help of findViewByid
        tv=findViewById(R.id.textView);//getting the value of button by the help of findViewByid

        ConnectivityManager m = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ak=m.getActiveNetworkInfo();//getting network info
        if(ak!=null){//if info is not null then
            if(ak.getType()== ConnectivityManager.TYPE_WIFI){// if network is from wifi
                net=1;//setting net=1
                Toast.makeText(this, "WIFI Network", Toast.LENGTH_SHORT).show();//Toast the message i.e. "Wifi network"
            }
            if(ak.getType()== ConnectivityManager.TYPE_MOBILE){// if network is from of Network type
                net=1;
                Toast.makeText(this, "Mobile Network ", Toast.LENGTH_SHORT).show();//Toast the message i.e. "mobile network"
            }
        }
        else{//otherwise
               net=0;//set noet=0
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();//Toast the message i.e. "No network connection"
        }

        but.setOnClickListener(new View.OnClickListener() {//on clicking on button
            @Override
            public void onClick(View v) {
                if(net==1) {//if the value set in net as 1 then that means Internet is available
                    s = "Internet Connection is Available";
                    new Getit().execute("https://faculty.iiitd.ac.in/~mukulika/s1.mp3"); //Download this song
                }
                else {//otherwise if the value set in net as 0 then that means Internet is available
                    s = "Internet Connection is Not Available";

                }
                tv.setText(s);//Now showing the message in textview

            }
        });


    }

class Getit extends AsyncTask<String, String, String> {//this will download the song and store in android private directory


    @Override
    protected void onPreExecute() { //This will executed when the Getit method executed
        super.onPreExecute();
        Toast.makeText(Network.this, "Starting Download", Toast.LENGTH_SHORT).show();//Displaying Toast that song downloading is starting
    }

    @Override
    protected String doInBackground(String... f_url) {//Downloading is done in background with the help of doInBackground
        int count;
        try {


            System.out.println("Downloading"); //Downloading
            URL url = new URL(f_url[0]);//taking instance of URL

            URLConnection con = url.openConnection();//opening URL connection and taking it as con
            con.connect();//connecting with URL connection
            int lenghtOfFile = con.getContentLength(); //getting length of file

            InputStream input = new BufferedInputStream(url.openStream(), lenghtOfFile); //input instance is taken as InputStream to check the input of file

            FileOutputStream output = openFileOutput("song.mp3", Context.MODE_PRIVATE);//output is of type FILEOutputStream taken to set the output file


            byte data[] = new byte[lenghtOfFile];//data array is taken of type byte

            while ((count = input.read(data)) != -1)//it will write the file
              output.write(data, 0, count);

            output.flush();//flushing the output i.e instance of FileOutputStream
            output.close();//closing output i.e. instance of FileOutputStream
            input.close();//closing output i.e. instance of InputStream

        } catch (Exception e) {
            Log.e("Occured Error: ", e.getMessage());//setting error messAGE
        }

        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        System.out.println("Downloaded");
        m = MediaPlayer.create(Network.this, Uri.parse("/data/data/com.example.tunebox/files/song.mp3"));
        m.setLooping(false);
        if(m.isPlaying())//if song is already playing then stop is
            m.stop();//stop music
        m.start();
       stop.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Mysong(m);//this method will be called after pressing on stop button
           }
       });

    }


}

    private void Mysong(MediaPlayer m) {//In this method after pressing on Stop button if song was palying then stop it
        if(m.isPlaying()) //if song is playing then
            m.stop();//stop the music
    }

}