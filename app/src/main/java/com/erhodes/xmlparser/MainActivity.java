package com.erhodes.xmlparser;

import android.content.Context;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Entry.OnChangeListener{
    MenuScreen mMenuScreen;
    SpacesManager mSpacesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpacesManager = new SpacesManager(this);

        mMenuScreen = (MenuScreen)findViewById(R.id.listView);
        mMenuScreen.addEntriesFromXml(R.xml.space_details);

        Entry bluetoothEntry = mMenuScreen.findEntry("bluetooth_restriction");
        bluetoothEntry.setOnChangeListener(this);
/*
        ResourceXmlParser resourceParser = new ResourceXmlParser();
        try {
            List<Entry> entries = resourceParser.parse(this);
            Log.d("Eric","read in " + entries.size() + " entries");
            mAdapter = new EntryAdapter(this, 0, entries);
            mListView.setAdapter(mAdapter);
        } catch (XmlPullParserException | IOException e) {
            Log.d("Eric","that's a problem");
            e.printStackTrace();
        }
        */
    }

    @Override
    public void onEntryChanged(Entry entry, Object newValue) {
        if (entry instanceof SwitchEntry) {
            boolean isChecked = (Boolean)newValue;
            if (isChecked) {
                mSpacesManager.addSpaceRestriction(0, entry.mKey);
            } else {
                mSpacesManager.clearSpaceRestriction(0, entry.mKey);
            }
        }
    }
/*
    public void pullUsingRaw() {
        DetailsXmlParser parser = new DetailsXmlParser();
        //XmlResourceParser xmlParser = getResources().getXml(R.xml.space_details);
        InputStream stream = getResources().openRawResource(R.raw.space_details);
        try {
            List<Entry> entries = parser.parse(stream);
            Log.d("Eric","read in " + entries.size() + " entries");
            mAdapter = new EntryAdapter(this, 0, entries);
            mListView.setAdapter(mAdapter);
        } catch (XmlPullParserException | IOException e) {
            Log.d("Eric","that's a problem");
            e.printStackTrace();
        }
    }
*/

}
