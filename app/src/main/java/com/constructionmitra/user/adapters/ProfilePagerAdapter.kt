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

enum class Profile{
    ABOUT, EXPERIENCE, WORK_LOCATION, WORK_PRIORITY, WORK_PREFERENCE, LETTER_HEAD, PHOTO_AND_ID_BOTH
}

val  PROFILE_CARDS = listOf(
    ProfileItem(R.drawable.ic_person,
        "अपने बारें में बताएं",
        "जानकारी जोड़े",
        Profile.ABOUT),
    ProfileItem(R.drawable.ic_bag,
    "अपना अनुभव साझा करें",
    "जानकारी जोड़े" ,
        Profile.EXPERIENCE,
    ),

    ProfileItem(R.drawable.ic_location,
        "अपने काम करने की जगह",
        "जानकारी जोड़े",
        Profile.WORK_LOCATION,
    ),

    ProfileItem(R.drawable.ic_camera_alt,
        "फोटो और आईडी",
        "जानकारी जोड़े",
        Profile.LETTER_HEAD
    ),
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
            binding.tvCta.setOnClickListener {
                onClick(items[position])
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