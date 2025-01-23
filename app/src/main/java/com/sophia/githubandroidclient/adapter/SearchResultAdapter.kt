package com.sophia.githubandroidclient.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sophia.githubandroidclient.base.Language
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.base.view.loadmore.BaseLoadMoreAdapter
import com.sophia.githubandroidclient.base.utils.TimeConverter
import com.sophia.githubandroidclient.model.RepoEntity
/**
 *  @author: SophiaGuo
 */
class SearchResultAdapter : BaseLoadMoreAdapter<RepoEntity, BaseViewHolder>(R.layout.item_serach_result) {

    override fun convert(helper: BaseViewHolder, item: RepoEntity) {
        val imageView = helper.getView<ImageView>(R.id.image_avatar)
        Glide.with(context).load(item.owner.avatar_url).placeholder(R.drawable.icon_github).into(imageView)

        helper.setText(R.id.text_owner, item.owner.login)
        helper.setText(R.id.text_repo_name, item.full_name)
        helper.setText(R.id.text_repo_desc, item.description)
        helper.setText(R.id.tvStarCount, item.stargazers_count.toString())
        helper.setText(R.id.text_owner, item.owner.login)

        val ivLanguage = helper.getView<ImageView>(R.id.ivLanguage)
        ivLanguage.setImageDrawable(getLanguageColor(item.language))
        helper.setText(R.id.tvLanguage, item.language)
        helper.setText(R.id.tvEventTime, TimeConverter.transformTimeAgo(item.updated_at))
    }

    private fun getLanguageColor(language: String?): Drawable {
        if (language == null) return ColorDrawable(Color.TRANSPARENT)

        val colorProvider: (Int) -> Drawable = { resId ->
            ColorDrawable(ContextCompat.getColor(context, resId))
        }

        return colorProvider(
            when (language) {
                Language.LANGUAGE_KOTLIN -> R.color.color_language_kotlin
                Language.LANGUAGE_JAVA -> R.color.color_language_java
                Language.LANGUAGE_DART -> R.color.color_language_dart
                Language.LANGUAGE_CPP -> R.color.color_language_cpp
                Language.LANGUAGE_JAVASCRIPT -> R.color.color_language_javascript
                else -> R.color.color_language_other
            })
    }
}
