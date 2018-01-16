package com.yuecheng.yue.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/12/29.
 */
                                /*@Entity 用于标识这是一个需要Greendao帮我们生成代码的bean

                                @Id 标明主键，括号里可以指定是否自增 相当于2.2版本的

                                        Entity entity = schema.addEntity("Friend");
                                        entity.addLongProperty("id").primaryKey().autoincrement();
                                @Property 用于设置属性在数据库中的列名（默认不写就是保持一致）

                                @NotNull 非空

                                @Transient 标识这个字段是自定义的不会创建到数据库表里*/
@Entity
public class Friend {

    private Long id;
    private String nickName;
    private String portraitUri;
    private String displayName;
    private String timeStamp;
    private String nickNameSpelling;
    private String displayNameSpelling;
    private String letters;
    @Id
    @NotNull
    private String phoneNumber;
@Generated(hash = 1383362442)
public Friend(Long id, String nickName, String portraitUri, String displayName, String timeStamp,
        String nickNameSpelling, String displayNameSpelling, String letters,
        @NotNull String phoneNumber) {
    this.id = id;
    this.nickName = nickName;
    this.portraitUri = portraitUri;
    this.displayName = displayName;
    this.timeStamp = timeStamp;
    this.nickNameSpelling = nickNameSpelling;
    this.displayNameSpelling = displayNameSpelling;
    this.letters = letters;
    this.phoneNumber = phoneNumber;
}
@Generated(hash = 287143722)
public Friend() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public String getNickName() {
    return this.nickName;
}
public void setNickName(String nickName) {
    this.nickName = nickName;
}
public String getPortraitUri() {
    return this.portraitUri;
}
public void setPortraitUri(String portraitUri) {
    this.portraitUri = portraitUri;
}
public String getDisplayName() {
    return this.displayName;
}
public void setDisplayName(String displayName) {
    this.displayName = displayName;
}
public String getTimeStamp() {
    return this.timeStamp;
}
public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
}
public String getNickNameSpelling() {
    return this.nickNameSpelling;
}
public void setNickNameSpelling(String nickNameSpelling) {
    this.nickNameSpelling = nickNameSpelling;
}
public String getDisplayNameSpelling() {
    return this.displayNameSpelling;
}
public void setDisplayNameSpelling(String displayNameSpelling) {
    this.displayNameSpelling = displayNameSpelling;
}
public String getLetters() {
    return this.letters;
}
public void setLetters(String letters) {
    this.letters = letters;
}
public String getPhoneNumber() {
    return this.phoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
}

}