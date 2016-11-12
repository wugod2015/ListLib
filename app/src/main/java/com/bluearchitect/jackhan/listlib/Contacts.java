package com.bluearchitect.jackhan.listlib;

import com.bluearchitect.jackhan.sortlistviewlibrary.SortModel;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class Contacts extends SortModel {

    String contactsName;

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
        setSortName(contactsName);
    }

}
