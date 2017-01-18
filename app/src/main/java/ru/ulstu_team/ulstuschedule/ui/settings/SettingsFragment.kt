import android.app.Fragment
import android.os.Bundle
import android.support.annotation.UiThread
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import org.jetbrains.anko.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.local.PrefsKeys
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleApiAdapter
import java.util.*

class SettingsFragment : Fragment() {

    private val ui = SettingsFragmentUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (groups.isEmpty()) {
            doAsync {
                val api = ScheduleApiAdapter()
                val groupNames = api.getGroups().map { group -> group.name }
                uiThread {
                    groups.addAll(groupNames)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View? {
        val view = ui.createView(AnkoContext.Companion.create(ctx, this))
        val prefsManager = PrefsManager(context)
        if (prefsManager.contains(PrefsKeys.CURRENT_GROUP)) {
            ui.actvCurrentGroup.text = SpannableStringBuilder(prefsManager.getString(PrefsKeys.CURRENT_GROUP))
        }

        val groupFieldAdapter = ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, groups)
        ui.actvCurrentGroup.setAdapter(groupFieldAdapter)
        ui.actvCurrentGroup.onItemClick { adapterView, view, i, l ->
            val groupName = adapterView!!.getItemAtPosition(i).toString()
            prefsManager.putString("currentGroup", groupName)
        }

        return view
    }

    companion object {
        val TAG = "SettingsFragment"

        fun newInstance() = SettingsFragment().apply {
            arguments = Bundle()
        }

        val groups: ArrayList<String> = ArrayList()
    }
}
