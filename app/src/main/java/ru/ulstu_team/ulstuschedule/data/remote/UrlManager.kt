package ru.ulstu_team.ulstuschedule.data.remote

class UrlManager {

    companion object {
        val URL_BASE_PART = "http://ulstuschedule.azurewebsites.net/ulstu/"
        val URL_STUDENT_SCHEDULE_UPDATE_DATE =
                "http://ulstuschedule.azurewebsites.net/Schedule/StudentScheduleUpdateDate"
        val URL_TEACHER_SCHEDULE_UPDATE_DATE =
                "http://ulstuschedule.azurewebsites.net/Schedule/TeacherScheduleUpdateDate"

        fun getUrl(key: String, id: Int): String {
            var url = URL_BASE_PART + key
            url = if (id != 0) url + id else url
            return url
        }
    }
}
