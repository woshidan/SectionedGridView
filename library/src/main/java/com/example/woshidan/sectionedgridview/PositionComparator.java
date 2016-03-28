package com.example.woshidan.sectionedgridview;

import java.util.Comparator;

/**
 * Created by woshidan on 2016/03/28.
 */
class PositionComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer left, Integer right) {
        if (left > right) {
            return 1;
        } else if (left == right) {
            return 0;
        } else {
            return -1;
        }
    }
}
