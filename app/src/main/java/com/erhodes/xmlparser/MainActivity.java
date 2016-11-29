package com.erhodes.xmlparser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.annotation.XmlRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    EntryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.listView);


        ResourceXmlPuller resourceParser = new ResourceXmlPuller();
        try {
            List<Entry> entries = resourceParser.parse(this);
            Log.d("Eric","read in " + entries.size() + " entries");
            mAdapter = new EntryAdapter(this, 0, entries);
            mListView.setAdapter(mAdapter);
        } catch (XmlPullParserException | IOException e) {
            Log.d("Eric","that's a problem");
            e.printStackTrace();
        }
    }

    public void pullUsingRaw() {
        DetailsXmlParser parser = new DetailsXmlParser();
        //XmlResourceParser xmlParser = getResources().getXml(R.xml.space_details);
        InputStream stream = getResources().openRawResource(R.raw.space_details);
        try {
            List<Entry> entries = parser.parse(stream);
            Log.d("Eric","read in " + entries.size() + " entries");
            mAdapter = new EntryAdapter(this, 0, entries);
            mListView.setAdapter(mAdapter);
        } catch (XmlPullParserException | IOException e) {
            Log.d("Eric","that's a problem");
            e.printStackTrace();
        }
    }

    public class EntryAdapter extends ArrayAdapter<Entry> {


        public EntryAdapter(Context context, int resource, List<Entry> entries) {
            super(context, resource, entries);
        }

        private class ViewHolder {
            TextView textView;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.entry_view, null);
                holder = new ViewHolder();
                holder.textView = (TextView)convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.textView.setText(getItem(position).toString());

            return convertView;
        }
    }
}
