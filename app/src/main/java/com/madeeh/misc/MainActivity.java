package com.madeeh.misc;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    GPSTracker gps;
    double longitude;
    double latitude;

    TextView tv;
    Button btnshowmyloc;
    Button btndial;
    Button btnsms;
    Button btndirections;
    Button btncamera;
    Button btnvideo;
    Button btncontacts;
    Button btnsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=(TextView)findViewById(R.id.tv_location);
        btnshowmyloc=(Button)findViewById(R.id.btn_showmyloc);
        btndial=(Button)findViewById(R.id.btn_dialmrhafez);
        btnsms=(Button)findViewById(R.id.btn_sms);
        btndirections=(Button)findViewById(R.id.btn_directions);
        btncamera=(Button)findViewById(R.id.btn_camera);
        btnvideo=(Button)findViewById(R.id.btn_video);
        btncontacts =(Button)findViewById(R.id.btn_contacts);
        btnsite=(Button)findViewById(R.id.btn_site);

        getLocation();

        btnshowmyloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String labelLocation="Home";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + latitude + ">,<" + longitude + ">?q=<" + latitude + ">,<" + longitude + ">(" + labelLocation + ")"));

                startActivity(intent);
            }
        });

        btndial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0548761370"));
                startActivity(callIntent);
            }
        });

        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:0548761370");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "Hi Mr. Hafez, this is a test for the Android course!");
                startActivity(it);
            }
        });

        btndirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDirections();
            }
        });

        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

        btnvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.VIDEO_CAMERA");
                startActivity(intent);
            }
        });

        btncontacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

                String all="";
                while (phones.moveToNext())
                {
                    String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    all+=name+", "+ phoneNumber+"\n";
                }
                phones.close();

                Toast.makeText(getApplicationContext(), all, Toast.LENGTH_LONG).show();
            }
        });

        btnsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.ipa.edu.sa";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void getLocation(){



        gps=new GPSTracker(getBaseContext());
        if(gps.canGetLocation()){
            longitude=gps.getLongitude();
            latitude=gps.getLatitude();

            tv.setText( longitude +","+ latitude +")");
        }
    }

    private void getDirections(){
        //21.670268, 39.150578: airport
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?saddr="+ latitude +","+ longitude +"&daddr=21.670268, 39.150578"));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,Prefs.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
