import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.ctx

class SettingsFragment : Fragment() {

    private val ui = SettingsFragmentUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View? {
        return ui.createView(AnkoContext.Companion.create(ctx, this))
    }

    companion object {
        val TAG = "SettingsFragment"

        fun newInstance() = SettingsFragment().apply {
            arguments = Bundle()
        }
    }
}
