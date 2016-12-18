package ru.ulstu_team.ulstuschedule.ui.favorites

import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.floatingActionButton
import ru.ulstu_team.ulstuschedule.R

class FavoritesFragmentUI : AnkoComponent<FavoritesFragment> {
    lateinit var emptyViewContainer: ViewGroup

    override fun createView(ui: AnkoContext<FavoritesFragment>) = with(ui) {
        coordinatorLayout {
            lparams {
                width = matchParent
                height = matchParent
            }

            relativeLayout {
                emptyViewContainer = verticalLayout {
                    imageView {
                        imageResource = R.drawable.ic_star_outline
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = dip(120)
                        height = dip(120)
                    }
                    textView {
                        textResource = R.string.empty_favorites
                        textSize = sp(5.5.toFloat()).toFloat()
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = dip(250)
                    }
                }.lparams {
                    centerInParent()
                }
            }.lparams {
                width = matchParent
                height = matchParent
            }

            floatingActionButton {
                imageResource = R.drawable.plus
                backgroundColor = R.color.colorAccent
                elevation = 4.toFloat()
            }.lparams {
                gravity = Gravity.BOTTOM or Gravity.END
                margin = dip(16)
            }
        }
    }
}