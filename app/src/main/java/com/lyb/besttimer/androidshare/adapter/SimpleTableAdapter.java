package com.lyb.besttimer.androidshare.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyb.besttimer.androidshare.R;
import com.lyb.besttimer.pluginwidget.view.tablelayout.TableAdapter;
import com.lyb.besttimer.pluginwidget.view.tablelayout.adapter.BaseTableHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */

public class SimpleTableAdapter extends TableAdapter {

    private List<SimpleTableData> simpleTableDatas = new ArrayList<>();

    public SimpleTableAdapter(TableInfo tableInfo, List<SimpleTableData> simpleTableDatas) {
        super(tableInfo);
        this.simpleTableDatas = simpleTableDatas;
    }

    public static class SimpleTableData {
        public String showContent;

        public SimpleTableData(String showContent) {
            this.showContent = showContent;
        }

    }

    private static class TableHolder extends BaseTableHolder<SimpleTableData> {

        private TextView tv;

        public TableHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }

        @Override
        public void fillView(SimpleTableData data, int position) {
            tv.setText(data.showContent);
        }
    }

    @Override
    public void onBindViewHolder(BaseTableHolder holder, int position) {
        holder.fillView(simpleTableDatas.get(position), position);
    }

    @Override
    public BaseTableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tablelayout, parent, false));
    }

    @Override
    public int getItemCount() {
        return simpleTableDatas.size();
    }

}
