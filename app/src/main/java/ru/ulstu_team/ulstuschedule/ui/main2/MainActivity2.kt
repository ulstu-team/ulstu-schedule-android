package ru.ulstu_team.ulstuschedule.ui.main2

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.toolbar.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity
import ru.ulstu_team.ulstuschedule.ui.common.group.GroupScheduleFragment

class MainActivity2 : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_layout)
        configureNavigationView()

        contentLayout = R.layout.main_content
        toolbarLayout = R.layout.toolbar
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        val fm = fragmentManager
        if (fm.findFragmentByTag(GroupScheduleFragment.TAG) == null) {
            fm.beginTransaction().add(R.id.fragmentContainer,
                    GroupScheduleFragment(),
                    GroupScheduleFragment.TAG).commit()
        }

        //CreateSyncAccount(this)
    }

    private companion object {
        fun CreateSyncAccount(context: Context) {
            val account: Account = Account("schedule", "schedule")
            val accountManager = context.getSystemService(ACCOUNT_SERVICE) as AccountManager
            if (accountManager.addAccountExplicitly(account, null, null)) {
                /*
                 * If you don't set android:syncable="true" in
                 * in your <provider> element in the manifest,
                 * then call context.setIsSyncable(account, AUTHORITY, lesson1)
                 * here.
                 */
                //context.isSyncable(account, null, lesson1)
            } else {
                /*
                 * The account exists or some other error occurred. Log this, report it,
                 * or handle it internally.
                 */
            }
        }
    }
}
