package com.bluearchitect.jackhan.sortlistviewlibrary;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author hhz
 * @time 2016/11/20
 * @descriptionJava 显示根据首字字母获取所有首字dialog
 */
public class FirstWordsDialog extends PopupWindow implements AdapterView.OnItemClickListener {
    private int firstwords_bg;
    private int firstwords_text_color;
    private int firstwords_text_size;

    GridView gridView;
    List<String> list = new ArrayList<>();
    GridAdapter gridAdapter;
    OnItemClickListener onItemClickListener;

    interface OnItemClickListener {

        void onItemClick(int position);
    }

    public FirstWordsDialog(Context context, int firstwords_bg, int firstwords_text_color, int firstwords_text_size, OnItemClickListener onItemClickListener) {
        super(context);
        this.firstwords_bg = firstwords_bg;
        this.firstwords_text_color = firstwords_text_color;
        this.firstwords_text_size = firstwords_text_size;

        this.onItemClickListener = onItemClickListener;
        gridView = (GridView) LayoutInflater.from(context).inflate(R.layout.dialog_first_words_layout, null);
        gridAdapter = new GridAdapter(context, list);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(this);
        setContentView(gridView);
        setBackgroundDrawable(null);

    }

    public class GridAdapter extends BaseAdapter {
        Context context;
        List<String> list;

        public GridAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            final String item = list.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_frist_word, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.firstWord.setText(item);
            if (firstwords_bg != 0)
                viewHolder.firstWord.setBackgroundResource(firstwords_bg);
            if (firstwords_text_color != 0)
                viewHolder.firstWord.setTextColor(firstwords_text_color);
            if (firstwords_text_size != 0)
                viewHolder.firstWord.setTextSize(TypedValue.COMPLEX_UNIT_PX, firstwords_text_size);
            return convertView;
        }
    }

    public class ViewHolder {
        TextView firstWord;

        public ViewHolder(View parentView) {
            firstWord = (TextView) parentView.findViewById(R.id.first_word);
        }
    }

    Map<String, Integer> firstWordsMap;

    /**
     * 设置firstWords
     *
     * @param firstWordsMap 首字位置map
     */
    public void setFirstWords(Map<String, Integer> firstWordsMap) {
        this.firstWordsMap = firstWordsMap;
        list.clear();
        Iterator it = firstWordsMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            list.add(key);
        }
        gridAdapter.notifyDataSetChanged();

        int firstWordsCellHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 35, getContentView().getResources().getDisplayMetrics());
        int numColumns = firstWordsMap.size() > 3 ? 3 : firstWordsMap.size();
        gridView.setNumColumns(numColumns);
        setWidth(firstWordsCellHeight * (numColumns));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        onItemClickListener.onItemClick((Integer) firstWordsMap.values().toArray()[i]);
    }

    @Override
    public void setTouchInterceptor(View.OnTouchListener l) {
        super.setTouchInterceptor(l);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        timer.start();
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    CountDownTimer timer = new CountDownTimer(2000, 10) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            if (isShowing())
                dismiss();

        }
    };
}
