package com.bluearchitect.jackhan.sortlistviewlibrary;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

public abstract class SortAdapter<VH extends SortAdapter.ViewHolder> extends BaseAdapter implements SectionIndexer {
    public List<SortModel> list = null;
    public Context mContext;
    private int resource;

    public SortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public SortAdapter(Context mContext, List<SortModel> list, int resource) {
        this.mContext = mContext;
        this.list = list;
        this.resource = resource;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<SortModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup arg2) {
        VH viewHolder = null;
        final SortModel sortModel = list.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_sortlist, null);
            viewHolder = onCreateViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (VH) view.getTag();
        }

        setLetter(viewHolder.tvLetter, sortModel, position);
        onBindViewHolder(viewHolder,sortModel, position);
        return view;

    }

    public abstract VH onCreateViewHolder(View parent);

    public abstract void onBindViewHolder(VH viewHolder,SortModel sortModel, int position);

    public void setLetter(TextView letterTV, SortModel sortModel, int position) {
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            letterTV.setVisibility(View.VISIBLE);
            letterTV.setText(sortModel.getSortLetters());
        } else {
            letterTV.setVisibility(View.GONE);
        }
    }

    public class ViewHolder {
        TextView tvLetter;
        FrameLayout contentContainers;

        public ViewHolder(View parentView,View childView) {
            tvLetter = (TextView) parentView.findViewById(R.id.catalog);
            contentContainers = (FrameLayout) parentView.findViewById(R.id.content_container);

            contentContainers.addView(childView);
        }
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}