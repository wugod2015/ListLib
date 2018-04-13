package com.bluearchitect.jackhan.listlib;

import com.bluearchitect.jackhan.sortlistviewlibrary.SortModel;

/**
 * @author hhz
 * @time 2016/11/13 16:47
 * @description 人员列表数据bean
 */

public class Contacts extends SortModel {

    String contactsName;

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    @Override
    public String bindSortName() {
        return contactsName;
    }
}
