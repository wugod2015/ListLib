package com.bluearchitect.jackhan.listlib;

import android.app.Activity;
import android.os.Bundle;

import com.bluearchitect.jackhan.sortlistviewlibrary.CharacterParser;
import com.bluearchitect.jackhan.sortlistviewlibrary.PinyinComparator;
import com.bluearchitect.jackhan.sortlistviewlibrary.SortAdapter;
import com.bluearchitect.jackhan.sortlistviewlibrary.SortListView;
import com.bluearchitect.jackhan.sortlistviewlibrary.SortModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    private SortListView sortListView;
    private SortAdapter adapter;
    private List<SortModel> SourceDateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        sortListView = (SortListView) findViewById(R.id.sortListView);

        SourceDateList = filledData(getResources().getStringArray(com.bluearchitect.jackhan.sortlistviewlibrary.R.array.date));

        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter,SourceDateList);

    }


    /**
     * ΪListView�������
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setSortName(date[i]);
            String pinyin = new CharacterParser().getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }


}
