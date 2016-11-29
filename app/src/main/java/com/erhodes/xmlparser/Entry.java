package com.erhodes.xmlparser;

/**
 * Created by Eric on 11/28/2016.
 */

public class Entry {
    String mKey, mTitle, mSummary;

    Entry(String key, String title, String summary) {
        mKey = key;
        mTitle = title;
        mSummary = summary;
    }

    public String toString() {
        return String.format("Entry with key: %s; title: %s; summary: %s", mKey, mTitle, mSummary);
    }
}
