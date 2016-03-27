package com.example.woshidan.sectionedgridview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by woshidan on 2016/03/27.
 */
public class FooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Long> footers;
    protected Context context;

    public FooterAdapter(Context context) {
        this.context = context;
    }

    public void setFooters(ArrayList<Long> footers) {
        this.footers = footers;
    }

    public ArrayList<Long> getFooters() {
        return footers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        throw new UnsupportedOperationException("You should override this method");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        throw new UnsupportedOperationException("You should override this method");
    }

    @Override
    public int getItemCount() {
        return footers.size();
    }
}
