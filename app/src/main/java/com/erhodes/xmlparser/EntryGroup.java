package com.erhodes.xmlparser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * An entry that contains a title and a group of entries.
 */

public class EntryGroup extends Entry {
    private List<Entry> mSubEntries = new ArrayList<>();
    private ListView mListView;
    private EntryAdapter mAdapter;

    EntryGroup(String key, String title, String summary, List<Entry> entries) {
        super(key, title, summary);
        mSubEntries = entries;
    }

    public void addEntries(List<Entry> entries) {
        mSubEntries = entries;
    }

    @Override
    public View getView(View convertView, LayoutInflater inflater, Context context) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.entry_heading, null);
            mListView = (ListView)convertView.findViewById(R.id.subEntryListView);
        }
        mAdapter = new EntryAdapter(context, mSubEntries);
        mListView.setAdapter(mAdapter);
        return convertView;
    }
}
