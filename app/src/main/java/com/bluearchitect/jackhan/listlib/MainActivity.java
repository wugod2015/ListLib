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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
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
        sortListView.sortListView.setOnItemClickListener(this);

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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "click：" + contactsList.get(i).getContactsName(), Toast.LENGTH_SHORT).show();
    }
}
