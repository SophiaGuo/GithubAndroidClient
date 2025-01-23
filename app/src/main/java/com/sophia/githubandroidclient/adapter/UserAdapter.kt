package com.sophia.githubandroidclient.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.base.view.loadmore.BaseLoadMoreAdapter
import com.sophia.githubandroidclient.model.UserInfo

/**
 *  @author: SophiaGuo
 */
class UserAdapter : BaseLoadMoreAdapter<UserInfo, BaseViewHolder>(R.layout.item_follower) {

    override fun convert(helper: BaseViewHolder, item: UserInfo) {
        helper.setText(R.id.text_owner, item.login)
        val imageAvatar = helper.getView<ImageView>(R.id.image_avatar)
        Glide.with(context).load(item.avatar_url).placeholder(R.drawable.icon_github).into(imageAvatar)
    }
}
