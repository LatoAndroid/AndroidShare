package com.lyb.besttimer.pluginwidget.view.linearlayout;

import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * linear adapter
 * Created by linyibiao on 2017/1/9.
 */

public abstract class LinearHorizontalAdapter {

    public abstract void onBindViewHolder(BaseLinearHolder holder, int position);

    public abstract BaseLinearHolder onCreateViewHolder(ViewGroup parent, int viewType);

    public int getItemViewType(int position) {
        return 0;
    }

    public abstract int getItemCount();

    public void removeView() {
        linearLayout.removeAllViews();
    }

    public void notifyDataSetChanged() {
        notifyDataSetChanged(0, getItemCount() - 1);
    }

    public void notifyDataSetChanged(int startPos, int endPos) {

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int position = startPos; position <= endPos; position++) {
            BaseLinearHolder baseLinearHolder = onCreateViewHolder(linearLayout, getItemViewType(position));
            linearLayout.addView(baseLinearHolder.itemView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            onBindViewHolder(baseLinearHolder, position);
        }

    }

    private LinearLayout linearLayout;

    public void onAttachedToLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public void onDetachedFromLinearLayout(LinearLayout linearLayout) {
    }

}
