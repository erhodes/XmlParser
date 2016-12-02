package com.erhodes.xmlparser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 11/28/2016.
 */

public class ResourceXmlParser {
    // this is the namespace if the attribute is of the form android:XX="fu"
    public static final String XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
    // this is the namespace if the attribute is of the form XX="bar"
    public static final String XML_NAMESPACE_NONE = null;

    public static final String TAG_ENTRY = "entry";
    public static final String TAG_SWITCH_ENTRY = "switch_entry";
    public static final String TAG_ENTRY_GROUP = "entry_group";
    public static final String TAG_DIALOG_ENTRY = "dialog_entry";

    public static final String ATTRIBUTE_NAME = "name";
    public static final String ATTRIBUTE_KEY = "key";
    public static final String ATTRIBUTE_SUMMARY = "summary";

    private Context mContext;
    private MenuScreen mMenuScreen;

    public List<Entry> parse(Context context, MenuScreen menuScreen, int resId) throws XmlPullParserException, IOException {
        mContext = context;
        mMenuScreen = menuScreen;
        ArrayList<Entry> entries = new ArrayList<>();
        XmlResourceParser parser = context.getResources().getXml(resId);
        parser.next();

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                readStartTag(parser, entries);
            }
            eventType = parser.next();
        }

        return entries;
    }

    private void readStartTag(XmlResourceParser parser, List<Entry> entries) throws XmlPullParserException, IOException {
        if (parser.getName().equals(TAG_ENTRY)) {
            entries.add(readEntry(parser));
        } else if (parser.getName().equals(TAG_SWITCH_ENTRY)) {
            entries.add(readSwitchEntry(parser));
        } else if (parser.getName().equals(TAG_ENTRY_GROUP)) {
            entries.add(readEntryGroup(parser));
        } else if (parser.getName().equals(TAG_DIALOG_ENTRY)) {
            entries.add(readDialogEntry(parser));
        }
    }

    private String getStringResource(XmlResourceParser parser, String namespace, String attribute) {
        String result;
        int resId = parser.getAttributeResourceValue(namespace, attribute, -1);
        if (resId == -1) {
            result = parser.getAttributeValue(namespace, attribute);
        } else {
            result = mContext.getString(resId);
        }
        return result;
    }

    private Entry readEntry(XmlResourceParser parser) {
        String name = getStringResource(parser, XML_NAMESPACE_ANDROID, ATTRIBUTE_NAME);
        String key = parser.getAttributeValue(XML_NAMESPACE_ANDROID, ATTRIBUTE_KEY);
        String summary = getStringResource(parser, XML_NAMESPACE_ANDROID, ATTRIBUTE_SUMMARY);

        return new Entry(key, name, summary, mMenuScreen);
    }

    private Entry readSwitchEntry(XmlResourceParser parser) {
        String name = getStringResource(parser, XML_NAMESPACE_ANDROID, ATTRIBUTE_NAME);
        String key = parser.getAttributeValue(XML_NAMESPACE_ANDROID, ATTRIBUTE_KEY);
        String summary = getStringResource(parser, XML_NAMESPACE_ANDROID, ATTRIBUTE_SUMMARY);

        return new SwitchEntry(key, name, summary, mMenuScreen);
    }

    private Entry readDialogEntry(XmlResourceParser parser) {
        String name = getStringResource(parser, XML_NAMESPACE_ANDROID, ATTRIBUTE_NAME);
        String key = parser.getAttributeValue(XML_NAMESPACE_ANDROID, ATTRIBUTE_KEY);
        String summary = getStringResource(parser, XML_NAMESPACE_ANDROID, ATTRIBUTE_SUMMARY);

        return new DialogEntry(key, name, summary, mContext, mMenuScreen);
    }

    private Entry readEntryGroup(XmlResourceParser parser) throws XmlPullParserException, IOException {
        Log.d("Eric","start tag?" + (parser.getEventType() == XmlPullParser.START_TAG) + "; namespace " + parser.getNamespace() + "; name " + parser.getName());
        parser.require(XmlPullParser.START_TAG, null, TAG_ENTRY_GROUP);
        String name = getStringResource(parser, XML_NAMESPACE_ANDROID, ATTRIBUTE_NAME);
        String key = parser.getAttributeValue(XML_NAMESPACE_ANDROID, ATTRIBUTE_KEY);
        String summary = getStringResource(parser, XML_NAMESPACE_ANDROID, ATTRIBUTE_SUMMARY);

        ArrayList<Entry> entries = new ArrayList<>();
        int eventType = parser.next();
        while (!(eventType == XmlPullParser.END_TAG && parser.getName().equals(TAG_ENTRY_GROUP))) {
            if(eventType == XmlPullParser.START_TAG) {
                readStartTag(parser, entries);
            }
            eventType = parser.next();
        }
        Log.d("Eric","entry group consists of " + entries.size() + " entries");
        return new EntryGroup(key, name, summary, mMenuScreen, entries);
    }
}