# 字母检索列表ListLib
* 字母检索、字母分类
* 字母检索后首字列表展示
* 搜索功能
## 效果展示
![](https://github.com/wugod2015/ListLib/raw/master/art/Screenshot4.png)
![](https://github.com/wugod2015/ListLib/raw/master/art/Screenshot2.png)
![](https://github.com/wugod2015/ListLib/raw/master/art/Screenshot3.png)
### 引用sortlistviewlibrary
直接引用sortlistviewlibrary module源码，或者引用build/outputs下sortlistviewlibrary-release.aar包
### 使用
在xml布局文件引用SortListView控件<br>
```
    <com.bluearchitect.jackhan.sortlistviewlibrary.SortListView
        android:id="@+id/sortListView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:dialog_bg="@drawable/show_head_toast_bg"
        app:dialog_text_color="@color/sort_dialog_text"
        app:dialog_text_size="15dp"
        app:letters_bg="@color/letters_bg"
        app:letters_text_color="@color/letters_text"
        app:letters_text_size="14sp"
        app:sidebar_bg="@drawable/sidebar_background"
        app:sidebar_cell_spacing="4dp"
        app:sidebar_text_color="@color/sidebar_text"
        app:sidebar_text_color_pressed="@color/sidebar_text_pressed"
        app:firstwords_dialog_bg="@drawable/first_word_bg"
        app:firstwords_dialog_text_color="@color/firstwords_dialog_text"
        app:firstwords_dialog_text_size="16sp">

    </com.bluearchitect.jackhan.sortlistviewlibrary.SortListView>
```
dialog_bg：字母弹窗背景<br>
dialog_text_color：字母弹窗字体颜色<br>
dialog_text_size：字母弹窗字体大小<br>
letters_bg：字母标题背景<br>
letters_text_color：字母标题字体颜色<br>
letters_text_size：字母标题字体大小<br>
sidebar_bg：侧栏背景<br>
sidebar_cell_spacing：侧栏字母间距<br>
sidebar_text_color：侧栏字体颜色<br>
sidebar_text_color_pressed：侧栏字体点击颜色<br>
firstwords_dialog_bg：字母检索首字列表背景<br>
firstwords_dialog_text_color：字母检索首字列表字体颜色<br>
firstwords_dialog_text_size：字母检索首字列表字体大小<br>
<br>
数据bean要继承SortModel，并且复写bindSortName()方法，配置需检索的字段<br>
```
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
```

列表适配器需继承SortAdapter
```
public class ContactsAdapter extends SortAdapter<ContactsAdapter.ContactsViewHolder, Contacts> {
    public ContactsAdapter(Context mContext, List<Contacts> list) {
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
```
开始使用
```
sortListView = (SortListView) findViewById(R.id.sortListView);

        contactsList = getContacts();

        adapter = new ContactsAdapter(this, contactsList);
        sortListView.setAdapter(adapter);
        sortListView.setShowFirstWordsByLetter(true);//是否显示字母检索后的所有首字
        sortListView.setOnItemClickListener(this);
```
