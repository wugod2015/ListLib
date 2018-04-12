package com.bluearchitect.jackhan.listlib;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bluearchitect.jackhan.sortlistviewlibrary.CharacterParser;
import com.bluearchitect.jackhan.sortlistviewlibrary.PinyinComparator;
import com.bluearchitect.jackhan.sortlistviewlibrary.SortAdapter;
import com.bluearchitect.jackhan.sortlistviewlibrary.SortListView;
import com.bluearchitect.jackhan.sortlistviewlibrary.SortModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hhz
 * @time 2016/11/13 16:48
 * @description 主界面
 */

public class MainActivity extends Activity implements SortListView.OnItemClickListener<Contacts> {
    private SortListView sortListView;
    private ContactsAdapter adapter;
    private List<Contacts> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        sortListView = (SortListView) findViewById(R.id.sortListView);

        contactsList = getContacts();

        adapter = new ContactsAdapter(this, contactsList);
        sortListView.setAdapter(adapter);
        sortListView.setShowFirstWordsByLetter(true);
        sortListView.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(Contacts item, AdapterView<?> var1, View var2, int var3, long var4) {
        Toast.makeText(this, "click：" + item.getContactsName(), Toast.LENGTH_SHORT).show();
    }
}
