import android.app.Fragment
import android.os.Bundle
import android.support.annotation.UiThread
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import org.jetbrains.anko.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager
import ru.ulstu_team.ulstuschedule.data.remote.ScheduleApiAdapter

class SettingsFragment : Fragment() {

    private val ui = SettingsFragmentUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View? {
        val view = ui.createView(AnkoContext.Companion.create(ctx, this))
        val prefixManager = PrefsManager(context)
        ui.autoCompleteForGroupField.hint = prefixManager.getString("currentGroup")

        doAsync{
            val api = ScheduleApiAdapter()
            val groups = api.getGroups()
            val groupsName = groups.map { group -> group.name }

            uiThread {
                val groupFieldAdapter = ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, groupsName)
                ui.autoCompleteForGroupField.setAdapter(groupFieldAdapter)
                ui.autoCompleteForGroupField.onItemClick { adapterView, view, i, l ->
                    val groupName = adapterView!!.getItemAtPosition(i).toString()
                    prefixManager.putString("currentGroup", groupName)
                }
            }
        }

        return view
    }

    companion object {
        val TAG = "SettingsFragment"

        fun newInstance() = SettingsFragment().apply {
            arguments = Bundle()
        }
    }
}
