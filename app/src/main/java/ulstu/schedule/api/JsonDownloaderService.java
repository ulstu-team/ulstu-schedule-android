package ulstu.schedule.api;

import android.app.IntentService;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public  class JsonDownloaderService extends IntentService {

    public JsonDownloaderService() {
        super("JsonDownloaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String path = intent.getStringExtra("url");
        String response = getContent(path);

        Intent broadcastIntent = new Intent(UlstuScheduleAPI.BROADCAST_ACTION);
        broadcastIntent.putExtra("json", response);
        sendBroadcast(broadcastIntent);
    }

    private String getContent(String path) {
        BufferedReader reader = null;
        String result = "";

        try {
            URL url = new URL(path);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Accept-Encoding", "gzip");
            c.setReadTimeout(3000);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(c.getInputStream())));
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
            result = buf.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

//    private void saveModelToDatabase(Object model, String key) {
//        ScheduleDatabaseHelper db = new ScheduleDatabaseHelper(this);
//        switch (key) {
//            case Schedule.GROUP:
//                db.getGroups().insert(Group.class.cast(model));
//                break;
//            case Schedule.GROUPS:
//                db.getGroups().insert(Arrays.asList(Group[].class.cast(model)));
//                break;
//            case Schedule.CATHEDRA:
//                db.getCathedries().insert(Cathedra.class.cast(model));
//                break;
//            case Schedule.CATHEDRIES:
//                db.getCathedries().insert(Arrays.asList(Cathedra[].class.cast(model)));
//                break;
//            case Schedule.FACULTY:
//                db.getFaculties().insert(Faculty.class.cast(model));
//                break;
//            case Schedule.FACULTIES:
//                db.getFaculties().insert(Arrays.asList(Faculty[].class.cast(model)));
//                break;
//            case Schedule.LESSON:
//                db.getLessons().insert(Lesson.class.cast(model));
//                break;
//            case Schedule.LESSONS:
//                db.getLessons().insert(Arrays.asList(Lesson[].class.cast(model)));
//                break;
//            case Schedule.TEACHER:
//                db.getTeachers().insert(Teacher.class.cast(model));
//                break;
//            case Schedule.TEACHERS:
//                db.getTeachers().insert(Arrays.asList(Teacher[].class.cast(model)));
//                break;
//            case Schedule.TEACHER_LESSONS:
//                TeacherLessons teacherLessons = TeacherLessons.class.cast(model);
//                db.
//                break;
//        }
//
//        return mType;
//    }
}
