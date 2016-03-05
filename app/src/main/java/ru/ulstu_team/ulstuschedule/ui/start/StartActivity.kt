package ru.ulstu_team.ulstuschedule.ui.start

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton

import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.local.PrefsKeys
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager
import ru.ulstu_team.ulstuschedule.ui.main.MainActivity

class StartActivity : AppCompatActivity() {
    private var actv: AutoCompleteTextView? = null
    private var prefs: PrefsManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        prefs = PrefsManager(this)

        actv = findViewById(R.id.groupNameText) as AutoCompleteTextView
        actv!!.gravity = Gravity.CENTER
        val groups = arrayOf<String>()
        val adapter = ArrayAdapter(this, R.layout.autocomplete_list_item, groups)
        actv!!.setAdapter(adapter)
    }

    fun onTypeUserClicked(view: View) {
        val checked = (view as RadioButton).isChecked

        when (view.getId()) {
            R.id.studentSchedule -> if (!checked) {
                (findViewById(R.id.tutorSchedule) as RadioButton).isChecked = false
                actv!!.visibility = View.VISIBLE
            }
            R.id.tutorSchedule -> if (!checked) {
                (findViewById(R.id.studentSchedule) as RadioButton).isChecked = false
                actv!!.visibility = View.GONE
            }
        }
    }

    fun onLoginClicked(view: View) {
        val intent = Intent(this@StartActivity, MainActivity::class.java)
        if ((findViewById(R.id.studentSchedule) as RadioButton).isChecked && actv!!.text.toString().trim { it <= ' ' } != "") {
            prefs!!.putString(PrefsKeys.USER_TYPE, "student")
            prefs!!.putString(PrefsKeys.USER_GROUP, actv!!.text.toString().trim { it <= ' ' })
            startActivity(intent)
        } else if ((findViewById(R.id.tutorSchedule) as RadioButton).isChecked) {
            prefs!!.putString(PrefsKeys.USER_TYPE, "tutor")
            startActivity(intent)
        }
    }

}
