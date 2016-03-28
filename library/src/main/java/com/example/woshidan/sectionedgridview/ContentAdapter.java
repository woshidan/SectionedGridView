package com.example.woshidan.sectionedgridview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by woshidan on 2016/03/27.
 */
public abstract class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<? extends Content> contents;
    protected Context context;

    public ContentAdapter(Context context) {
        this.context = context;
    }

    public void setContents(ArrayList<? extends Content> contents) {
        this.contents = contents;
    }

    public ArrayList<? extends Content> getContents() {
        return contents;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
