package com.yuecheng.yue.http.pinyin;


import com.yuecheng.yue.db.entity.Friend;
import com.yuecheng.yue.ui.bean.YUE_FriendsBean;

import java.util.Comparator;


/**
 *
 * @author
 *
 */
public class PinyinComparator2 implements Comparator<Friend> {


    public static PinyinComparator2 instance = null;

    public static PinyinComparator2 getInstance() {
        if (instance == null) {
            instance = new PinyinComparator2();
        }
        return instance;
    }

    public int compare(Friend o1, Friend o2) {
        if (o1.getLetters().equals("@")
                || o2.getLetters().equals("#")) {
            return -1;
        } else if (o1.getLetters().equals("#")
                   || o2.getLetters().equals("@")) {
            return 1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }
}
