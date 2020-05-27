package com.example.sked.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sked.domain.Institute;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static Database instance;

    private static String DATABASE_NAME = "db_sked";
    private static int DATABASE_VERSION = 3;

    private Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized Database getInstance(@Nullable Context context) {
        if (instance == null) {
            instance = new Database(context);
        }
        return instance;
    }

    //my-institutes
    private static final String TABLE_MY_INSTITUTIONS = "my_institutions";
    private static final String KEY_ID_INST = "_id";
    private static final String NAME_INST = "name";
    private static final String DESCRIPTION_INST = "description";
    private static final String SITE_INST = "site";
    private static final String PHONE_INST = "phone";
    private static final String MAIL_INST = "mail";
    private static final String USER_ID_INST = "user_id";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_MY_INSTITUTIONS + "("
                + KEY_ID_INST + " integer primary key, "
                + NAME_INST + " text,"
                + DESCRIPTION_INST + " text,"
                + SITE_INST + " text,"
                + PHONE_INST + " text,"
                + MAIL_INST + " text,"
                + USER_ID_INST + " integer"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_MY_INSTITUTIONS);
        onCreate(db);
    }

    public void addInstitute(Institute institute) {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(NAME_INST, institute.getName());
        content.put(DESCRIPTION_INST, institute.getDescription());
        content.put(SITE_INST, institute.getSite());
        content.put(PHONE_INST, institute.getPhone());
        content.put(MAIL_INST, institute.getMail());
        content.put(USER_ID_INST, institute.getUser().getId());

        db.insert(TABLE_MY_INSTITUTIONS, null, content);
        db.close();
    }

    public void deleteInstituteById(String id) {
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(TABLE_MY_INSTITUTIONS, KEY_ID_INST + "= ?", new String[] {id});
        db.close();
    }

    public List<Institute> getAllInstituteNames() {
        List<Institute> institutes = new ArrayList<>();

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_MY_INSTITUTIONS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(KEY_ID_INST);
            int name = cursor.getColumnIndex(NAME_INST);
            int userId = cursor.getColumnIndex(USER_ID_INST);

            do {
                Institute institute = new Institute();
                institute.setId(cursor.getLong(id));
                institute.setName(cursor.getString(name));
                institute.setUserId(cursor.getLong(userId));
                institutes.add(institute);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return institutes;
    }
}
