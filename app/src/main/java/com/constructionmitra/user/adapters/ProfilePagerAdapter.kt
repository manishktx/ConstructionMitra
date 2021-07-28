package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.constructionmitra.user.R
import com.constructionmitra.user.data.ProfileItem
import com.constructionmitra.user.databinding.ItemProfileBinding

val  DUMMY_LIST = listOf<ProfileItem>(
    ProfileItem(R.drawable.ic_bag,
    "अपना अनुभव साझा करें",
    "जानकारी जोड़े"),

    ProfileItem(R.drawable.ic_bag,
        "अपने काम करने की जगों",
        "जानकारी जोड़े"),

    ProfileItem(R.drawable.ic_bag,
        "अपने काम की प्रथमिकता",
        "जानकारी जोड़े"),

    ProfileItem(R.drawable.ic_bag,
        "फोटो और आईडी",
        "जानकारी जोड़े"),
)

class ProfilePagerAdapter(
    private val items: List<ProfileItem>,
    var onClick: (profileItem: ProfileItem) -> Unit,
) : PagerAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val binding =
            ItemProfileBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )

        binding.root.apply {
            // set data
            container.addView(this)
            setOnClickListener {
            }
        }
        binding.data = items[position]
        binding.executePendingBindings()
        return binding.root
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any,
    ) {
        container.removeView(`object` as CardView)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

}