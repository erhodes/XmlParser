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
        mAdapter.notifyDataSetChanged();
        invalidate();
    }

    public void removeEntry(Entry entry) {
        mEntries.remove(entry);
        mAdapter = new EntryAdapter(getContext(), mEntries);
        setAdapter(mAdapter);
    }

    /**
     * Returns the entry with the given key, or null if no such entry can be found.
     * @param key
     * @return
     */
    public Entry findEntry(String key) {
        for (Entry entry : mEntries) {
            if (entry.mKey.equals(key)) {
                return entry;
            }
        }
        return null;
    }
}
