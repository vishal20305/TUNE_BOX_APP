package com.example.tunebox;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Rece extends BroadcastReceiver {


    public Rece(){
        super();
    } //constructor

    @Override
    public void onReceive(Context context, Intent intent) {

        String intAct = intent.getAction(); //getting the received action
        if (intAct != null) { //if received action is not null
            String mesg = "UNKNOWN ACTION";// set mesg=UNKNOWN ACTION
            switch (intAct){//switch case is used to checak action
                case Intent.ACTION_BATTERY_OKAY ://if it is battery okay action
                    mesg= "BATTERY IS OKAY";// set mesg=BATTERY IS OKAY
                    break;
                case Intent.ACTION_POWER_CONNECTED://if it is power connected action
                    mesg = "POWER CONNECTED";// set mesg=POWER CONNECTED
                    break;
                case Intent.ACTION_BATTERY_LOW ://if it is battery low action
                    mesg = "LOW BATTERY";// set mesg=LOW BATTERY
                    break;
                case Intent.ACTION_POWER_DISCONNECTED://if it is power disconnected action
                    mesg = "POWER DISCONNECTED";// set mesg=POWER DISCONNECTED
                    break;
            }
            Toast.makeText(context, mesg, Toast.LENGTH_SHORT).show();//Showing the Toast
        }
    }
}
