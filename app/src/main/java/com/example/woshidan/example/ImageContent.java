package com.example.woshidan.example;

import com.example.woshidan.sectionedgridview.Content;

/**
 * Created by yoshidanozomi on 2016/03/27.
 */
public class ImageContent extends Content {
    private long createdAt;
    private int imageId;

    public ImageContent(long createdAt, int imageId) {
        this.createdAt = createdAt;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }


    @Override
    public long getSortKey() {
        return createdAt;
    }

    @Override
    public long getSectionKey() {
        return createdAt - (createdAt % 3000000000L);
    }
}
