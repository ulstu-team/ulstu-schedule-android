package ru.ulstu_team.ulstuschedule.data.remote.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder

class SyncService : Service() {

    private companion object {
        var mSyncAdapter: ScheduleSyncAdapter? = null
        val mSyncAdapterLock = Object()
    }

    override fun onCreate() {
        synchronized(mSyncAdapterLock) {
            if (mSyncAdapter == null)
                mSyncAdapter = ScheduleSyncAdapter(applicationContext, true, false)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return mSyncAdapter?.syncAdapterBinder
    }

}