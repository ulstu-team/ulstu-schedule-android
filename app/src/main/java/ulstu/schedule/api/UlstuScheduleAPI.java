package ulstu.schedule.api;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import ru.ulstu_team.ulstuschedule.databinding.TeacherListItemBinding;
import ulstu.schedule.models.Cathedra;
import ulstu.schedule.models.Faculty;
import ulstu.schedule.models.Group;
import ulstu.schedule.models.Lesson;
import ulstu.schedule.models.Teacher;
import ulstu.schedule.models.TeacherLessons;

public class UlstuScheduleAPI
        implements ReceiverSetter, Request, JsonDownloadTask.OnDownloadedListener {

    private static final String URL_BASE_PART = "http://ulstuschedule.azurewebsites.net/ulstu/";

    private static UlstuScheduleAPI mUlstuScheduleAPI;
    private UlstuScheduleAPI() { }

    private static String mUrl;
    private static Type mType;
    private static ScheduleReceiver mReceiver;

    public static ReceiverSetter makeRequest(String key) {
        prepareNewRequest(key);
        mUrl = URL_BASE_PART + key;
        return mUlstuScheduleAPI;
    }

    public static ReceiverSetter makeRequest(String key, int id) {
        prepareNewRequest(key);
        mUrl = URL_BASE_PART + key + "/" + id;
        return mUlstuScheduleAPI;
    }

    @Override
    public Request setReceiver(ScheduleReceiver receiver) {
        mReceiver = receiver;
        return mUlstuScheduleAPI;
    }

    @Override
    public void request() {
        new JsonDownloadTask(mUrl, this)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onDownloaded(String response) {
        Gson gson = new GsonBuilder().create();
        // TODO: carefully testing! Add exception to method signature
        if (response == null || response.isEmpty())
            throw new DownloadException();
        mReceiver.onDataReceived(gson.fromJson(response, mType));
    }

    private static void prepareNewRequest(String key) {
        if (mUlstuScheduleAPI == null)
            mUlstuScheduleAPI = new UlstuScheduleAPI();

        switch (key) {
            case Schedule.GROUP:
                mType = Group.class;
                break;
            case Schedule.GROUPS:
                mType = Group[].class;
                break;
            case Schedule.CATHEDRA:
                mType = Cathedra.class;
                break;
            case Schedule.CATHEDRIES:
                mType = Cathedra[].class;
                break;
            case Schedule.FACULTY:
                mType = Faculty.class;
                break;
            case Schedule.FACULTIES:
                mType = Faculty[].class;
                break;
            case Schedule.LESSON:
                mType = Lesson.class;
                break;
            case Schedule.LESSONS:
                mType = Lesson[].class;
                break;
            case Schedule.TEACHER:
                mType = Teacher.class;
                break;
            case Schedule.TEACHERS:
                mType = Teacher[].class;
                break;
            case Schedule.TEACHER_LESSONS:
                mType = TeacherLessons.class;
                break;
        }

        mUrl = null;
        mReceiver = null;
    }
}