package com.bluearchitect.jackhan.sortlistviewlibrary;

import android.text.TextUtils;

/**
 * @author hhz
 * @time 2016/11/13 16:45
 * @description 字母检索数据bean基类
 */

public abstract class SortModel {

    private String sortName;   //显示的数据
    private String sortFirstWordSpell;  //数据首字拼音
    private String sortLetters;  //数据拼音的首字母

    public String getSortName() {
        init();
        return sortName;
    }

    public void init() {
        if (TextUtils.isEmpty(sortName)) {
            setSortName(bindSortName());
        }
    }

    public abstract String bindSortName();

    public void setSortName(String sortName) {
        this.sortName = sortName;

        String pinyin = new CharacterParser().getSelling(sortName);
        String sortString = pinyin.substring(0, 1).toUpperCase();

        if (sortString.matches("[A-Z]")) {
            setSortLetters(sortString.toUpperCase());
        } else {
            setSortLetters("#");
        }

        String fristWordStr = sortName.substring(0, 1).toUpperCase();
        String fristWordPinYin = new CharacterParser().getSelling(fristWordStr);

        setSortFirstWordSpell(fristWordPinYin);
    }

    public String getSortFirstWordSpell() {
        init();
        return sortFirstWordSpell;
    }

    public void setSortFirstWordSpell(String sortFirstWordSpell) {
        this.sortFirstWordSpell = sortFirstWordSpell;
    }

    public String getSortLetters() {
        init();
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
