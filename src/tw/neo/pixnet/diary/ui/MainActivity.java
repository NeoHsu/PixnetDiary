/*
 *  Created by Neo Hsu on 2014/6/17.
 *  Copyright (c) 2014 Neo Hsu. All rights reserved.
 */

package tw.neo.pixnet.diary.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import tw.neo.pixnet.diary.R;
import tw.neo.pixnet.diary.util.BaseRowItem;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private List<BaseRowItem> rowItems;
    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        mCtx = MainActivity.this;
        if (savedInstanceState == null) {
            findViews();
            setListView();
        }
    }

    private void findViews() {
        Log.d(TAG, "findViews");
        listView = (ListView) findViewById(R.id.list);
    }

    private void setListView() {
        Log.d(TAG, "setListView");
        rowItems = new ArrayList<BaseRowItem>();
        for (int i = 0; i < 10; i++) {
            BaseRowItem item = new BaseRowItem("test", "test");
            rowItems.add(item);
        }
        BaseCustomAdapter adapter = new BaseCustomAdapter(mCtx, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

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

}
