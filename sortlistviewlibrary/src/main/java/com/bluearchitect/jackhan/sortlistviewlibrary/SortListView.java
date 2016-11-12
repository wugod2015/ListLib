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

public class SortListView extends LinearLayout implements AdapterView.OnItemClickListener, SearchView.OnCloseListener, SideBar.OnTouchingLetterChangedListener, SearchView.OnQueryTextListener {
    private int letters_bg;
    private int letters_text_color;
    private int letters_text_size;
    private int sidebar_bg;
    private int sidebar_text_color;
    private int sidebar_text_size;


    private ListView sortListView;
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
                        TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.SortListView_sidebar_bg) {
                sidebar_bg = typedArray.getResourceId(attr, R.drawable.sidebar_background);
            } else if (attr == R.styleable.SortListView_sidebar_text_color) {
                sidebar_text_color = typedArray.getColor(attr, getResources().getColor(R.color.sidebar_text));
            } else if (attr == R.styleable.SortListView_sidebar_text_size) {
                sidebar_text_size = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
            }

        }
        typedArray.recycle();

        LayoutInflater.from(context).inflate(R.layout.sort_layout, this, true);

        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        sideBar.setOnTouchingLetterChangedListener(this);

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(this);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }

    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SortListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/
    public void setAdapter(SortAdapter adapter, List<SortModel> sortList) {
        this.sortList = sortList;
        this.adapter=adapter;
        sortListView.setAdapter(adapter);
    }


    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sortList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : sortList) {
                String name = sortModel.getSortName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
