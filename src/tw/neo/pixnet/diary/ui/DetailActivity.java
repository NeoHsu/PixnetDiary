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

import tw.neo.pixnet.diary.R;

public class DetailActivity extends BaseActivity {
    private static final String TAG = "DetailActivity";
    private ProgressBar progressBar;
    private WebView webview;
    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_detail);
        mCtx = DetailActivity.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (savedInstanceState == null) {
            findViews();
        }
    }

    private void findViews() {
        Log.d(TAG, "findViews");
        webview = (WebView) findViewById(R.id.webview);
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
        new WebViewTask().execute();

    }

    private class WebViewTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            webview.setVisibility(View.GONE);
        }

        protected Boolean doInBackground(String... urls) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return true;
        }

        protected void onPostExecute(Boolean status) {
            if (status) {
                progressBar.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
                setWebView("<style>img{width:100%}</style><p>新聞雜誌也各增刊而特刊，依然步伐整齊地前進，孩子般的眼光，</p>\r\n<p><a href=\"http://emmademo.pixnet.net/album/photo/26136728\"><img title=\"高雄燈塔\" src=\"http://pic.pimg.tw/emmademo/1332812983-4000655212.jpg\" alt=\"高雄燈塔\" border=\"0\" /></a> &nbsp;<br />福戶內的事，在表示著歡迎。典衫當被，現在小戶已負擔不起，在幾千年前，<br />不停地前進。精神上多有些緊張，好啊，在閃爍地放亮。像日本未來的時，<br />愈著急愈覺得金錢的寶貴，平生慣愛咬文嚼字，聽到泉聲和松籟的奏彈；<br />到激昂緊張起來，試把這箇假定廢掉，看看又要到了。這回在奔走的人，<br />何用自作麻煩。有什麼科派捐募，在一處的客廳裡，卻自甘心著，<br />就可自由假設嗎？</p>\r\n<p><a href=\"http://emmademo.pixnet.net/album/photo/4274500\"><img title=\"測試\" src=\"http://pic.pimg.tw/emmademo/1311842096-12828a6a7d1d6f39103a119f6cd3bdf4.jpg\" alt=\"測試\" border=\"0\" /></a> &nbsp;</p>");
            }
        }
    }
}
