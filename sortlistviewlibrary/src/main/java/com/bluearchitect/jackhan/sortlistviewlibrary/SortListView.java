package com.bluearchitect.jackhan.sortlistviewlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/11/12 0012.
 */

public class SortListView extends LinearLayout implements SearchView.OnCloseListener, SideBar.OnTouchingLetterChangedListener, SearchView.OnQueryTextListener {
    private int letters_bg;
    private int letters_text_color;
    private int letters_text_size;
    private int sidebar_bg;
    private int sidebar_text_color;
    private int sidebar_text_color_pressed;
    private int sidebar_cell_spacing;
    private int dialog_bg;
    private int dialog_text_color;
    private int dialog_text_size;

    public ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    SearchView searchView;

    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;

    List<SortModel> sortList;

    public SortListView(Context context) {
        this(context, null);
    }

    public SortListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SortListView,
                defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.SortListView_letters_bg) {
                letters_bg = typedArray.getColor(attr, getResources().getColor(R.color.letters_bg));
            } else if (attr == R.styleable.SortListView_letters_text_color) {
                letters_text_color = typedArray.getColor(attr, getResources().getColor(R.color.letters_text));
            } else if (attr == R.styleable.SortListView_letters_text_size) {
                // 默认设置为16sp，TypeValue也可以把sp转化为px
                letters_text_size = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.SortListView_sidebar_bg) {
                sidebar_bg = typedArray.getResourceId(attr, R.drawable.sidebar_background);
            } else if (attr == R.styleable.SortListView_sidebar_text_color) {
                sidebar_text_color = typedArray.getColor(attr,
                        getResources().getColor(R.color.sidebar_text));
            } else if (attr == R.styleable.SortListView_sidebar_text_color_pressed) {
                sidebar_text_color_pressed = typedArray.getColor(attr,
                        getResources().getColor(R.color.sidebar_text_pressed));
            } else if (attr == R.styleable.SortListView_sidebar_cell_spacing) {
                sidebar_cell_spacing = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.SortListView_dialog_bg) {
                dialog_bg = typedArray.getResourceId(attr, R.drawable.show_head_toast_bg);
            } else if (attr == R.styleable.SortListView_dialog_text_color) {
                dialog_text_color = typedArray.getColor(attr,
                        getResources().getColor(R.color.sort_dialog_text));
            } else if (attr == R.styleable.SortListView_dialog_text_size) {
                dialog_text_size = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
            }

        }
        typedArray.recycle();

        LayoutInflater.from(context).inflate(R.layout.sort_layout, this, true);

        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        if (dialog_bg != 0)
            dialog.setBackgroundResource(dialog_bg);
        if (dialog_text_size != 0)
            dialog.setTextSize(dialog_text_size);
        if (dialog_text_color != 0)
            dialog.setTextColor(dialog_text_color);
        sideBar.setTextView(dialog);

        sideBar.setViewAttr(sidebar_cell_spacing, sidebar_text_color,
                sidebar_text_color_pressed, sidebar_bg);

        sideBar.setOnTouchingLetterChangedListener(this);

        sortListView = (ListView) findViewById(R.id.sort_contentListView);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setIconified(false);
    }
/*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SortListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    /**
     * 给列表设置adapter
     *
     * @param adapter
     */
    public void setAdapter(SortAdapter adapter) {
        adapter.setLettersAttr(letters_bg, letters_text_color, letters_text_size);
        this.sortList = adapter.sortList;
        this.adapter = adapter;
        sortListView.setAdapter(adapter);
    }


    /**
     * 根据sortName查找数据
     *
     * @param filterSortName
     */
    private void filterData(String filterSortName) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterSortName)) {
            filterDateList = sortList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : sortList) {
                String name = sortModel.getSortName();
                if (name.indexOf(filterSortName.toString()) != -1
                        || characterParser.getSelling(name).startsWith(filterSortName.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }


    @Override
    public boolean onClose() {

        adapter.updateListView(sortList);
        return false;
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int position = adapter.getPositionForSection(s.charAt(0));
        if (position != -1) {
            sortListView.setSelection(position);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        filterData(newText);
        return false;
    }
}
