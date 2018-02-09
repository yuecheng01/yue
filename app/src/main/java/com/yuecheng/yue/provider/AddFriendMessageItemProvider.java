package com.yuecheng.yue.provider;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.yue.message.AddFriendsMessage;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

/**
 * Created by Administrator on 2018/1/29.
 */
@ProviderTag(messageContent = AddFriendsMessage.class)
public class AddFriendMessageItemProvider extends IContainerItemProvider.MessageProvider<AddFriendsMessage> {
    @Override
    public void bindView(View view, int i, AddFriendsMessage addFriendsMessage, UIMessage uiMessage) {

    }

    @Override
    public Spannable getContentSummary(AddFriendsMessage addFriendsMessage) {
        return null;
    }

    @Override
    public void onItemClick(View view, int i, AddFriendsMessage addFriendsMessage, UIMessage uiMessage) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        return null;
    }
}
