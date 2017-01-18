import android.view.Gravity
import android.widget.AutoCompleteTextView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.switchCompat
import org.jetbrains.anko.design.textInputLayout
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.util.color

class SettingsFragmentUI : AnkoComponent<SettingsFragment> {

    lateinit var actvCurrentGroup: AutoCompleteTextView

    override fun createView(ui: AnkoContext<SettingsFragment>) = with(ui) {
        verticalLayout {
            linearLayout {
                lparams {
                    height = dip(64); width = matchParent
                }
                backgroundColor = ctx.color(R.color.colorPrimary)

                textView {
                    textSize = sp(8).toFloat()
                    text = "Настройки"
                    gravity = Gravity.CENTER_VERTICAL
                    textColor = ctx.color(R.color.white)
                }.lparams {
                    gravity = Gravity.CENTER_VERTICAL
                    marginStart = dip(24)
                    width = matchParent; height = matchParent
                }
            }

            verticalLayout {
                textInputLayout {
                    actvCurrentGroup = autoCompleteTextView {

                        lparams {
                            width = matchParent
                        }
                    }
                    hint = "Текущая группа"
                }.lparams {
                    width = matchParent
                }

                switchCompat {
                    textResource = R.string.notification_current_lesson
                    textSize = sp(5.5.toFloat()).toFloat()
                    textColor = ctx.color(R.color.primaryText)
                    isChecked = true
                }.lparams {
                    width = matchParent
                    topMargin = dip(20)
                }
                switchCompat {
                    textResource = R.string.notification_lessons
                    textSize = sp(5.5f).toFloat()
                    isChecked = true
                    textColor = ctx.color(R.color.primaryText)
                }.lparams {
                    width = matchParent
                    topMargin = dip(24)
                }
            }.lparams {
                width = matchParent
                marginEnd = dip(16)
                marginStart = dip(16)
                topMargin = dip(16)
            }
        }
    }
}