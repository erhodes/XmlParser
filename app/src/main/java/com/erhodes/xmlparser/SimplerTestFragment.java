package com.erhodes.xmlparser;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.securespaces.android.spaceapplibrary.entries.DialogEntry;
import com.securespaces.android.spaceapplibrary.entries.Entry;
import com.securespaces.android.spaceapplibrary.entries.MenuScreen;
import com.securespaces.android.spaceapplibrary.entries.SingleButtonEntry;
import com.securespaces.android.spaceapplibrary.entries.SwitchEntry;

/**
 * Created by eric on 08/12/16.
 */
public class SimplerTestFragment extends Fragment implements Entry.OnChangeListener{
    private static final String FIRST_KEY = "switch1";
    private static final String SECOND_KEY = "switch2";
    private static final String FIRST_REGULAR_KEY = "entry1";
    private static final String DIALOG_KEY = "dialog1";
    private static final String BUTTON_KEY = "button1";

    MenuScreen mMenuScreen;
    ListView mListView;

    Entry mFirstEntry, mSecondEntry, mFirstRegularEntry, mDialogEntry, mSubEntry;
    SingleButtonEntry mButtonEntry;

    TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.simpler_test_fragment, container, false);

        mListView = (ListView)view.findViewById(R.id.listView);

        mMenuScreen = new MenuScreen(getActivity());
        mMenuScreen.addEntriesFromXml(R.xml.space_details_uniform);
        mMenuScreen.bindView(mListView);

        Button button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuScreen.removeEntry(mFirstEntry);
            }
        });

        mFirstEntry = findEntry(FIRST_KEY);
        mFirstEntry.setOnChangeListener(this);
        mSecondEntry = findEntry(SECOND_KEY);
        mSecondEntry.setOnChangeListener(this);
        mFirstRegularEntry = findEntry(FIRST_REGULAR_KEY);
        mFirstRegularEntry.setOnChangeListener(this);
        mDialogEntry = findEntry(DIALOG_KEY);
        mDialogEntry.setOnChangeListener(this);
        mButtonEntry = (SingleButtonEntry)findEntry(BUTTON_KEY);
        mButtonEntry.setOnChangeListener(this);
        mButtonEntry.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonEntry.setButtonTextColor(R.color.colorAccent);
            }
        });

        /*
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
*/
        mTextView = (TextView)view.findViewById(R.id.textView);
        mTextView.setEnabled(false);

        return view;
    }

    public Entry findEntry(String key) {
        return mMenuScreen.findEntry(key);
    }

    @Override
    public void onEntryChanged(Entry entry, Object newValue) {
        Log.d("Eric","onEntryChanged: " + entry.getKey());
        if (entry instanceof SwitchEntry) {
            boolean isChecked = (Boolean)newValue;
            String string = newValue.toString();

            if (entry.getKey().equals(FIRST_KEY)) {
                mSecondEntry.setTitle(newValue.toString());
                mSecondEntry.setEnabled(isChecked);
            } else if (entry.getKey().equals(SECOND_KEY)) {
                mTextView.setEnabled(isChecked);
                mTextView.setText(newValue.toString());
                mFirstRegularEntry.setTitle(string);
            }
        }
    }
}
