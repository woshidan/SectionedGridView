package com.example.woshidan.sectionedgridview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by woshidan on 2016/03/27.
 */
public abstract class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Long> headers;
    protected Context context;

    public HeaderAdapter(Context context) {
        this.context = context;
    }

    public void setHeaders(ArrayList<Long> headers) {
        this.headers = headers;
    }

    public ArrayList<Long> getHeaders() {
        return headers;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        throw new UnsupportedOperationException("You should override this method");
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        throw new UnsupportedOperationException("You should override this method");
//    }

    @Override
    public int getItemCount() {
        return headers.size();
    }
    // ViewHolderを持っている
}
