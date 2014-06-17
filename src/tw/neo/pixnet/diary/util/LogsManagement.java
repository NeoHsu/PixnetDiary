/*
 *  Created by Neo Hsu on 2014/6/17.
 *  Copyright (c) 2014 Neo Hsu. All rights reserved.
 */

package tw.neo.pixnet.diary.util;

import android.util.Log;

public class LogsManagement {
    private static final boolean mEnable = true;
    public void d(String tag, String context) {
        if(mEnable){
            Log.d(tag, context);
        }
    }
}
