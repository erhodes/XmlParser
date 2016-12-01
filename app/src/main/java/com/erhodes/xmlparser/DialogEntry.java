package com.erhodes.xmlparser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Eric on 11/30/2016.
 */

public class DialogEntry extends Entry {
    Context mContext;
    MenuScreen mMenuScreen;

    DialogEntry(String key, String title, String summary, Context context, MenuScreen menuScreen) {
        super(key, title, summary);
        mContext = context;
        mMenuScreen = menuScreen;
    }

    @Override
    protected void onChanged(Object newValue) {
        mSummary = (String)newValue;
        super.onChanged(newValue);
        mMenuScreen.update();
    }

    /**
     * Override this method to change what kind of dialog is opened. It is important
     * that the dialog call the Entry's onChanged method, passing in the user's selection.
     * @return
     */
    protected Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogEntry.this.onChanged("yes");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogEntry.this.onChanged("no");
            }
        });
        return builder.create();
    }

    @Override
    protected void onClicked() {
        super.onClicked();

        createDialog().show();
    }
}
