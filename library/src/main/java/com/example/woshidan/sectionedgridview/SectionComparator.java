package com.example.woshidan.sectionedgridview;

import java.util.Comparator;

/**
 * Created by woshidan on 2016/03/28.
 */
public class SectionComparator implements Comparator<Long> {
    @Override
    public int compare(Long lhs, Long rhs) {
        if (lhs > rhs) {
            return 1;
        } else if (lhs == rhs) {
            return 0;
        } else {
            return -1;
        }
    }
}
