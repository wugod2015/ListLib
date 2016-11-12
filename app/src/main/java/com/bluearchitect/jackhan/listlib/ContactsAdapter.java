package com.bluearchitect.jackhan.listlib;

import android.content.Context;

import com.bluearchitect.jackhan.sortlistviewlibrary.SortAdapter;
import com.bluearchitect.jackhan.sortlistviewlibrary.SortModel;

import java.util.List;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class ContactsAdapter extends SortAdapter {
    public ContactsAdapter(Context mContext,  List<SortModel> list) {
        super(mContext, list);
    }
}
