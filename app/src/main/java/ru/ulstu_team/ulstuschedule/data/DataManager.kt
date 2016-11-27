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

    val userId: Int?
        get() = mPrefsManager.getInt(PrefsKeys.USER_ID)

    val userName: String
        get() = mPrefsManager.getString(PrefsKeys.USER_NAME)


    fun getFavorites(): List<Favorite> = mRealm.where(Favorite::class.java).findAll()

    fun addToFavorites(ownerId: Int, ownerName: String, ownerType: String, isSaved: Boolean) {
        if (!containsInFavorites(`@+id/teacher`)) {
            val fav = Favorite().apply {
                this.ownerId = ownerId
                this.isSaved = isSaved
                this.name = `@+id/teacher`
                this.type = ownerType
            }
            mRealm.executeTransaction {
                mRealm.copyToRealm(fav)
            }
        }
    }

    fun removeFromFavorites(ownerName: String) {
        val fav = mRealm.where(Favorite::class.java).equalTo("Name", `@+id/teacher`).findFirst()
        if (fav != null) {
            mRealm.executeTransaction { fav.removeFromRealm() }
        }
    }

    fun containsInFavorites(ownerName: String): Boolean =
            mRealm.where(Favorite::class.java).equalTo("Name", `@+id/teacher`).findFirst() == null

    //    fun isSavedInDatabase(ownerName: String, ownerType:String): Boolean {
    //        if (!containsInFavorites(ownerName))
    //            return false
    //
    //        return when (ownerType) {
    //            "group" -> mRealm.where(Lesson::class.java)
    //        }
    //    }

    fun getFaculties(): List<Faculty> {
        val result = mRealm.where(Faculty::class.java).findAll()
        result.sort("Name")
        return result
    }

    fun getCathedries(): List<Cathedra> {
        val result = mRealm.where(Cathedra::class.java).findAll()
        result.sort("Name")
        return result
    }

    fun getFacultyCathedries(facultyId: Int): List<Cathedra> {
        val result = mRealm.where(Cathedra::class.java).equalTo("FacultyId", facultyId).findAll()
        result.sort("Name")
        return result
    }

    fun getGroups(): List<Group> {
        val result = mRealm.where(Group::class.java).findAll()
        result.sort("Name")
        return result
    }

    fun getTeachers(): List<Teacher> {
        val result = mRealm.where(Teacher::class.java).findAll()
        result.sort("Name")
        return result
    }

    fun getCathedraTeachers(cathedraId: Int): List<Teacher> {
        val result = mRealm.where(Teacher::class.java).equalTo("CathedraId", cathedraId).findAll()
        result.sort("Name")
        return result
    }

    fun getLessonsForCurrentGroup(): List<Lesson> = mRealm.where(Lesson::class.java)
            .equalTo("GroupId", userId).findAll()

    fun getLessonsForGroup(id: Int): List<Lesson> = mRealm.where(Lesson::class.java)
            .equalTo("GroupId", id).findAll()

    fun getLessonsForCurrentTeacher(): List<Lesson> = mRealm.where(Lesson::class.java)
            .equalTo("TeacherId", userId).findAll()

    fun getLessonsForTeacher(teacherId: Int): List<Lesson> = mRealm.where(Lesson::class.java)
            .equalTo("TeacherId", teacherId).findAll()


    fun loadFaculties(callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.FACULTIES, Faculty::class.java,
                    mRealm.where(Faculty::class.java), callbacks))

    fun loadCathedries(callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.CATHEDRIES, Cathedra::class.java,
                    mRealm.where(Cathedra::class.java), callbacks))

    fun loadFacultyCathedries(facultyId: Int, callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.CATHEDRIES, facultyId, Cathedra::class.java,
                    mRealm.where(Cathedra::class.java).equalTo("FacultyId", facultyId), callbacks))

    fun loadGroups(callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.GROUPS, Group::class.java,
                    mRealm.where(Group::class.java), callbacks))

    fun loadTeachers(callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.TEACHERS, Teacher::class.java,
                    mRealm.where(Teacher::class.java), callbacks))

    fun loadCathedraTeachers(cathedraId: Int, callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.TEACHERS, cathedraId, Teacher::class.java,
                    mRealm.where(Teacher::class.java).equalTo("CathedraId", cathedraId), callbacks))

    fun loadLessonsForCurrentGroup(callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.GROUP_LESSONS, userId!!, Lesson::class.java,
                    mRealm.where(Lesson::class.java).equalTo("GroupId", userId), callbacks))

    fun loadLessonsForGroup(groupId: Int, callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.GROUP_LESSONS, groupId, Lesson::class.java,
                    mRealm.where(Lesson::class.java).equalTo("GroupId", groupId), callbacks))

    fun loadLessonsForCurrentTeacher(callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.TEACHERS_LESSONS, Lesson::class.java,
                    mRealm.where(Lesson::class.java).equalTo("TeacherId", userId), callbacks))

    fun loadLessonsForTeacher(teacherId: Int, callbacks: RequestCallbacks) =
            executeRequest(ScheduleRequest(Schedule.TEACHERS_LESSONS, Lesson::class.java,
                    mRealm.where(Lesson::class.java).equalTo("TeacherId", teacherId), callbacks))


    private fun executeRequest(request: ScheduleRequest) {
        val volleyRequest = StringRequest(UrlManager.getUrl(request.key, request.id),
                Response.Listener<String> { response -> saveInDatabase(response, request) },
                Response.ErrorListener { error -> request.callbacks.onError(error) }
        ).setRetryPolicy(object : RetryPolicy {
            override fun getCurrentTimeout() = 3000
            override fun getCurrentRetryCount() = 2

            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) { }
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


        val objects = request.realmQuery.findAll()
        mRealm.executeTransaction {
            objects.clear()
        }

        mRealm.executeTransaction { realm ->
            if (mIsOneModel) {
                realm.createOrUpdateObjectFromJson(clazz, json)
            } else {
                mRealm.createOrUpdateAllFromJson(clazz, json)
            }
            request.callbacks.onSuccess()
        }
    }
}
