package ulstu.schedule.storage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import ulstu.schedule.models.Faculty;

public class Faculties implements BaseColumns {
    private ScheduleDatabaseHelper _dbHelper;

    static final String TABLE_NAME = "faculty";
    static final String _ID = "faculty_id";
    static final String COLUMN_NAME_NAME = "faculty_name";

    static final String SQL_CREATE_FACULTIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_NAME + " TEXT " + ")";

    static final String SQL_DELETE_FACULTIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Faculties(ScheduleDatabaseHelper db){
        _dbHelper = db;
    }

    public Faculty get(long id){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                _ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c == null) return null;
        c.moveToFirst();

        Faculty faculty = getFacultyFromDb(c);
        return faculty;
    }

    public List<Faculty> getAll(){
        List<Faculty> faculties = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c == null) return faculties;

        if (c.moveToFirst()){
            do {
                Faculty faculty = getFacultyFromDb(c);
                faculties.add(faculty);
            } while (c.moveToNext());
        }
        return faculties;
    }

    public long insert(Faculty faculty){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, faculty.Name);

        long id = db.insert(TABLE_NAME, null, values);
        return id;
    }

    public void insert(List<Faculty> faculties){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        for (Faculty faculty : faculties) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_NAME, faculty.Name);

            db.insert(TABLE_NAME, null, values);
        }
    }

    public void delete(Faculty faculty){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE " + _ID + " = " + faculty.Id;

        db.execSQL(deleteQuery);
    }

    public void deleteAll(){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME;

        db.execSQL(deleteQuery);
    }

    static Faculty getFacultyFromDb(Cursor c){
        Faculty faculty = new Faculty();
        faculty.Id = c.getInt(c.getColumnIndex(_ID));
        faculty.Name = c.getString(c.getColumnIndex(COLUMN_NAME_NAME));

        return faculty;
    }
}