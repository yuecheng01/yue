package com.yuecheng.yue.http.pinyin;


import com.yuecheng.yue.db.entity.Friend;
import com.yuecheng.yue.ui.bean.YUE_FriendsBean;
import java.util.Comparator;



/**
 *
 * @author
 *
 */
public class PinyinComparator implements Comparator<YUE_FriendsBean> {


    public static PinyinComparator instance = null;

    public static PinyinComparator getInstance() {
        if (instance == null) {
            instance = new PinyinComparator();
        }
        return instance;
    }

    public int compare(YUE_FriendsBean o1, YUE_FriendsBean o2) {
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
