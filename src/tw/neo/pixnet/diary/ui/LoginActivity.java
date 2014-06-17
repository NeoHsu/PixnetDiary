/*
 *  Created by Neo Hsu on 2014/6/17.
 *  Copyright (c) 2014 Neo Hsu. All rights reserved.
 */

package tw.neo.pixnet.diary.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import tw.neo.pixnet.diary.R;
import tw.neo.pixnet.diary.util.LogsManagement;
import tw.neo.pixnet.diary.util.SharedPreferencesManagement;

public class LoginActivity extends Activity {
    private static final LogsManagement Log = new LogsManagement();
    private SharedPreferencesManagement mSPM;
    private static final String TAG = "LoginActivity";
    private Context mCtx;
    private LinearLayout loginLayout;
    private TextView login;
    private ProgressBar progressBar;
    private WebView webview;
    private ProgressDialog pd;

    private static final String REDIRECT_URI = "https://neo.demo.com";
    private static final String API_KEY = "abcc70dc246d80c2343d40d24b4ab5e5";
    private static final String SECRET_KEY = "8bba9018d66321e563504997a45da1be";
    private static final String STATE_PARAM = "code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_login);
        mCtx = LoginActivity.this;
        mSPM = new SharedPreferencesManagement(mCtx);
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
                loginLayout.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
                webview.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String authorizationUrl) {
                        if (authorizationUrl.startsWith(REDIRECT_URI)) {
                            Log.d("Authorize", "");
                            Uri uri = Uri.parse(authorizationUrl);
                            Log.d("URI Query", uri.getQuery());
                            String stateToken = uri.getQueryParameter(STATE_PARAM);
                            if (stateToken == null) {
                                Log.d("Authorize", "State token doesn't match");
                                return true;
                            }

                            String authorizationToken =
                                    uri.getQueryParameter(STATE_PARAM);
                            if (authorizationToken == null) {
                                Log.d("Authorize",
                                        "The user doesn't allow authorization.");
                                return true;
                            }
                            Log.d("Authorize", "Auth token received: " +
                                    authorizationToken);

                            String[] accessTokenUrl = {
                                    stateToken, REDIRECT_URI,
                                    API_KEY,
                                    SECRET_KEY
                            };
                            new GetRequestAsyncTask().execute(accessTokenUrl);

                        } else {
                            Log.d("Authorize", "Redirecting to: " + authorizationUrl);
                            webview.loadUrl(authorizationUrl);
                        }
                        return true;
                    }
                });
                String tmp = "https://emma.pixnet.cc/oauth2/authorize?redirect_uri=" + REDIRECT_URI
                        + "&client_id=" + API_KEY + "&response_type=" + STATE_PARAM;
                webview.loadUrl(tmp);
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

    private class GetRequestAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(LoginActivity.this, "",
                    LoginActivity.this.getString(R.string.loading), true);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            if (urls.length > 0) {
                HttpURLConnection conn = null;
                try {
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    URL url = new URL(
                            "https://emma.pixnet.cc/oauth2/grant?grant_type=authorization_code&code="
                                    + urls[0].toString() +
                                    "&redirect_uri=" + urls[1].toString() +
                                    "&client_id=" + urls[2].toString() +
                                    "&client_secret=" + urls[3].toString());
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            conn.getInputStream(), "UTF-8"));
                    String tmp = reader.readLine();
                    reader.close();
                    String jsonString = tmp;
                    JSONObject jsonObj = new JSONObject(jsonString);
                    mSPM.setAccessToken(jsonObj.getString("access_token"));
                    return true;
                } catch (Exception e) {
                    return false;
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean status) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            if (status) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }
        }
    }

}
