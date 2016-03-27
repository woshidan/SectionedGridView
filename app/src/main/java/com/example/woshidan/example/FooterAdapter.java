package com.example.woshidan.example;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yoshidanozomi on 2016/03/27.
 */
public class FooterAdapter extends com.example.woshidan.sectionedgridview.FooterAdapter {
    public FooterAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.footer_in_image_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).footerCaption.setText("section " + position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView footerCaption;
        public ViewHolder(View itemView) {
            super(itemView);
            footerCaption = (TextView) itemView.findViewById(R.id.footerCaption);
        }
    }
}
