package ulstu.schedule.storage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import ulstu.schedule.models.Lesson;

public class Lessons implements BaseColumns {
    private ScheduleDatabaseHelper _dbHelper;

    static final String TABLE_NAME = "lesson";
    static final String _ID = "lesson_id";
    static final String COLUMN_NAME_NUMBER = "lesson_number";
    static final String COLUMN_NAME_NAME = "lesson_name";
    static final String COLUMN_NAME_DAY_OF_WEEK = "lesson_day_of_week";
    static final String COLUMN_NAME_NUMBER_OF_WEEK = "lesson_number_of_week";
    static final String COLUMN_NAME_CABINET = "lesson_cabinet";
    static final String COLUMN_NAME_GROUP_ID = "lesson_group_id";
    static final String COLUMN_NAME_TEACHER_ID = "lesson_teacher_id";

    static final String SQL_CREATE_LESSONS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_NUMBER + " INTEGER, " +
                    COLUMN_NAME_NAME + " TEXT, " +
                    COLUMN_NAME_DAY_OF_WEEK + " INTEGER, " +
                    COLUMN_NAME_NUMBER_OF_WEEK + " INTEGER, " +
                    COLUMN_NAME_CABINET + " TEXT, " +
                    COLUMN_NAME_GROUP_ID + " INTEGER, " +
                    COLUMN_NAME_TEACHER_ID + " INTEGER " +
                    ")";

    static final String SQL_DELETE_LESSONS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Lessons(ScheduleDatabaseHelper db){
        _dbHelper = db;
    }

    public Lesson get(long id){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + ", " + Groups.TABLE_NAME + ", " + Teachers.TABLE_NAME +
                " WHERE " + _ID + " = " + id +
                " WHERE " + TABLE_NAME + "." + COLUMN_NAME_GROUP_ID + " = " + Groups.TABLE_NAME + "." + Groups._ID +
                " WHERE " + TABLE_NAME + "." + COLUMN_NAME_TEACHER_ID + " = " + Teachers.TABLE_NAME + "." + Teachers._ID;


        Cursor c = db.rawQuery(selectQuery, null);
        if (c == null) return null;
        c.moveToFirst();

        Lesson lesson = getLessonFromDb(c);
        return lesson;
    }

    public List<Lesson> getAll(){
        List<Lesson> lessons = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c == null) return lessons;

        if (c.moveToFirst()){
            do {
                Lesson lesson = getLessonFromDb(c);
                lessons.add(lesson);
            } while (c.moveToNext());
        }
        return lessons;
    }

    public long insert(Lesson lesson){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        ContentValues values = getLessonContentValues(lesson);

        long id = db.insert(TABLE_NAME, null, values);
        return id;
    }

    public void insert(List<Lesson> lessons){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        for (Lesson lesson : lessons) {
            ContentValues values = getLessonContentValues(lesson);

            db.insert(TABLE_NAME, null, values);
        }
    }

    private ContentValues getLessonContentValues(Lesson lesson){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NUMBER, lesson.Number);
        values.put(COLUMN_NAME_NAME, lesson.Name);
        values.put(COLUMN_NAME_DAY_OF_WEEK, lesson.DayOfWeek);
        values.put(COLUMN_NAME_NUMBER_OF_WEEK, lesson.NumberOfWeek);
        values.put(COLUMN_NAME_CABINET, lesson.Cabinet);
        values.put(COLUMN_NAME_GROUP_ID, lesson.GroupId);
        values.put(COLUMN_NAME_TEACHER_ID, lesson.TeacherId);

        return values;
    }

    public void delete(Lesson lesson){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE " + _ID + " = " + lesson.Id;

        db.execSQL(deleteQuery);
    }

    public void deleteAll(){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME;

        db.execSQL(deleteQuery);
    }

    Lesson getLessonFromDb(Cursor c){
        Lesson lesson = new Lesson();
        lesson.Id = c.getInt(c.getColumnIndex(_ID));
        lesson.Number = c.getInt(c.getColumnIndex(COLUMN_NAME_NUMBER));
        lesson.Name = c.getString(c.getColumnIndex(COLUMN_NAME_NAME));
        lesson.DayOfWeek = c.getInt(c.getColumnIndex(COLUMN_NAME_DAY_OF_WEEK));
        lesson.NumberOfWeek = c.getInt(c.getColumnIndex(COLUMN_NAME_NUMBER_OF_WEEK));
        lesson.Cabinet = c.getString(c.getColumnIndex(COLUMN_NAME_CABINET));

        lesson.GroupId = c.getInt(c.getColumnIndex(COLUMN_NAME_GROUP_ID));
        lesson.Group = Groups.getGroupFromDb(c);

        lesson.TeacherId = c.getInt(c.getColumnIndex(COLUMN_NAME_TEACHER_ID));
        lesson.Teacher = Teachers.getTeacherFromDb(c);

        return lesson;
    }
}