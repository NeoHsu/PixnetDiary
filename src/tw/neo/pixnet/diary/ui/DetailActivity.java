/*
 *  Created by Neo Hsu on 2014/6/17.
 *  Copyright (c) 2014 Neo Hsu. All rights reserved.
 */

package tw.neo.pixnet.diary.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import tw.neo.pixnet.diary.R;

public class DetailActivity extends BaseActivity {
    private static final String TAG = "DetailActivity";
    private ProgressBar progressBar;
    private WebView webview;
    private TextView mTitle;
    private Context mCtx;
    String detailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_detail);
        mCtx = DetailActivity.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            detailID = bundle.getString("ID");
            findViews();
        }
    }

    private void findViews() {
        Log.d(TAG, "findViews");
        webview = (WebView) findViewById(R.id.webview);
        mTitle = (TextView) findViewById(R.id.mTitle);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void setWebView(String _data) {
        Log.d(TAG, "setWebView");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("", _data.toString(), "text/html", "UTF-8", "");

    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        new WebViewTask().execute("https://emma.pixnet.cc/blog/articles/" + detailID.toString()
                + "?access_token="
                + mSPM.getAccessToken());

    }

    private class WebViewTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            webview.setVisibility(View.GONE);
        }

        protected String doInBackground(String... urls) {
            if (urls.length > 0) {
                HttpURLConnection conn = null;
                try {
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    URL url = new URL(urls[0]);
                    Log.d(TAG, urls[0]);
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
                    String jsonStringTmp = reader.readLine();
                    reader.close();
                    String jsonString = jsonStringTmp;

                    return jsonString;
                } catch (Exception e) {
                    return "null";
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
            return "null";
        }

        protected void onPostExecute(String data) {
            if (!data.equals("null")) {
                try {
                    JSONObject jsonObj = new JSONObject(data);
                    JSONObject tmp = jsonObj.getJSONObject("article");
                    mTitle.setText(tmp.getString("title"));
                    progressBar.setVisibility(View.GONE);
                    webview.setVisibility(View.VISIBLE);

                    setWebView("<style>img{width:100%}</style>" + tmp.getString("body"));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
}
