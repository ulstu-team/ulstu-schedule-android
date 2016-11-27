package ru.ulstu_team.ulstuschedule.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.main.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.util.showToast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        configureBottomNavigationBar()
    }

    private fun configureBottomNavigationBar() {
        bottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_my_schedule ->
                        showToast(R.string.bottom_bar_my_schedule)
                R.id.action_favorites ->
                        showToast(R.string.bottom_bar_favorite)
                R.id.action_settings ->
                        showToast(R.string.bottom_bar_settings)
            }
            false
        }
    }
}
