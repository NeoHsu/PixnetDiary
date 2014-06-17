/*
 *  Created by Neo Hsu on 2014/6/17.
 *  Copyright (c) 2014 Neo Hsu. All rights reserved.
 */

package tw.neo.pixnet.diary.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import tw.neo.pixnet.diary.R;
import tw.neo.pixnet.diary.util.LogsManagement;

public class LoginActivity extends Activity {
    private static final LogsManagement Log = new LogsManagement();
    private static final String TAG = "LoginActivity";
    private Context mCtx;
    private LinearLayout loginLayout;
    private TextView login;
    private ProgressBar progressBar;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_login);
        mCtx = LoginActivity.this;
        if (savedInstanceState == null) {
            findViews();
            setClick();
        }
    }

    private void findViews() {
        Log.d(TAG, "findViews");
        loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
        login = (TextView) findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webview = (WebView) findViewById(R.id.webview);
    }

    private void setClick() {
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mCtx, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        loginLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        webview.setVisibility(View.GONE);

    }

}
