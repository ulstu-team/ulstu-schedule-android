package ru.ulstu_team.ulstuschedule.data.remote.sync

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import ru.ulstu_team.ulstuschedule.data.DataManager
import ru.ulstu_team.ulstuschedule.data.local.PrefsKeys
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager
import ru.ulstu_team.ulstuschedule.data.model.FavoriteType
import ru.ulstu_team.ulstuschedule.data.remote.RequestCallbacks
import ru.ulstu_team.ulstuschedule.data.remote.UrlManager
import ru.ulstu_team.ulstuschedule.data.remote.VolleySingleton
import javax.inject.Inject

class ScheduleSyncAdapter(context: Context, autoInitialize: Boolean, allowParallelSyncs: Boolean)
: AbstractThreadedSyncAdapter(context, autoInitialize, allowParallelSyncs) {

    @Inject internal lateinit var mDataManager: DataManager

    private val mPrefsManager = PrefsManager(context)
    private val mVolley: VolleySingleton = VolleySingleton.getInstance(context)
    private var isSyncSucceed = true

    override fun onPerformSync(account: Account?,
                               extras: Bundle?,
                               authority: String?,
                               provider: ContentProviderClient?,
                               syncResult: SyncResult?) {

        checkAndUpdateFavorites()
        syncStudentsSchedule()
        syncTeachersSchedule()
    }

    private fun checkAndUpdateFavorites() {
        val dateOfLastStudentScheduleUpdate =
                mPrefsManager.getLong(PrefsKeys.STUDENT_SCHEDULE_LAST_UPDATE)
        val dateOfLastTeacherScheduleUpdate =
                mPrefsManager.getLong(PrefsKeys.TEACHER_SCHEDULE_LAST_UPDATE)

        mVolley.addToRequestQueue(
                StringRequest(UrlManager.URL_STUDENT_SCHEDULE_UPDATE_DATE,
                        Response.Listener<String> { response ->
                            if (dateOfLastStudentScheduleUpdate < response.toLong())
                                syncStudentsSchedule()
                        },
                        Response.ErrorListener { }
                ).setRetryPolicy(retryPolicy)
        )

        mVolley.addToRequestQueue(
                StringRequest(UrlManager.URL_TEACHER_SCHEDULE_UPDATE_DATE,
                        Response.Listener<String> { response ->
                            if (dateOfLastTeacherScheduleUpdate < response.toLong())
                                syncTeachersSchedule()
                        },
                        Response.ErrorListener { }
                ).setRetryPolicy(retryPolicy)
        )
    }

    private fun syncTeachersSchedule() {
        val favs = mDataManager.getFavorites()
        favs.forEach {
            if (it.type == FavoriteType.TEACHER)
                mDataManager.loadLessonsForTeacher(it.id,
                        object : RequestCallbacks {
                            override fun onSuccess() {
                                it.isSaved = true
                            }

                            override fun onError(e: Exception) {
                                it.isSaved = false
                            }
                        })
        }
    }

    private fun syncStudentsSchedule() {
        val favs = mDataManager.getFavorites()
        favs.forEach {
            if (it.type == FavoriteType.STUDENT)
                mDataManager.loadLessonsForGroup(it.id,
                        object : ru.ulstu_team.ulstuschedule.data.remote.RequestCallbacks {
                            override fun onSuccess() {
                                it.isSaved = true
                            }

                            override fun onError(e: Exception) {
                                it.isSaved = false
                            }
                        })
        }
    }

    private val retryPolicy = object : RetryPolicy {
        override fun getCurrentTimeout() = 3000

        override fun getCurrentRetryCount() = 3

        @Throws(VolleyError::class)
        override fun retry(error: VolleyError) { }
    }
}
