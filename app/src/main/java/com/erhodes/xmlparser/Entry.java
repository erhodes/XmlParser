package com.erhodes.xmlparser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Eric on 11/28/2016.
 */

public class Entry {
    protected MenuScreen mMenuScreen;
    protected String mKey, mTitle, mSummary;
    protected OnClickListener mClickListener;
    protected OnChangeListener mChangeListener;
    protected TextView mTitleView, mSummaryView;
    protected View mView, mDivider;


    Entry(String key, String title, String summary, MenuScreen menuScreen) {
        mKey = key;
        mTitle = title;
        mSummary = summary;
        mMenuScreen = menuScreen;
    }

    public void setEnabled(boolean enabled) {
        Log.d("Eric","setting entry to " + enabled);
        if (mView != null) {
            mView.setEnabled(enabled);
        }
        if (mTitleView != null) {
            mTitleView.setEnabled(enabled);
        }
        if (mSummaryView != null) {
            mSummaryView.setEnabled(enabled);
        }
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

    protected void onClicked() {
        if (mClickListener != null) {
            mClickListener.onEntryClicked();
        }
    }

    protected void onChanged(Object newValue) {
        if (mChangeListener != null) {
            mChangeListener.onEntryChanged(this, newValue);
        }
    }

    /**
     * Set the visibility of the divider. Should be one of {@link View#GONE}
     * @param visibility
     */
    public void setDividerVisibility(int visibility) {
        mDivider.setVisibility(visibility);
    }

    protected void initializeViews(View convertView) {
        mTitleView = (TextView)convertView.findViewById(R.id.title);
        mSummaryView = (TextView)convertView.findViewById(R.id.summary);
        mView = convertView;
        mDivider = convertView.findViewById(R.id.divider);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicked();
            }
        });
    }

    public View getView(View convertView, LayoutInflater inflater, Context context) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.entry_basic, null);
            initializeViews(convertView);
        }
        mTitleView.setText(mTitle);
        mSummaryView.setText(mSummary);

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
