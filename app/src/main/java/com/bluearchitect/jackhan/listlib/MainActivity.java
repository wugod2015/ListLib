package com.bluearchitect.jackhan.listlib;

import android.app.Activity;
import android.os.Bundle;

import com.bluearchitect.jackhan.sortlistviewlibrary.CharacterParser;
import com.bluearchitect.jackhan.sortlistviewlibrary.PinyinComparator;
import com.bluearchitect.jackhan.sortlistviewlibrary.SortAdapter;
import com.bluearchitect.jackhan.sortlistviewlibrary.SortListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    private SortListView sortListView;
    private ContactsAdapter adapter;
    private List<Contacts> SourceDateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        sortListView = (SortListView) findViewById(R.id.sortListView);

        SourceDateList = getContacts();

        adapter = new ContactsAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);

    }

    private List<Contacts> getContacts() {
        String[] date = getResources().getStringArray(R.array.date);

        List<Contacts> mSortList = new ArrayList<Contacts>();

        for (int i = 0; i < date.length; i++) {
            Contacts sortModel = new Contacts();
            sortModel.setContactsName(date[i]);
            mSortList.add(sortModel);
        }
        return mSortList;

    }


}
