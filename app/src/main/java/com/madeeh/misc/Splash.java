package com.madeeh.misc;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Splash extends ActionBarActivity {

    MediaPlayer song;
    boolean music=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        music=preferences.getBoolean("music",true);


        song=MediaPlayer.create(Splash.this,R.raw.song);

        if(music) {
            song.start();
        }

        Thread timer=new Thread(){

            public void run(){

                try {
                    if(music) {
                        sleep(11000);
                    }
                    else
                    {
                        sleep(3000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(music) {
            song.release();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }
}
