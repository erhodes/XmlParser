package com.erhodes.xmlparser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * An entry that contains a title and a group of entries.
 */

public class EntryGroup extends Entry {
    private List<Entry> mSubEntries = new ArrayList<>();

    EntryGroup(String key, String title, String summary, MenuScreen menuScreen, List<Entry> entries) {
        super(key, title, summary, menuScreen);
        mSubEntries = entries;
    }

    public void addEntries(List<Entry> entries) {
        mSubEntries = entries;
    }

    /**
     * Returns the entry with the given key, or null if no such entry can be found.
     * @param key
     * @return
     */
    public Entry findEntry(String key) {
        //Log.d("Eric","checking subgroup for entry " + key);
        for (Entry entry : mSubEntries) {
            //Log.d("Eric","checking entry " + entry.mKey);
            if (entry.mKey.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    public boolean removeEntry(Entry entry) {
        boolean removed = mSubEntries.remove(entry);
        if (removed) {
            mMenuScreen.update();
        }
        return removed;
    }

    @Override
    public View getView(View convertView, LayoutInflater inflater, Context context) {
        if (convertView == null) {
            Log.d("Eric","creating view and there's " + mSubEntries.size() + " entries");
            convertView = inflater.inflate(R.layout.entry_heading, null);
            for (Entry entry : mSubEntries) {
                ((LinearLayout)convertView).addView(entry.getView(null, inflater, context));
            }
        }

        //mAdapter = new EntryAdapter(context, mSubEntries);
        //mListView.setAdapter(mAdapter);
        return convertView;
    }
}
