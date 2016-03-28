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

    public String getImageName() {
        switch (imageId) {
            case R.drawable.i1:
                return "Egg";
            case R.drawable.i2:
                return "Donkey";
            case R.drawable.i3:
                return "Tulip";
            case R.drawable.i4:
                return "Crocus";
            case R.drawable.i5:
                return "Glacier";
            case R.drawable.i6:
                return "Aurora";
            case R.drawable.i7:
                return "Deck";
            case R.drawable.i8:
                return "Mountain";
            case R.drawable.i9:
                return "Roof";
            case R.drawable.i10:
                return "Skeleton";
            case R.drawable.i11:
                return "Balloon";
            case R.drawable.i12:
                return "Mustang";
            case R.drawable.i13:
                return "Clock";
            case R.drawable.i14:
                return "Deer";
            case R.drawable.i15:
                return "Wheel";
            default:
                return "???";
        }
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
