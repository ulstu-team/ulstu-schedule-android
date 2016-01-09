package ulstu.schedule.storage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import ulstu.schedule.models.Teacher;

public class Teachers implements BaseColumns {
    private ScheduleDatabaseHelper _dbHelper;

    static final String TABLE_NAME = "teacher";
    static final String _ID = "teacher_id";
    static final String COLUMN_NAME_NAME = "teacher_name";
    static final String COLUMN_NAME_CATHEDRA_ID = "teacher_cathedra_id";

    static final String SQL_CREATE_TEACHERS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_NAME + " TEXT, " +
                    COLUMN_NAME_CATHEDRA_ID + " INTEGER " +
                    ")";

    static final String SQL_DELETE_TEACHERS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Teachers(ScheduleDatabaseHelper db){
        _dbHelper = db;
    }

    public Teacher get(long id){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + ", " + Cathedries.TABLE_NAME +
                " WHERE " + TABLE_NAME + "." + _ID + " = " + id +
                " AND " + TABLE_NAME + "." + COLUMN_NAME_CATHEDRA_ID + " = " + Cathedries.TABLE_NAME + "." + Cathedries._ID;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c == null) return null;
        c.moveToFirst();

        Teacher teacher = getTeacherFromDb(c);
        return teacher;
    }

    public List<Teacher> getAll(){
        List<Teacher> teachers = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + ", " + Cathedries.TABLE_NAME;

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c == null) return teachers;

        if (c.moveToFirst()){
            do {
                Teacher teacher = getTeacherFromDb(c);
                teachers.add(teacher);
            } while (c.moveToNext());
        }
        return teachers;
    }

    public long insert(Teacher teacher){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, teacher.Name);
        values.put(COLUMN_NAME_CATHEDRA_ID, teacher.CathedraId);

        long id = db.insert(TABLE_NAME, null, values);
        return id;
    }

    public void insert(List<Teacher> teachers){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        for (Teacher teacher : teachers) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_NAME, teacher.Name);
            values.put(COLUMN_NAME_CATHEDRA_ID, teacher.CathedraId);

            db.insert(TABLE_NAME, null, values);
        }
    }

    public void delete(Teacher teacher){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE " + _ID + " = " + teacher.Id;

        db.execSQL(deleteQuery);
    }

    public void deleteAll(){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME;

        db.execSQL(deleteQuery);
    }

    static Teacher getTeacherFromDb(Cursor c){
        Teacher teacher = new Teacher();
        teacher.Id = c.getInt(c.getColumnIndex(_ID));
        teacher.Name = c.getString(c.getColumnIndex(COLUMN_NAME_NAME));
        teacher.CathedraId = c.getInt(c.getColumnIndex(COLUMN_NAME_CATHEDRA_ID));
        teacher.Cathedra = Cathedries.getCathedraFromDb(c);

        return teacher;
    }
}
