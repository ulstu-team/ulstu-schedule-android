package ru.ulstu_team.ulstuschedule.data.remote.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ScheduleAuthenticatorService() : Service() {

    private lateinit var mAuthenticator: ScheduleAuthenticator

    init {
        mAuthenticator = ScheduleAuthenticator(this)
    }

    override fun onBind(p0: Intent?): IBinder?
            = mAuthenticator.iBinder

}