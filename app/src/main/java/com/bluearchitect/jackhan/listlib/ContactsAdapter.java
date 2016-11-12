package com.bluearchitect.jackhan.listlib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bluearchitect.jackhan.sortlistviewlibrary.SortAdapter;
import com.bluearchitect.jackhan.sortlistviewlibrary.SortModel;

import java.util.List;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class ContactsAdapter extends SortAdapter<ContactsAdapter.ContactsViewHolder> {
    public ContactsAdapter(Context mContext, List<SortModel> list) {
        super(mContext, list);
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(View parent) {
        ContactsViewHolder viewHolder = new ContactsViewHolder(parent, LayoutInflater.from(mContext)
                .inflate(R.layout.item_contacts, null));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder viewHolder, SortModel sortModel, int position) {

        viewHolder.titleTV.setText(sortModel.getSortName());
    }

    class ContactsViewHolder extends SortAdapter.ViewHolder {
        TextView titleTV;

        public ContactsViewHolder(View parentView, View childView) {
            super(parentView, childView);
            titleTV = (TextView) childView.findViewById(R.id.title);
        }
    }
}
