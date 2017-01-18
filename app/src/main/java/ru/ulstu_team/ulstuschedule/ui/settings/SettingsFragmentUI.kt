import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.switchCompat
import org.jetbrains.anko.design.coordinatorLayout
import org.w3c.dom.Text
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.R.color.colorSecondaryText
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleApiAdapter

class SettingsFragmentUI : AnkoComponent<SettingsFragment> {

    private val NOTIFICATION_CURRENT_LESSON = 0x13
    private val NOTIFICATION_LESSONS = 0x13

    lateinit var autoCompleteForGroupField: AutoCompleteTextView

    override fun createView(ui: AnkoContext<SettingsFragment>) = with(ui) {
        coordinatorLayout {
            lparams {
                width = matchParent
                height = matchParent
            }

            relativeLayout {
                verticalLayout {
                    textView {
                        textResource = R.string.current_group
                        textSize = sp(5.5.toFloat()).toFloat()
                    }.lparams {
                        width = dip(250)
                        margin = dip(10)
                    }

                    autoCompleteForGroupField = autoCompleteTextView(){
                        textSize = sp(5.5.toFloat()).toFloat()

                    }.lparams {
                        width = dip(250)
                        leftMargin = dip(10)
                    }


                    /*autoCompleteTextView {
                        textResource = R.string.current_group_name
                        textSize = sp(5.5.toFloat()).toFloat()
                    }.lparams {
                        width = dip(250)
                        leftMargin = dip(10)
                    }*/

                    view {
                        backgroundResource = R.color.colorPrimary

                    }.lparams {
                        height = dip(1)
                        topMargin = dip(10)
                    }

                    switchCompat {
                        id = NOTIFICATION_CURRENT_LESSON
                        textResource = R.string.notification_current_lesson
                        textSize = sp(5.5.toFloat()).toFloat()
                        textColor = colorSecondaryText
                    }.lparams {
                        margin = dip(10)
                        width = matchParent
                    }

                    switchCompat {
                        id = NOTIFICATION_LESSONS
                        textResource = R.string.notification_lessons
                        textSize = sp(5.5.toFloat()).toFloat()
                        isChecked = true
                        textColor = colorSecondaryText
                    }.lparams {
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                        width = matchParent
                    }
                }.lparams {
                    gravity = Gravity.LEFT
                }
            }
        }
    }

    private fun UpdateCurrentGroup() {

    }
}