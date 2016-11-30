package com.erhodes.xmlparser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Eric on 11/28/2016.
 */

public class Entry {
    protected String mKey, mTitle, mSummary;
    protected OnClickListener mClickListener;
    protected OnChangeListener mChangeListener;



    Entry(String key, String title, String summary) {
        mKey = key;
        mTitle = title;
        mSummary = summary;
    }

    public void setOnClickListener(OnClickListener listener) {
        mClickListener = listener;
    }

    public OnClickListener getOnClickListener() {
        return mClickListener;
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mChangeListener = listener;
    }

    public OnChangeListener getOnChangeListener() {
        return mChangeListener;
    }

    public class ViewHolder {
        TextView titleView, summaryView;
    }

    protected void onClicked() {
        if (mClickListener != null) {
            mClickListener.onEntryClicked();
        }
    }

    public View getView(View convertView, LayoutInflater inflater, Context context) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.entry_basic, null);
            holder = new ViewHolder();
            holder.titleView = (TextView)convertView.findViewById(R.id.title);
            holder.summaryView = (TextView)convertView.findViewById(R.id.summary);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicked();
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.titleView.setText(mTitle);
        holder.summaryView.setText(mSummary);

        return convertView;
    }

    public String toString() {
        return String.format("Entry with key: %s; title: %s; summary: %s", mKey, mTitle, mSummary);
    }

    public interface OnClickListener {
        void onEntryClicked();
    }
    public interface OnChangeListener {
        void onEntryChanged(Entry entry, Object newValue);
    }
}
