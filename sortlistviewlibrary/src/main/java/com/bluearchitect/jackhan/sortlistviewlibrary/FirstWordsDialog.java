package com.bluearchitect.jackhan.sortlistviewlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/20 0020.
 */

public class FirstWordsDialog extends PopupWindow implements AdapterView.OnItemClickListener {
    GridView gridView;
    ArrayAdapter<String> arrayAdapter;
    OnItemClickListener onItemClickListener;

    interface OnItemClickListener {

        void onItemClick(int position);
    }

    public FirstWordsDialog(Context context, OnItemClickListener onItemClickListener) {
        super(context);
        this.onItemClickListener = onItemClickListener;
        gridView = (GridView) LayoutInflater.from(context).inflate(R.layout.dialog_first_words_layout, null);
        arrayAdapter = new ArrayAdapter<String>(context, R.layout.item_frist_word);
        gridView.setAdapter(arrayAdapter);
        gridView.setOnItemClickListener(this);
        setContentView(gridView);
        setBackgroundDrawable(null);

        int firstWordsCellHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 35, context.getResources().getDisplayMetrics());
        setWidth(firstWordsCellHeight * 3);
    }

    Map<String, Integer> firstWordsMap;

    /**
     * 设置firstWords
     *
     * @param firstWordsMap
     */
    public void setFirstWords(Map<String, Integer> firstWordsMap) {
        this.firstWordsMap = firstWordsMap;
        arrayAdapter.clear();
        String[] strings = new String[firstWordsMap.size()];
        arrayAdapter.addAll(firstWordsMap.keySet().toArray(strings));
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        onItemClickListener.onItemClick((Integer) firstWordsMap.values().toArray()[i]);
    }
}
