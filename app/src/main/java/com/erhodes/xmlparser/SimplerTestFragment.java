package com.erhodes.xmlparser;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.securespaces.android.spaceapplibrary.entries.DialogEntry;
import com.securespaces.android.spaceapplibrary.entries.Entry;
import com.securespaces.android.spaceapplibrary.entries.MenuScreen;
import com.securespaces.android.spaceapplibrary.entries.SwitchEntry;

/**
 * Created by eric on 08/12/16.
 */
public class SimplerTestFragment extends Fragment implements Entry.OnChangeListener{
    MenuScreen mMenuScreen;

    Entry mFirstEntry, mBluetoothEntry, mDialogEntry, mSubDialogEntry, mSubEntry;
    TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.simpler_test_fragment, container, false);

        mMenuScreen = (MenuScreen)view.findViewById(R.id.listView);
        mMenuScreen.addEntriesFromXml(R.xml.space_details);

        Button button = (Button)view.findViewById(R.id.button);
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

        mTextView = (TextView)view.findViewById(R.id.textView);
        mTextView.setEnabled(false);

        return view;
    }

    @Override
    public void onEntryChanged(Entry entry, Object newValue) {
        if (entry instanceof SwitchEntry) {
            boolean isChecked = (Boolean)newValue;
            mTextView.setEnabled(isChecked);
            mSubDialogEntry.setEnabled(isChecked);
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
