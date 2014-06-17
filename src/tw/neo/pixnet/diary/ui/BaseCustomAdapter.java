/*
 *  Created by Neo Hsu on 2014/6/17.
 *  Copyright (c) 2014 Neo Hsu. All rights reserved.
 */

package tw.neo.pixnet.diary.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import tw.neo.pixnet.diary.R;
import tw.neo.pixnet.diary.util.BaseRowItem;
import tw.neo.pixnet.diary.util.LogsManagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BaseCustomAdapter extends BaseAdapter {
    private static final String TAG = "BaseCustomAdapter";
    private static final LogsManagement Log = new LogsManagement();
    Context mCtx;
    List<BaseRowItem> rowItems;
    BaseRowItem rowItem;

    public BaseCustomAdapter(Context context, List<BaseRowItem> items) {
        Log.d(TAG, "Init");
        this.mCtx = context;
        this.rowItems = items;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.d(TAG, "getCount");
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        Log.d(TAG, "getItem");
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        Log.d(TAG, "getItemId");
        return rowItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Log.d(TAG, "getView");

        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) mCtx
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.base_list_item, null);
            holder = new ViewHolder();
            holder.mTime = (TextView) convertView.findViewById(R.id.mTime);
            holder.mDate = (TextView) convertView.findViewById(R.id.mDate);
            holder.mTitle = (TextView) convertView.findViewById(R.id.mTitle);
            holder.mDesc = (TextView) convertView.findViewById(R.id.mDesc);
            holder.mButton = (TextView) convertView.findViewById(R.id.mButton);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        rowItem = (BaseRowItem) getItem(position);

        SimpleDateFormat timeFormat = new SimpleDateFormat("a h:mm");
        long timestamp = Long.parseLong(rowItem.getTimestamp()) * 1000;
        holder.mTime.setText(timeFormat.format(timestamp));

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMd");
        timestamp = Long.parseLong(rowItem.getTimestamp()) * 1000;
        holder.mDate.setText(dateFormat.format(timestamp));

        holder.mTitle.setText(rowItem.getTitle());

        holder.mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("ID", rowItem.getID().toString());
                intent.putExtras(bundle);
                intent.setClass(mCtx, DetailActivity.class);
                mCtx.startActivity(intent);
                ((Activity) mCtx).overridePendingTransition(R.anim.in_from_right,
                        R.anim.out_to_left);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView mTime;
        TextView mDate;
        TextView mTitle;
        TextView mDesc;
        TextView mButton;
    }
}
