package com.lyb.besttimer.pluginwidget.view.recycleview;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Suspension head characteristics
 * Created by linyibiao on 2016/7/19.
 */
public abstract class HeaderFeature extends RecyclerView.OnScrollListener {

    private RecyclerView recyclerView;

    private RecyclerView.ViewHolder targetHolder = null;

    private int targetPosition = RecyclerView.NO_POSITION;

    private FrameLayout headerLayout;

    private SparseArray<Pair<Integer, Integer>> positionSizeArray = new SparseArray<>();

    private HEADER_ORIENTION HEADEROriention = HEADER_ORIENTION.HORIZONTAL;

    public enum HEADER_ORIENTION {
        HORIZONTAL, VERTICAL,
    }

    /**
     * Unique constructor
     *
     * @param recyclerView    Target view
     * @param header          The first view, the outermost layer of extra requirements
     * @param HEADEROriention Head view display direction
     */
    public HeaderFeature(RecyclerView recyclerView, View header, HEADER_ORIENTION HEADEROriention) {
        this.recyclerView = recyclerView;
        headerLayout = (FrameLayout) header;
        this.HEADEROriention = HEADEROriention;
    }

    public void applyFeature() {
        recyclerView.addOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        updateHeader();
    }

    private void updateHeader() {
        int headerPosition = showHeaderPosition(0);
        if (headerPosition != RecyclerView.NO_POSITION) {
            if (targetHolder == null || getTargetAdapterPosition() != headerPosition) {
                releaseHeader();
                setupHeader(headerPosition);
            }
            ajustHeader();
            headerLayout.setVisibility(View.VISIBLE);
        } else {
            releaseHeader();
            headerLayout.setVisibility(View.GONE);
        }
    }

    private void ajustHeader() {
        int headerPosition = getTargetAdapterPosition();
        for (int index = 0; index < recyclerView.getChildCount(); index++) {
            View nextChild = recyclerView.getChildAt(index);
            int position = recyclerView.getChildAdapterPosition(nextChild);
            if (isHeader(recyclerView, position)) {
                if (position != headerPosition) {
                    int ScrollX = 0;
                    int ScrollY = 0;
                    if (HEADEROriention == HEADER_ORIENTION.HORIZONTAL) {
                        ScrollX = headerLayout.getChildAt(0).getWidth() - (nextChild.getLeft() - recyclerView.getPaddingLeft());
                    } else if (HEADEROriention == HEADER_ORIENTION.VERTICAL) {
                        ScrollY = headerLayout.getChildAt(0).getHeight() - (nextChild.getTop() - recyclerView.getPaddingTop());
                    }
                    headerLayout.scrollTo(ScrollX > 0 ? ScrollX : 0, ScrollY > 0 ? ScrollY : 0);
                    return;
                }
            }
        }
        headerLayout.scrollTo(0, 0);
    }

    private int getTargetAdapterPosition() {
        int position = targetHolder.getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            return position;
        }
        return targetPosition;
    }

    private void setupHeader(int headerPosition) {
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(headerPosition);
        //If an empty description is not displayed, then we create a
        if (viewHolder == null) {
            viewHolder = recyclerView.getAdapter().createViewHolder(recyclerView, recyclerView.getAdapter().getItemViewType(headerPosition));
            viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            recyclerView.getAdapter().bindViewHolder(viewHolder, headerPosition);
        }
        View view = ((ViewGroup) viewHolder.itemView).getChildAt(0);
        Pair<Integer, Integer> sizePair = positionSizeArray.get(headerPosition, new Pair<>(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (view.getWidth() != 0 && view.getHeight() != 0) {
            sizePair = new Pair<>(view.getWidth(), view.getHeight());
            positionSizeArray.put(headerPosition, sizePair);
        }
        viewHolder.itemView.getLayoutParams().width = sizePair.first;
        viewHolder.itemView.getLayoutParams().height = sizePair.second;
        targetHolder = viewHolder;
        ((ViewGroup) viewHolder.itemView).removeView(view);
        viewHolder.setIsRecyclable(false);
        headerLayout.addView(view, new FrameLayout.LayoutParams(sizePair.first, sizePair.second));
        targetPosition = headerPosition;
    }

    private void releaseHeader() {
        if (targetHolder != null) {
            View view = headerLayout.getChildAt(0);
            headerLayout.removeView(view);
            headerLayout.scrollTo(0, 0);
            ((ViewGroup) targetHolder.itemView).addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            targetHolder.setIsRecyclable(true);
            targetHolder = null;
        }
        targetPosition = RecyclerView.NO_POSITION;
    }

    private int showHeaderPosition(int childIndex) {
        View firstView = recyclerView.getChildAt(childIndex);
        int position = recyclerView.getChildAdapterPosition(firstView);
        for (int currPos = position; currPos >= 0; currPos--) {
            if (isHeader(recyclerView, currPos)) {
                return currPos;
            }
        }
        return RecyclerView.NO_POSITION;
    }

    /**
     * Is set to a suspended head
     *
     * @param recyclerView
     * @param position
     * @return
     */
    public abstract boolean isHeader(RecyclerView recyclerView, int position);

}