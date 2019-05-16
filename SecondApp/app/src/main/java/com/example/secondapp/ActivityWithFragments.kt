package com.example.secondapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.secondapp.fragments.DetailFragment
import com.example.secondapp.fragments.ListFragment
import com.example.secondapp.fragments.PhotoFragment
import com.example.secondapp.row_models.FirstRow
import kotlinx.android.synthetic.main.activity_with_fragments.*

class ActivityWithFragments : AppCompatActivity() {

    companion object {
        const val ROW_LIST = "rowList"
        const val POSITION = "position"
        const val PHOTO = "Photo"
        const val DETAIL = "Detail"
        const val LIST = "List"
    }
    var isFirstFragment = true
    lateinit var fragmentPhoto: PhotoFragment
    lateinit var fragmentDetail: DetailFragment
    lateinit var fragmentList: ListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragments)

        val rowList = intent.getParcelableArrayListExtra<FirstRow>(ROW_LIST)
        val position = intent.getIntExtra(POSITION, -1)

        fragmentPhoto = PhotoFragment.newInstance(rowList[position].url)
        fragmentDetail = DetailFragment.newInstance(rowList[position])
        fragmentList = ListFragment.newInstance(rowList, position)

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_with_fragments, fragmentPhoto, PHOTO)
                .commit()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_with_fragments, fragmentDetail, DETAIL)
                .commit()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_with_fragments, fragmentList, LIST)
                .commit()
            hideDetailAndListFragment()
        }
        switcher.setOnClickListener {
            isFirstFragment = if(isFirstFragment){
                showDetailAndListFragment()
                false
            }else{
                supportFragmentManager
                    .beginTransaction()
                    .show(fragmentPhoto)
                    .commit()
                hideDetailAndListFragment()
                true
            }
        }
    }

    private fun hideDetailAndListFragment(){
        supportFragmentManager
            .beginTransaction()
            .hide(fragmentDetail)
            .commit()
        supportFragmentManager
            .beginTransaction()
            .hide(fragmentList)
            .commit()
    }

    private fun showDetailAndListFragment(){
        supportFragmentManager
            .beginTransaction()
            .hide(fragmentPhoto)
            .commit()
        supportFragmentManager
            .beginTransaction()
            .show(fragmentDetail)
            .commit()
        supportFragmentManager.
            beginTransaction()
            .show(fragmentList)
            .commit()
    }

}
