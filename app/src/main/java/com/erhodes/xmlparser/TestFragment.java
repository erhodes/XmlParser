package com.erhodes.xmlparser;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.securespaces.android.spaceapplibrary.entries.DialogEntry;
import com.securespaces.android.spaceapplibrary.entries.Entry;
import com.securespaces.android.spaceapplibrary.entries.MenuScreen;
import com.securespaces.android.spaceapplibrary.entries.SwitchEntry;
import com.securespaces.android.ssm.SpaceRestrictions;
import com.securespaces.android.ssm.SpacesManager;
import com.securespaces.android.ssm.SystemUtils;

/**
 * Created by eric on 06/12/16.
 */
public class TestFragment extends Fragment implements Entry.OnChangeListener {
    /**
     * Space category keys
     **/
    private static final String PREF_CAT_SPACE_MGMT = "prefs_space_mgmt";
    private static final String PREF_CAT_CROSS_SPACE = "prefs_cross_space";
    private static final String PREF_CAT_CONNECTIONS = "prefs_connections";
    private static final String PREF_CAT_PHONE = "prefs_phone";
    private static final String PREF_CAT_RECORDING = "prefs_recording";
    private static final String PREF_CAT_APPLICATIONS = "prefs_applications";
    private static final String PREF_CAT_DEBUGGING = "prefs_debug";

    /**
     * Individual Preference Keys
     **/
    private static final String PREF_SPACE_NAME = "pref_space_name";

    private static final String PREF_CROSS_SPACE_NOTIFICATIONS = "pref_cross_space_notifications";
    private static final String PREF_CROSS_SPACE_SIM_CARDS = "pref_cross_space_sim_cards";

    private static final String PREF_CONNECTIONS_PRIVATE_APNS = "pref_connections_private_apns";

    private static final String PREF_SPACE_MGMT_ADD_USERS = SpaceRestrictions.DISALLOW_ADD_USER;
    private static final String PREF_SPACE_MGMT_ADD_SPACES = SpaceRestrictions.SS_DISALLOW_ADD_SPACE;
    private static final String PREF_SPACE_MGMT_DISALLOW_PHYSICAL_MEDIA = SpaceRestrictions.DISALLOW_MOUNT_PHYSICAL_MEDIA;
    private static final String PREF_SPACE_MGMT_PASSWORD_RECOVERY = "pref_password_recovery";
    private static final String PREF_SPACE_MGMT_EXIT_ON_SLEEP = "pref_exit_on_sleep";
    private static final String PREF_SPACE_MGMT_STOP_SPACE_ON_EXIT = SpaceRestrictions.SS_ENSURE_STOP_SPACE_ON_EXIT;
    private static final String PREF_SPACE_MGMT_LOCK_OTHER_SPACES = SpaceRestrictions.SS_ENSURE_LOCK_OTHER_SPACES_ON_ENTER;
    private static final String PREF_SPACE_MGMT_SCREEN_LOCK = SpaceRestrictions.SS_DISABLE_SCREEN_LOCK;
    private static final String PREF_SPACE_MGMT_AUTO_CLEAN = SpaceRestrictions.SS_ENSURE_SPACE_AUTO_CLEAN;

    private static final String PREF_RECORDING_CAMERA = SpaceRestrictions.SS_DISALLOW_CAMERA_ACCESS;

    private static final String PREF_APPLICATIONS_INSTALL_APPS = SpaceRestrictions.DISALLOW_INSTALL_APPS;
    private static final String PREF_APPLICATIONS_UNKNOWN_SOURCES = SpaceRestrictions.DISALLOW_INSTALL_UNKNOWN_SOURCES;

    private static final String PREF_REGISTER_OWNER_SPACE = "pref_register_owner_space";
    private static final String PREF_DELETE_SPACE = "pref_delete_space";
    private static final String PREF_DELETE_USER_DATA = "pref_delete_user_data";

    private static final String PREF_TEST = "test";
    private static final String PREF_SWITCH_TEST = "switch_test";

    MenuScreen mMenuScreen;
    SpacesManager mSpacesManager;

    Entry mTestEntry, mSwitchTestEntry, mBluetoothEntry, mDialogEntry, mSubDialogEntry, mSubEntry;
    Entry mDisallowAddUser, mDisallowAddSpace, mStopOnExit;
    TextView mTextView;

    private Entry findEntry(String key) {
        return mMenuScreen.findEntry(key);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);
        mMenuScreen = (MenuScreen) view.findViewById(R.id.listView);
        mMenuScreen.addEntriesFromXml(R.xml.space_details_2);

        mSpacesManager = new SpacesManager(getActivity());

        mTestEntry = findEntry(PREF_TEST);
        mTestEntry.setOnChangeListener(this);
        mSwitchTestEntry = findEntry(PREF_SWITCH_TEST);
        mSwitchTestEntry.setOnChangeListener(this);
        mDisallowAddUser = findEntry(PREF_SPACE_MGMT_ADD_USERS);
        mDisallowAddUser.setOnChangeListener(this);
        mDisallowAddSpace = findEntry(PREF_SPACE_MGMT_ADD_SPACES);
        mDisallowAddSpace.setOnChangeListener(this);
        mStopOnExit = findEntry(PREF_SPACE_MGMT_STOP_SPACE_ON_EXIT);

        return view;
    }

    @Override
    public void onEntryChanged(Entry entry, Object newValue) {
        if (entry instanceof SwitchEntry) {
            boolean checked = (Boolean)newValue;
            if (entry.getKey().equals(PREF_SPACE_MGMT_ADD_USERS)) {
                mDisallowAddSpace.setEnabled(checked);
            } else if (entry.getKey().equals(PREF_SPACE_MGMT_ADD_SPACES) || entry.getKey().equals(PREF_SWITCH_TEST)) {
                mTestEntry.setEnabled(checked);
                mTestEntry.setSummary(newValue.toString());
            }
        } else if (entry instanceof DialogEntry) {
            String value = (String)newValue;
            if (value.equals("no")) {
                mBluetoothEntry.setEnabled(false);
            } else {
                mBluetoothEntry.setEnabled(true);
            }
            mMenuScreen.invalidate();
        }
    }
}
