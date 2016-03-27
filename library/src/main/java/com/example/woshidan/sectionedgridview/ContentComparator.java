package com.example.woshidan.sectionedgridview;

import java.util.Comparator;

/**
 * Created by woshidan on 2016/03/27.
 */
public class ContentComparator implements Comparator<Content> {
    @Override
    public int compare(Content leftContent, Content rightContent) {
        if (leftContent.getSortKey() > rightContent.getSortKey()) {
            return 1;
        } else if (leftContent.getSortKey() == rightContent.getSortKey()) {
            return 0;
        } else {
            return -1;
        }
    }
}
