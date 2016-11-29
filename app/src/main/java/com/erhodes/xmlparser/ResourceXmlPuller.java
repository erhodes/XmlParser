package com.erhodes.xmlparser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 11/28/2016.
 */

public class ResourceXmlPuller {
    // this is the namespace if the attribute is of the form android:XX
    public static final String ANDROID_XML_NAMESPACE = "http://schemas.android.com/apk/res/android";

    public List<Entry> parse(Context context) throws XmlPullParserException, IOException {
        ArrayList<Entry> entries = new ArrayList<>();
        XmlResourceParser parser = context.getResources().getXml(R.xml.space_details);
        parser.next();
        StringBuffer stringBuffer = new StringBuffer();

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if(eventType == XmlPullParser.START_DOCUMENT)
            {
                stringBuffer.append("--- Start XML ---");
            }
            else if(eventType == XmlPullParser.START_TAG)
            {
                //Log.d("Eric","start tag with name " + parser.getName() + " with attributes " + parser.getAttributeCount());
                if (parser.getName().equals("entry")) {
                    Log.d("Eric","entry with name " + parser.getAttributeName(0) + " and value " + parser.getAttributeValue(ANDROID_XML_NAMESPACE, "name"));
                    Log.d("Eric","namespace " + parser.getNamespace());
                    Log.d("Eric","attribute namespace " + parser.getAttributeNamespace(0));
                    entries.add(readEntry(parser));
                }
                stringBuffer.append("\nSTART_TAG: "+parser.getName());
            }
            else if(eventType == XmlPullParser.END_TAG)
            {
                stringBuffer.append("\nEND_TAG: "+parser.getName());
            }
            else if(eventType == XmlPullParser.TEXT)
            {
                stringBuffer.append("\nTEXT: "+parser.getText());
            }
            eventType = parser.next();
        }
        stringBuffer.append("\n--- End XML ---");
        //Log.d("Eric",stringBuffer.toString());
        //return stringBuffer.toString();

        return entries;
    }

    private Entry readEntry(XmlResourceParser parser) {
        String name = parser.getAttributeValue(ANDROID_XML_NAMESPACE, "name");
        String key = "key";
        String value = "value";

        return new Entry(name, key, value);
    }
}
