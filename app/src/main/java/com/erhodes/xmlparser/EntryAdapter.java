package com.erhodes.xmlparser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class EntryAdapter extends ArrayAdapter<Entry> {
    private LayoutInflater mLayoutInflater;

    public EntryAdapter(Context context, List<Entry> entries) {
        super(context, 0, entries);
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(convertView, mLayoutInflater, getContext());
    }
}