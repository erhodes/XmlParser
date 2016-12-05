package com.erhodes.xmlparser;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Eric on 11/30/2016.
 */

public class MenuScreen extends ListView {
    private static final String TAG = MenuScreen.class.getSimpleName();

    private List<Entry> mEntries;
    private EntryAdapter mAdapter;

    public MenuScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setDivider(null);
    }

    public void addEntriesFromXml(int resId) {
        ResourceXmlParser parser = new ResourceXmlParser();
        try {
            mEntries = parser.parse(getContext(), this, resId);
            mAdapter = new EntryAdapter(getContext(), mEntries);
            setAdapter(mAdapter);
        } catch (XmlPullParserException | IOException e) {
            Log.d(TAG,"Error inflating xml");
            e.printStackTrace();
        }
    }

    public void update() {
        Log.d("Eric","updated has been called");
        mAdapter = new EntryAdapter(getContext(), mEntries);
        setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
//        invalidate();
//        invalidateViews();
    }

    public boolean removeEntry(Entry entry) {
        boolean removed = mEntries.remove(entry);
        if (removed) {
            mAdapter = new EntryAdapter(getContext(), mEntries);
            setAdapter(mAdapter);
        }
        return removed;
    }

    /**
     * Returns the entry with the given key, or null if no such entry can be found.
     * @param key
     * @return
     */
    public Entry findEntry(String key) {
        for (Entry entry : mEntries) {
            //Log.d("Eric","checking entry " + entry.mKey);
            if (entry instanceof EntryGroup) {
                //Log.d("Eric","this one is a group, check it's entries as well");
                Entry subEntry = ((EntryGroup) entry).findEntry(key);
                if (subEntry != null) {
                    return subEntry;
                }
            } else if (entry.mKey.equals(key)) {
                return entry;
            }
        }
        return null;
    }
}
