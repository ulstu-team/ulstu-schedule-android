package ulstu.schedule.storage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.lang.String;import java.util.ArrayList;
import java.util.List;

import ulstu.schedule.models.Cathedra;

public class Cathedries implements BaseColumns {
    private ScheduleDatabaseHelper _dbHelper;

    static final String TABLE_NAME = "cathedra";
    static final String _ID = "cathedra_id";
    static final String COLUMN_NAME_NAME = "cathedra_name";

    static final String SQL_CREATE_CATHEDRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_NAME + " TEXT " + ")";

    static final String SQL_DELETE_CATHEDRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Cathedries(ScheduleDatabaseHelper db){
        _dbHelper = db;
    }

    public Cathedra get(long id){
        SQLiteDatabase db = _dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                _ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c == null) return null;
        c.moveToFirst();

        Cathedra cathedra = getCathedraFromDb(c);
        return cathedra;
    }

    public List<Cathedra> getAll(){
        List<Cathedra> cathedries = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c == null) return cathedries;

        if (c.moveToFirst()){
            do {
                Cathedra cathedra = getCathedraFromDb(c);
                cathedries.add(cathedra);
            } while (c.moveToNext());
        }
        return cathedries;
    }

    public long insert(Cathedra cathedra){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, cathedra.Name);

        long id = db.insert(TABLE_NAME, null, values);
        return id;
    }

    public void insert(List<Cathedra> cathedries){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        for (Cathedra cathedra : cathedries) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_NAME, cathedra.Name);

            db.insert(TABLE_NAME, null, values);
        }
    }

    public void delete(Cathedra cathedra){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE " + _ID + " = " + cathedra.Id;

        db.execSQL(deleteQuery);
    }

    public void deleteAll(){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME;

        db.execSQL(deleteQuery);
    }

    static Cathedra getCathedraFromDb(Cursor c){
        Cathedra cathedra = new Cathedra();
        cathedra.Id = c.getInt(c.getColumnIndex(_ID));
        cathedra.Name = c.getString(c.getColumnIndex(COLUMN_NAME_NAME));

        return cathedra;
    }
}
