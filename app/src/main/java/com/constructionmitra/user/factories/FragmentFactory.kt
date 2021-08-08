package com.constructionmitra.user.factories

import androidx.fragment.app.Fragment
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
                WorkDetailsFragment::class.java.name ->
                    WorkDetailsFragment.newInstance()
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