package com.example.woshidan.example;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yoshidanozomi on 2016/03/27.
 */
public class HeaderAdapter extends com.example.woshidan.sectionedgridview.HeaderAdapter {
    public HeaderAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.header_in_image_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("getHeaders.get(n)", "headers.length: " + getHeaders().size() + " positon: " + position);
        ((ViewHolder)holder).headerCaption.setText(formatHeaderCaption((getHeaders().get(position))));
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView headerCaption;
        public ViewHolder(View itemView) {
            super(itemView);
            headerCaption = (TextView) itemView.findViewById(R.id.headerCaption);
        }
    }

    @NonNull
    private CharSequence formatHeaderCaption(long createdAt) {
        return DateFormat.format("yyyy.MM.dd kk:mm", createdAt);
    }
}

