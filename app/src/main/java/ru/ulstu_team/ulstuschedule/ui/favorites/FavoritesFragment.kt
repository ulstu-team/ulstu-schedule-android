package ru.ulstu_team.ulstuschedule.ui.favorites

import android.app.Fragment
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.floatingActionButton
import ru.ulstu_team.ulstuschedule.R

class FavoritesFragment : Fragment() {

    private val ui = FavoritesFragmentUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                     savedInstanceState: Bundle?): View? {
        return ui.createView(AnkoContext.Companion.create(ctx, this))
    }

    companion object {
        val TAG = "FavoritesFragment"

        fun newInstance() = FavoritesFragment().apply {
            arguments = Bundle()
        }
    }
}
