package com.example.woshidan.sectionedgridview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by woshidan on 2016/03/27.
 */
public class SectionedGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_CONTENT = 0;
    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_FOOTER = 2;

    public ArrayList<Content> contents;
    private ArrayList<Integer> headerPositions; // get(positionInHeader) => positionInLayout
    private ArrayList<Integer> footerPositions; // get(positionInFooter) => positionInLayout
    private ArrayList<Long> headers;
    private ArrayList<Long> footers;

    private ContentAdapter contentAdapter;
    private HeaderAdapter headerAdapter;
    private FooterAdapter footerAdapter;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private SectionedGroupAdapter(ArrayList<Content> contents) {
        Collections.sort(contents, new ContentComparator());
        this.contents = contents;
        this.headerPositions = new ArrayList<Integer>();
        this.footerPositions = new ArrayList<Integer>();

        this.headers = new ArrayList<Long>();
        this.footers = new ArrayList<Long>();
    }

    private void init() {
        buildInitialSections();
        setupLayoutManager();
        initializeChildrenAdapter();
    }

    protected void buildInitialSections() {
        Iterator<? extends Content> iterator = contents.iterator();
        Long previousSectionKey = null;
        int currentPosition = 0;
        while (iterator.hasNext()) {
            Content content = iterator.next();
            long sectionKey = content.getSectionKey();

            if (footerAdapter != null && previousSectionKey != null && previousSectionKey != sectionKey) {
                footers.add(previousSectionKey);
                footerPositions.add(currentPosition);
                currentPosition++;
            }

            if (headerAdapter != null && !headers.contains(sectionKey)) {
                headers.add(sectionKey);
                headerPositions.add(currentPosition);
                currentPosition++;
            }
            previousSectionKey = content.getSectionKey();
            currentPosition++;
        }
        footers.add(previousSectionKey);
        footerPositions.add(currentPosition);
    }

    protected void initializeChildrenAdapter() {
        contentAdapter.setContents(contents);

        if (headerAdapter != null) {
            headerAdapter.setHeaders(headers);
        }

        if (footerAdapter != null) {
            footerAdapter.setFooters(footers);
        }
    }

    private void setupLayoutManager() {
        layoutManager = (GridLayoutManager)(recyclerView.getLayoutManager());
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (getItemViewType(position) == VIEW_TYPE_CONTENT)? 1 : layoutManager.getSpanCount();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CONTENT:
                return contentAdapter.onCreateViewHolder(parent, viewType);
            case VIEW_TYPE_HEADER:
                return headerAdapter.onCreateViewHolder(parent, viewType);
            case VIEW_TYPE_FOOTER:
                return footerAdapter.onCreateViewHolder(parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_CONTENT:
                contentAdapter.onBindViewHolder(holder, transformLayoutPositionToContentPosition(position));
                break;
            case VIEW_TYPE_HEADER:
                headerAdapter.onBindViewHolder(holder, transformLayoutPositionToHeaderPosition(position));
                break;
            case VIEW_TYPE_FOOTER:
                footerAdapter.onBindViewHolder(holder, transformLayoutPositionToFooterPosition(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return headers.size() + contents.size() + footers.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (headerPositions.contains(position)) {
            return VIEW_TYPE_HEADER;
        } else if (footerPositions.contains(position)) {
            return VIEW_TYPE_FOOTER;
        } else {
            return VIEW_TYPE_CONTENT;
        }
    }

    public void sortContents() {
        Collections.sort(contents, new ContentComparator());
    }

    // 1. get getInsertContentPosition(Content insertedContent)
    // 2. addContents contents.add(getInsertContentPosition(Content insertedContent), insertedContent)
    // 3. call this.
    public void insertedContentAt(int insertedContentPosition) {
        // Log.d("insertedContentAt", "insertedContentPosition: " + insertedContentPosition);
        Content insertedContent = contents.get(insertedContentPosition);

        boolean onlyContentInSection = getIsOnlyContentInSection(insertedContent, insertedContentPosition);
        // Log.d("insertedContentAt", "onlyContentInSection: " + onlyContentInSection);

        // 後で仮に足される
        int insertedLayoutPosition = transformContentPositionToLayoutPosition(insertedContentPosition);
        // Log.d("insertedContentAt", "insertedLayoutPosition: " + insertedLayoutPosition);

        int shiftedHeaderPosition = 1;
        int shiftedFooterPosition = 1;

        int insertLayoutRangeStart = insertedLayoutPosition;
        int insertLayoutRangeEnd   = insertedLayoutPosition;

        this.headerPositions = new ArrayList<Integer>();
        this.footerPositions = new ArrayList<Integer>();

        this.headers = new ArrayList<Long>();
        this.footers = new ArrayList<Long>();

        buildInitialSections();

        if (onlyContentInSection) {
            if (headerAdapter != null) {
                insertLayoutRangeEnd++;
                headerAdapter.setHeaders(headers);
            }

            if (footerAdapter != null) {
                insertLayoutRangeEnd++;
                footerAdapter.setFooters(footers);
            }
        }

        contentAdapter.setContents(contents);
        notifyItemRangeInserted(insertLayoutRangeStart, insertLayoutRangeEnd - insertLayoutRangeStart + 1);
    }

    private boolean getIsOnlyContentInSection(Content content, int positionInContents) {
        boolean isFirstContentInSection;
        if (positionInContents == 0) {
            isFirstContentInSection = true;
        } else {
            isFirstContentInSection = (contents.get(positionInContents - 1).getSectionKey() != content.getSectionKey());
        }

        boolean isLastContentInSection;
        if (positionInContents == contents.size() - 1) {
            isLastContentInSection = true;
        } else {
            isLastContentInSection = (contents.get(positionInContents + 1).getSectionKey() != content.getSectionKey());
        }

        return isFirstContentInSection && isLastContentInSection;
    }

    public int getInsertContentPosition(Content insertedContent) {
        Iterator<? extends Content> iterator = contents.iterator();
        int position = 0;
        while (iterator.hasNext()) {
            Content content = iterator.next();
            if (insertedContent.getSortKey() < content.getSortKey()) {
                break;
            }
            position++;
        }
        return position;
    }

    public void removeContentAt(int positionInContents) {
        int positionInLayout = transformContentPositionToLayoutPosition(positionInContents);
        Content removedContent = contents.get(positionInContents);

        boolean onlyContentInSection = getIsOnlyContentInSection(removedContent, positionInContents);

        int shiftedHeaderPosition = 1;
        int shiftedFooterPosition = 1;

        int removeLayoutRangeStart = positionInLayout;
        int removeLayoutRangeEnd   = positionInLayout;

        if (onlyContentInSection) {
            if (headerAdapter != null) {
                removeLayoutRangeStart = positionInLayout - 1;
                int removedHeaderPosition = transformLayoutPositionToHeaderPosition((positionInLayout-1));
                headers.remove(removedHeaderPosition);
                headerPositions.remove(removedHeaderPosition);

                headerAdapter.setHeaders(headers);
                shiftedHeaderPosition++;
                shiftedFooterPosition++;
            }

            if (footerAdapter != null) {
                removeLayoutRangeEnd = positionInLayout + 1;
                int removedFooterPosition = transformLayoutPositionToFooterPosition(positionInLayout+1);
                footers.remove(removedFooterPosition);
                footerPositions.remove(removedFooterPosition);

                footerAdapter.setFooters(footers);
                shiftedFooterPosition++;
                shiftedHeaderPosition++;
            }
        }

        if (headerAdapter != null) {
            headerPositions = shiftPosition(removeLayoutRangeStart, - 1 * shiftedHeaderPosition, headerPositions);
        }

        if (footerAdapter != null) {
            footerPositions = shiftPosition(removeLayoutRangeEnd, - 1 * shiftedFooterPosition, footerPositions);
        }

        contents.remove(positionInContents);
        contentAdapter.setContents(contents);

        notifyItemRangeRemoved(removeLayoutRangeStart, removeLayoutRangeEnd - removeLayoutRangeStart + 1);
    }

    public void notifyContentSetChanged() {
        Collections.sort(contents, new ContentComparator());
        this.headerPositions = new ArrayList<Integer>();
        this.footerPositions = new ArrayList<Integer>();

        this.headers = new ArrayList<Long>();
        this.footers = new ArrayList<Long>();

        init();

        contentAdapter.setContents(contents);
        headerAdapter.setHeaders(headers);
        footerAdapter.setFooters(footers);

        notifyDataSetChanged();
    }

    private ArrayList<Integer> shiftPosition(int shiftStart, int shiftDiff, ArrayList<Integer> positions) {
        ArrayList<Integer> shiftedPositions = new ArrayList<Integer>();
        Iterator<Integer> iterator = positions.iterator();
        while(iterator.hasNext()) {
            int position = iterator.next();
            if (position > shiftStart) {
                shiftedPositions.add(position + shiftDiff);
            } else {
                shiftedPositions.add(position);
            }
        }
        Collections.sort(shiftedPositions, new PositionComparator());
        return shiftedPositions;
    }

    protected int transformLayoutPositionToContentPosition(int layoutPosition) {
        Iterator<Integer> headerIterator = headerPositions.iterator();
        int aboveHeaderCount = 0;

        headerIterator = headerPositions.iterator();
        while (headerIterator.hasNext()) {
            int headerPosition = headerIterator.next();
            // Log.d("ToContentPosition", "headerPosition " + headerPosition);
            if (headerPosition > layoutPosition) {
                break;
            }
            aboveHeaderCount++;
        }

        Iterator<Integer> footerIterator = footerPositions.iterator();
        int aboveFooterCount = 0;
        while (footerIterator.hasNext()) {
            int footerPosition = footerIterator.next();
            // Log.d("ToContentPosition", "footerPosition " + footerPosition);
            if (footerPosition > layoutPosition) {
                break;
            }
            aboveFooterCount++;
        }
        // Log.d("ToContentPosition", "layoutPosition " + layoutPosition + " aboveHeaderCount " + aboveHeaderCount  + " aboveFooterCount " + aboveFooterCount);
        return layoutPosition - aboveHeaderCount - aboveFooterCount;
    }

    protected int transformLayoutPositionToHeaderPosition(int layoutPosition) {
        return headerPositions.indexOf(layoutPosition);
    }

    protected int transformLayoutPositionToFooterPosition(int layoutPosition) {
        return footerPositions.indexOf(layoutPosition);
    }

    protected int transformContentPositionToLayoutPosition(int contentPosition) {
        Content content = contents.get(contentPosition);
        Iterator<Long> headerIterator = headers.iterator();
        int aboveHeaderCount = 0;
        while (headerIterator.hasNext()) {
            long headerSectionKey = headerIterator.next();
            if (headerSectionKey > content.getSectionKey()) {
                break;
            }
            aboveHeaderCount++;
        }

        Iterator<Long> footerIterator = footers.iterator();
        int aboveFooterCount = 0;
        while (footerIterator.hasNext()) {
            long footerSectionKey = footerIterator.next();
            if (footerSectionKey >= content.getSectionKey()) {
                break;
            }
            aboveFooterCount++;
        }
        return contentPosition + aboveHeaderCount + aboveFooterCount;
    }

    public static class Builder {
        private SectionedGroupAdapter adapter;

        public Builder(ArrayList<Content> contents) {
            adapter = new SectionedGroupAdapter(contents);
        }

        public Builder withContentAdapter(ContentAdapter contentAdapter) {
            adapter.contentAdapter = contentAdapter;
            return this;
        }

        public Builder withHeaderAdapter(HeaderAdapter headerAdapter) {
            adapter.headerAdapter = headerAdapter;
            return this;
        }

        public Builder withFooterAdapter(FooterAdapter footerAdppter) {
            adapter.footerAdapter = footerAdppter;
            return this;
        }

        public Builder withRecyclerView(RecyclerView recyclerView) {
            adapter.recyclerView = recyclerView;
            return this;
        }

        public SectionedGroupAdapter build() {
            adapter.init();
            return adapter;
        }
    }
}
