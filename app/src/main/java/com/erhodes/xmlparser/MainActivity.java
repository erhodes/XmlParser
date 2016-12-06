package com.erhodes.xmlparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.securespaces.android.spaceapplibrary.entries.DialogEntry;
import com.securespaces.android.spaceapplibrary.entries.Entry;
import com.securespaces.android.spaceapplibrary.entries.MenuScreen;
import com.securespaces.android.spaceapplibrary.entries.SwitchEntry;
import com.securespaces.android.ssm.SpacesManager;

public class MainActivity extends AppCompatActivity implements Entry.OnChangeListener {
    MenuScreen mMenuScreen;
    SpacesManager mSpacesManager;

    Entry mFirstEntry, mBluetoothEntry, mDialogEntry, mSubDialogEntry, mSubEntry;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpacesManager = new SpacesManager(this);

        mMenuScreen = (MenuScreen)findViewById(R.id.listView);
        mMenuScreen.addEntriesFromXml(R.xml.space_details);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuScreen.removeEntry(mFirstEntry);
            }
        });

        mBluetoothEntry = mMenuScreen.findEntry("bluetooth_restriction");
        mBluetoothEntry.setOnChangeListener(this);

        mFirstEntry = mMenuScreen.findEntry("test_group");

        mDialogEntry = mMenuScreen.findEntry("dialog_key");
        if (mDialogEntry != null) {
            mDialogEntry.setOnChangeListener(this);
        }

        mSubDialogEntry = mMenuScreen.findEntry("subswitchkey");
        mSubDialogEntry.setOnChangeListener(this);

        mSubEntry = mMenuScreen.findEntry("subentry");

        mTextView = (TextView)findViewById(R.id.textView);
        mTextView.setEnabled(false);
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
            mTextView.setEnabled(isChecked);
            mSubDialogEntry.setEnabled(isChecked);
            if (isChecked) {
                //mSpacesManager.addSpaceRestriction(0, entry.mKey);
            } else {
                //mSpacesManager.clearSpaceRestriction(0, entry.mKey);
            }
        } else if (entry instanceof DialogEntry) {
            String value = (String)newValue;
            if (value.equals("no")) {
                mBluetoothEntry.setEnabled(false);
            } else {
                mBluetoothEntry.setEnabled(true);
            }
            mMenuScreen.invalidate();
        }
    }
}
