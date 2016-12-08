package com.erhodes.xmlparser;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.securespaces.android.spaceapplibrary.entries.DialogEntry;
import com.securespaces.android.spaceapplibrary.entries.Entry;
import com.securespaces.android.spaceapplibrary.entries.MenuScreen;
import com.securespaces.android.spaceapplibrary.entries.SwitchEntry;
import com.securespaces.android.ssm.SpacesManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new SimplerTestFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, "test")
                .commit();
    }
}
