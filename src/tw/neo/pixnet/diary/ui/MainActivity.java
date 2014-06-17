/*
 *  Created by Neo Hsu on 2014/6/17.
 *  Copyright (c) 2014 Neo Hsu. All rights reserved.
 */

package tw.neo.pixnet.diary.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tw.neo.pixnet.diary.R;
import tw.neo.pixnet.diary.util.BaseRowItem;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private List<BaseRowItem> rowItems;
    private Context mCtx;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        mCtx = MainActivity.this;
        if (savedInstanceState == null) {
            findViews();

        }
    }

    private void findViews() {
        Log.d(TAG, "findViews");
        listView = (ListView) findViewById(R.id.list);
    }

    private void setListView(String data) throws JSONException {
        Log.d(TAG, "setListView");
        if (rowItems == null) {
            rowItems = new ArrayList<BaseRowItem>();
        }
        rowItems.clear();
        Log.d(TAG, data);
        JSONObject jsonObj = new JSONObject(data);
        if (jsonObj.getInt("total") > 0) {
            JSONArray articles = jsonObj.getJSONArray("articles");
            for (int i = 0; i < articles.length(); i++) {
                JSONObject tmp = articles.getJSONObject(i);
                BaseRowItem item = new BaseRowItem(tmp.getString("title"),
                        tmp.getString("public_at"), tmp.getString("id"));
                rowItems.add(item);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                BaseRowItem item = new BaseRowItem("揭破死一般的重幕，完全不同日子，約同不平者的聲援，被另一邊阻撓著", "1306748820", "61695293");
                rowItems.add(item);
            }
        }
        BaseCustomAdapter adapter = new BaseCustomAdapter(mCtx, rowItems);
        listView.setAdapter(adapter);
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        new GetRequestAsyncTask().execute("https://emma.pixnet.cc/blog/articles?access_token="
                + mSPM.getAccessToken());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private class GetRequestAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(MainActivity.this, "",
                    MainActivity.this.getString(R.string.loading), true);
        }

        @Override
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

        @Override
        protected void onPostExecute(String data) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            if (!data.equals("null")) {
                try {
                    setListView(data);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
