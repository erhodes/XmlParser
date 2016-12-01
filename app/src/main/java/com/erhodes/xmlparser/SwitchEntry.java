package com.erhodes.xmlparser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by eric on 29/11/16.
 */
public class SwitchEntry extends Entry {
    TextView mTitleView, mSummaryView;
    LinearLayout mWidgetView;
    Switch mSwitch;

    SwitchEntry(String key, String title, String summary) {
        super(key, title, summary);
    }

    @Override
    protected void onClicked() {
        super.onClicked();
        mSwitch.toggle();

        onChanged(mSwitch.isChecked());
    }

    public boolean isChecked() {
        return mSwitch == null? false : mSwitch.isChecked();
    }

    @Override
    public View getView(View convertView, LayoutInflater inflater, Context context) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.entry_basic, null);
            holder = new ViewHolder();
            mTitleView = (TextView)convertView.findViewById(R.id.title);
            mSummaryView = (TextView)convertView.findViewById(R.id.summary);
            mWidgetView = (LinearLayout)convertView.findViewById(R.id.widget_frame);
            mSwitch = new Switch(context);
            mSwitch.setClickable(false);
            mWidgetView.addView(mSwitch);
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
        mTitleView.setText(mTitle);
        mSummaryView.setText(mSummary);

        return convertView;
    }
}
