package com.erhodes.xmlparser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by eric on 29/11/16.
 */
public class SwitchEntry extends Entry {
    LinearLayout mWidgetView;
    Switch mSwitch;

    SwitchEntry(String key, String title, String summary, MenuScreen menuScreen) {
        super(key, title, summary, menuScreen);
    }

    @Override
    protected void onClicked() {
        super.onClicked();
        mSwitch.toggle();

        onChanged(mSwitch.isChecked());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (mSwitch != null) {
            mSwitch.setEnabled(enabled);
        }
    }

    public boolean isChecked() {
        return mSwitch == null? false : mSwitch.isChecked();
    }

    @Override
    public View getView(View convertView, LayoutInflater inflater, Context context) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.entry_basic, null);
            mTitleView = (TextView)convertView.findViewById(R.id.title);
            mSummaryView = (TextView)convertView.findViewById(R.id.summary);
            mWidgetView = (LinearLayout)convertView.findViewById(R.id.widget_frame);
            mView = convertView;
            mSwitch = new Switch(context);
            mSwitch.setClickable(false);
            mWidgetView.addView(mSwitch);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicked();
                }
            });
        }
        mTitleView.setText(mTitle);
        mSummaryView.setText(mSummary);

        return convertView;
    }
}
