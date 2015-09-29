package com.madeeh.misc;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;


public class Prefs extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }

}
