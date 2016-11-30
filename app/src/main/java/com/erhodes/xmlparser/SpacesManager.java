package com.erhodes.xmlparser;

import android.content.Context;
import android.os.UserHandle;
import android.util.Log;

/**
 * A stub SpacesManager for testing purposes
 */

public class SpacesManager {

    SpacesManager(Context context) {}

    public void addSpaceRestriction(int userHandle, String restrictionName) {
        Log.d("Eric", "added space restriction " + restrictionName + " to space " + userHandle);
    }

    public void clearSpaceRestriction(int userHandle, String restrictionName) {
        Log.d("Eric", "removed space restriction " + restrictionName + " from space " + userHandle);
    }
}
