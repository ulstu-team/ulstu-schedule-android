package ulstu.schedule.storage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import ulstu.schedule.models.Group;

public class Groups implements BaseColumns {
    private ScheduleDatabaseHelper _dbHelper;

    static final String TABLE_NAME = "groups";
    static final String _ID = "groups_id";
    static final String COLUMN_NAME_NAME = "group_name";

    static final String SQL_CREATE_GROUPS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_NAME + " TEXT " + ")";

    static final String SQL_DELETE_GROUPS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Groups(ScheduleDatabaseHelper db){
        _dbHelper = db;
    }

    public Group get(long id){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + _ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c == null) return null;
        c.moveToFirst();

        return getGroupFromDb(c);
    }

    public List<Group> getAll(){
        List<Group> groups = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c == null) return groups;

        if (c.moveToFirst()){
            do {
                Group group = getGroupFromDb(c);
                groups.add(group);
            } while (c.moveToNext());
        }
        return groups;
    }

    public long insert(Group group){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, group.Name);

        long id = db.insert(TABLE_NAME, null, values);
        return id;
    }

    public void insert(List<Group> groups){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        for (Group group : groups) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_NAME, group.Name);

            db.insert(TABLE_NAME, null, values);
        }
    }

    public void delete(Group group){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE " + _ID + " = " + group.Id;

        db.execSQL(deleteQuery);
    }

    public void deleteAll(){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME;

        db.execSQL(deleteQuery);
    }

    static Group getGroupFromDb(Cursor c){
        Group group = new Group();
        group.Id = c.getInt(c.getColumnIndex(_ID));
        group.Name = c.getString(c.getColumnIndex(COLUMN_NAME_NAME));

        return group;
    }
}