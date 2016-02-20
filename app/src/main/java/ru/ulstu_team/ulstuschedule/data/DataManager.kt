package ru.ulstu_team.ulstuschedule.data

import android.content.Context
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import io.realm.Realm
import io.realm.RealmObject
import ru.ulstu_team.ulstuschedule.data.local.PrefsKeys
import ru.ulstu_team.ulstuschedule.data.local.PrefsManager
import ru.ulstu_team.ulstuschedule.data.model.*
import ru.ulstu_team.ulstuschedule.data.remote.*
import javax.inject.Inject

class DataManager
@Inject
constructor(val context: Context, private val mPrefsManager: PrefsManager, private val mVolley: VolleySingleton) {

    private val mRealm = Realm.getDefaultInstance()

    fun dispose() = { mRealm.close() }

    val userId: Int
        get() = mPrefsManager.getInt(PrefsKeys.USER_ID)

    val userName: String
        get() = mPrefsManager.getString(PrefsKeys.USER_NAME)

    private fun getUrl(key: String, id: Int): String {
        var url = URL_BASE_PART + key
        url = if (id != 0) url + id else url
        return url
    }

    private fun executeRequest(request: ScheduleRequest) {
        val volleyRequest = StringRequest(getUrl(request.key, request.id),
                Response.Listener<String> { response -> saveInDatabase(response, request) },
                Response.ErrorListener { error -> request.callbacks.onError(error) }
        ).setRetryPolicy(object : RetryPolicy {
            override fun getCurrentTimeout() = 3000

            override fun getCurrentRetryCount() = 2

            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {
            }
        })
        mVolley.addToRequestQueue(volleyRequest)
    }

    private fun saveInDatabase(json: String?, request: ScheduleRequest) {
        if (json == null || json.isEmpty()) {
            request.callbacks.onError(DownloadException())
            return
        }
        //val mRealm = Realm.getDefaultInstance()

        // If json starts with '{' then it is a JSONObject and model is one
        val mIsOneModel = json.trim { it <= ' ' }[0] == '{'
        val clazz: Class<out RealmObject> = request.dataType

        mRealm.beginTransaction()

        val objects = request.realmQuery.findAll()
        objects.clear()

        if (mIsOneModel) {
            mRealm.createOrUpdateObjectFromJson(clazz, json)
        } else {
            mRealm.createOrUpdateAllFromJson(clazz, json)
        }

        mRealm.commitTransaction()
        request.callbacks.onSuccess()

        //        mRealm.executeTransaction(
        //                new Realm.Transaction() {
        //
        //                    @Override
        //                    public void execute(Realm realm) {
        //                        RealmResults objects = request.getRealmQuery().findAll();
        //                        objects.clear();
        //
        //                        if (mIsOneModel) {
        //                            mRealm.createOrUpdateObjectFromJson(clazz, json);
        //                        } else {
        //                            mRealm.createOrUpdateAllFromJson(clazz, json);
        //                        }
        //                    }
        //                }, new Realm.Transaction.Callback() {
        //                    @Override
        //                    public void onSuccess() {
        //                        request.getCallbacks().onSuccess();
        //                    }
        //
        //                    @Override
        //                    public void onError(Exception e) {
        //                        request.getCallbacks().onError(e);
        //                    }
        //                }
        //        );
        //mRealm.close()
    }

    fun getFaculties(): List<Faculty> = mRealm.where(Faculty::class.java).findAll()

    fun getCathedries(): List<Cathedra> = mRealm.where(Cathedra::class.java).findAll()

    fun getFacultyCathedries(facultyId: Int): List<Cathedra> = mRealm.where(Cathedra::class.java)
            .equalTo("FacultyId", facultyId).findAll()

    fun getGroups(): List<Group> = mRealm.where(Group::class.java).findAll()

    fun getTeachers(): List<Teacher> = mRealm.where(Teacher::class.java).findAll()


    fun getLessonsForCurrentGroup(): List<Lesson> = mRealm.where(Lesson::class.java)
            .equalTo("GroupId", userId).findAll()

    fun getLessonsForGroup(id: Int): List<Lesson> = mRealm.where(Lesson::class.java)
            .equalTo("GroupId", id).findAll()

    fun getLessonsForCurrentTeacher(): List<Lesson> = mRealm.where(Lesson::class.java)
            .equalTo("TeacherId", userId).findAll()

    fun getLessonsForTeacher(teacherId: Int): List<Lesson> = mRealm.where(Lesson::class.java)
            .equalTo("TeacherId", teacherId).findAll()

    fun loadFaculties(callbacks: RequestCallbacks) =
            executeRequest(
                    ScheduleRequest(Schedule.FACULTIES, Faculty::class.java,
                            mRealm.where(Faculty::class.java),
                            callbacks)
            )

    fun loadCathedries(callbacks: RequestCallbacks) =
            executeRequest(
                    ScheduleRequest(Schedule.CATHEDRIES, Cathedra::class.java,
                            mRealm.where(Cathedra::class.java),
                            callbacks)
            )

    fun loadGroups(callbacks: RequestCallbacks) =
            executeRequest(
                    ScheduleRequest(Schedule.GROUPS, Group::class.java,
                            mRealm.where(Group::class.java),
                            callbacks)
            )

    fun loadFacultyCathedries(facultyId: Int, callbacks:RequestCallbacks) =
            executeRequest(
                    ScheduleRequest(Schedule.CATHEDRIES, facultyId, Cathedra::class.java,
                            mRealm.where(Cathedra::class.java).equalTo("FacultyId", facultyId),
                            callbacks)
            )

    fun loadLessonsForCurrentGroup(callbacks: RequestCallbacks) =
            executeRequest(
                    ScheduleRequest(Schedule.GROUP_LESSONS, userId, Lesson::class.java,
                            mRealm.where(Lesson::class.java).equalTo("GroupId", userId),
                            callbacks)
            )

    fun loadLessonsForGroup(groupId: Int, callbacks: RequestCallbacks) =
            executeRequest(
                    ScheduleRequest(Schedule.GROUP_LESSONS, groupId, Lesson::class.java,
                            mRealm.where(Lesson::class.java).equalTo("GroupId", groupId),
                            callbacks)
            )

    fun loadLessonsForCurrentTeacher(callbacks: RequestCallbacks) =
            executeRequest(
                    ScheduleRequest(Schedule.TEACHERS_LESSONS, Lesson::class.java,
                            mRealm.where(Lesson::class.java).equalTo("TeacherId", userId),
                            callbacks)
            )

    fun loadLessonsForTeacher(teacherId: Int, callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.TEACHERS_LESSONS, Lesson::class.java,
                    mRealm.where(Lesson::class.java).equalTo("TeacherId", teacherId),
                    callbacks)
            )

    companion object {
        private val URL_BASE_PART = "http://ulstuschedule.azurewebsites.net/ulstu/"
    }
}
