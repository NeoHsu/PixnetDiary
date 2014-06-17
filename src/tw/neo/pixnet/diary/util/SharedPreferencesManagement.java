/*
 *  Created by Neo Hsu on 2014/6/17.
 *  Copyright (c) 2014 Neo Hsu. All rights reserved.
 */

package tw.neo.pixnet.diary.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManagement {
    private static final LogsManagement Log = new LogsManagement();
    private static final String TAG = "SharedPreferencesManagement";
    private static final String SP_TAG = "DIARY";
    private static final int SP_MODE = Context.MODE_PRIVATE;
    private Context mCtx;
    SharedPreferences mSP;
    SharedPreferences.Editor mPE;

    public SharedPreferencesManagement(Context mCtx) {
        Log.d(TAG, "SharedPreferencesManagement Init");
        this.mCtx = mCtx;
        this.mSP = this.mCtx.getSharedPreferences(SP_TAG, SP_MODE);
        this.mPE = this.mSP.edit();
    }
}
