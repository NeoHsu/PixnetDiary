/*
 *  Created by Neo Hsu on 2014/6/17.
 *  Copyright (c) 2014 Neo Hsu. All rights reserved.
 */

package tw.neo.pixnet.diary.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import tw.neo.pixnet.diary.util.LogsManagement;
import tw.neo.pixnet.diary.util.SharedPreferencesManagement;

public class BaseActivity extends ActionBarActivity {
    private static final String TAG = "BaseActivity";
    protected static final LogsManagement Log = new LogsManagement();
    protected SharedPreferencesManagement mSPM;
    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        mCtx = BaseActivity.this;
        mSPM = new SharedPreferencesManagement(mCtx);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
