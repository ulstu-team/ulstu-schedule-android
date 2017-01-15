package ru.ulstu_team.ulstuschedule.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.ui.favorites.FavoritesFragment
import ru.ulstu_team.ulstuschedule.ui.schedule.ScheduleFragment
import ru.ulstu_team.ulstuschedule.util.presentFragment

class MainActivity : AppCompatActivity() {
    private val ui = MainActivityUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)
        configureBottomNavigationBar()

        showSchedule()
    }

    private fun configureBottomNavigationBar() {
        ui.bottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_my_schedule -> showSchedule()
                R.id.action_favorites -> showFavorites()
                R.id.action_settings -> showSettings()
            }
            true
        }
    }

    private fun showSchedule() = presentFragment(ui.CONTENT_CONTAINER_ID,
            ScheduleFragment.TAG, { ScheduleFragment.newInstance() })
    private fun showFavorites() = presentFragment(ui.CONTENT_CONTAINER_ID,
            FavoritesFragment.TAG, { FavoritesFragment.newInstance() })

    private fun showSettings() = toast(R.string.bottom_bar_settings)
}
