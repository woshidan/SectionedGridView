package com.example.woshidan.example;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woshidan.sectionedgridview.ContentAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by yoshidanozomi on 2016/03/27.
 */
public class ImageContentAdapter extends ContentAdapter {
    public ImageContentAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_in_image_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageContent imageContent = (ImageContent)getContents().get(position);
        ((ViewHolder)holder).textView.setText(imageContent.getImageName());
        Picasso.with(context)
                .load(imageContent.getImageId())
                .fit()
                .into(((ViewHolder)holder).imageView);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
