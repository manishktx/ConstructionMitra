package com.constructionmitra.user.factories

import androidx.fragment.app.Fragment
import com.constructionmitra.user.ui.ShowImageFragment
import com.constructionmitra.user.ui.employer.ViewJobDetailsFragment
import com.constructionmitra.user.ui.login.WorkSubCategoriesFragment
import com.constructionmitra.user.ui.profile.*
import com.constructionmitra.user.ui.work.WorkDetailsFragment
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FragmentFactory @Inject constructor() {

    companion object {
        const val SHOW_WORK_DETAILS_FRAGMENT = 1

        fun<T: Fragment> create(fragmentClass: Class<T>): Fragment{
            return with(fragmentClass){
                when{
                    isAssignableFrom(WorkDetailsFragment::class.java) ->
                        WorkDetailsFragment.newInstance()
                    else ->
                        throw IllegalArgumentException("Unknown Fragment class ${fragmentClass.name}")
                }
            }
        }

        fun create(className: String): Fragment{
            return  when(className) {
                AboutFragment::class.java.name ->
                    AboutFragment.newInstance()

                WorkExpFragment::class.java.name ->
                    WorkExpFragment.newInstance()

                WorkPriorityFragment::class.java.name ->
                    WorkPriorityFragment.newInstance()

                WorkLocationFragment::class.java.name ->
                    WorkLocationFragment.newInstance()

                UploadPhotoAndIdFragment::class.java.name ->
                    UploadPhotoAndIdFragment.newInstance()

                WorkDetailsFragment::class.java.name ->
                    WorkDetailsFragment.newInstance()

                WorkSubCategoriesFragment::class.java.name ->
                    WorkSubCategoriesFragment.newInstance()

                ShowImageFragment::class.java.name ->
                    ShowImageFragment.newInstance()

                CatalogFragment::class.java.name ->
                    CatalogFragment.newInstance()

                CompanyLetterHeadFragment::class.java.name ->
                    CompanyLetterHeadFragment.newInstance()

                ViewJobDetailsFragment::class.java.name ->
                    ViewJobDetailsFragment.newInstance()

                WorkPreferenceFragment::class.java.name ->
                    WorkPreferenceFragment.newInstance()

                else ->
                    throw IllegalArgumentException("Unknown Fragment class $className")
            }
        }
    }



    private fun <T: Fragment>getFragmentClassName(whichFragment: Int) =
         when(whichFragment){
            SHOW_WORK_DETAILS_FRAGMENT ->
                WorkDetailsFragment::class.java
            else ->
                WorkDetailsFragment::class.java
        }
}