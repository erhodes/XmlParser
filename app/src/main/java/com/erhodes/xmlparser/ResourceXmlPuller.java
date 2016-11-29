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

public class ResourceXmlPuller {
    // this is the namespace if the attribute is of the form android:XX="fu"
    public static final String XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
    // this is the namespace if the attribute is of the form XX="bar"
    public static final String XML_NAMESPACE_NONE = null;

    public static final String TAG_ENTRY = "entry";
    public static final String TAG_SWITCH_ENTRY = "switch_entry";

    public static final String ATTRIBUTE_NAME = "name";
    public static final String ATTRIBUTE_KEY = "key";
    public static final String ATTRIBUTE_SUMMARY = "summary";

    private Context mContext;

    public List<Entry> parse(Context context) throws XmlPullParserException, IOException {
        mContext = context;
        ArrayList<Entry> entries = new ArrayList<>();
        XmlResourceParser parser = context.getResources().getXml(R.xml.space_details);
        parser.next();
        StringBuffer stringBuffer = new StringBuffer();

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                stringBuffer.append("--- Start XML ---");
            } else if(eventType == XmlPullParser.START_TAG) {
                //Log.d("Eric","start tag with name " + parser.getName() + " with attributes " + parser.getAttributeCount());
                if (parser.getName().equals(TAG_ENTRY)) {
                    Log.d("Eric","entry with name " + parser.getAttributeName(0) + " and value " + parser.getAttributeValue(XML_NAMESPACE_ANDROID, "name"));
                    Log.d("Eric","namespace " + parser.getNamespace());
                    Log.d("Eric","attribute namespace " + parser.getAttributeNamespace(0));
                    entries.add(readEntry(parser));
                } else if (parser.getName().equals(TAG_SWITCH_ENTRY)) {
                    entries.add(readSwitchEntry(parser));
                }
                stringBuffer.append("\nSTART_TAG: "+parser.getName());
            } else if(eventType == XmlPullParser.END_TAG) {
                stringBuffer.append("\nEND_TAG: "+parser.getName());
            } else if(eventType == XmlPullParser.TEXT) {
                stringBuffer.append("\nTEXT: "+parser.getText());
            }
            eventType = parser.next();
        }
        stringBuffer.append("\n--- End XML ---");

        return entries;
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

        return new Entry(key, name, summary);
    }

    private Entry readSwitchEntry(XmlResourceParser parser) {
        String name = getStringResource(parser, XML_NAMESPACE_ANDROID, ATTRIBUTE_NAME);
        String key = parser.getAttributeValue(XML_NAMESPACE_ANDROID, ATTRIBUTE_KEY);
        String summary = getStringResource(parser, XML_NAMESPACE_ANDROID, ATTRIBUTE_SUMMARY);

        return new SwitchEntry(key, name, summary);
    }
}