package com.example.sked.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sked.domain.Group;
import com.example.sked.domain.Institute;
import com.example.sked.domain.MyGroup;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static Database instance;

    private static String DATABASE_NAME = "db_sked";
    private static int DATABASE_VERSION = 4;

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

    //my-groups
    private static final String TABLE_MY_GROUPS = "my_groups";
    private static final String KEY_ID_G = "_id";
    private static final String NAME_G = "name";
    private static final String DEPARTMENT_NAME_G = "department_name";
    private static final String COURSE_NAME_G = "course_name";
    private static final String FACULTY_NAME_G = "faculty_name";
    private static final String SEMESTER_NAME_G = "semester_name";
    private static final String SEMESTER_START_G = "semester_start";
    private static final String SEMESTER_FINISH_G = "semester_finish";
    private static final String DIVISION_NAME_G = "division_name";
    private static final String LAST_MODIFIED_G = "last_modified";


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

        db.execSQL("create table " + TABLE_MY_GROUPS + "("
                + KEY_ID_G + " integer primary key, "
                + NAME_G + " text,"
                + DEPARTMENT_NAME_G + " text,"
                + COURSE_NAME_G + " text,"
                + FACULTY_NAME_G + " text,"
                + SEMESTER_NAME_G + " text,"
                + SEMESTER_START_G + " insteger,"
                + SEMESTER_FINISH_G + " insteger,"
                + DIVISION_NAME_G + " text,"
                + LAST_MODIFIED_G + " integer"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_MY_INSTITUTIONS);
        onCreate(db);
    }

    public void addInstitute(Institute institute) {
        SQLiteDatabase db = this.getWritableDatabase();
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

    public void addGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(NAME_G, group.getName());
        content.put(DEPARTMENT_NAME_G, group.getDepartment().getName());
        content.put(COURSE_NAME_G, group.getDepartment().getCourse().getName());
        content.put(FACULTY_NAME_G, group.getDepartment().getCourse().getFaculty().getName());
        content.put(SEMESTER_NAME_G, group.getDepartment().getCourse().getFaculty().getSemester().getName());
        content.put(SEMESTER_START_G, group.getDepartment().getCourse().getFaculty().getSemester().getStart());
        content.put(SEMESTER_FINISH_G, group.getDepartment().getCourse().getFaculty().getSemester().getFinish());
        content.put(DIVISION_NAME_G, group.getDepartment().getCourse().getFaculty().getSemester().getDivision().getName());
        content.put(LAST_MODIFIED_G, group.getDepartment().getCourse().getFaculty().getSemester().getDivision().getLastModified());


        db.insert(TABLE_MY_GROUPS, null, content);
        db.close();
    }

    public void deleteGroupById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MY_GROUPS, KEY_ID_G + "= ?", new String[] {id});
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

    public List<MyGroup> getAllGroups() {
        List<MyGroup> groups = new ArrayList<>();

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_MY_GROUPS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(KEY_ID_G);
            int name = cursor.getColumnIndex(NAME_G);
            int departmentName = cursor.getColumnIndex(DEPARTMENT_NAME_G);
            int courseName = cursor.getColumnIndex(COURSE_NAME_G);
            int facultyName = cursor.getColumnIndex(FACULTY_NAME_G);
            int semesterName = cursor.getColumnIndex(SEMESTER_NAME_G);
            int semesterStart = cursor.getColumnIndex(SEMESTER_START_G);
            int semesterFinish = cursor.getColumnIndex(SEMESTER_FINISH_G);
            int divisionName = cursor.getColumnIndex(DIVISION_NAME_G);
            int lastModified = cursor.getColumnIndex(LAST_MODIFIED_G);

            do {
                MyGroup group = new MyGroup();
                group.setId(cursor.getLong(id));
                group.setName(cursor.getString(name));
                group.setDepartmentName(cursor.getString(departmentName));
                group.setCourseName(cursor.getString(courseName));
                group.setFacultyName(cursor.getString(facultyName));
                group.setSemesterName(cursor.getString(semesterName));
                group.setSemesterStart(cursor.getLong(semesterStart));
                group.setSemesterFinish(cursor.getLong(semesterFinish));
                group.setDivisionName(cursor.getString(divisionName));
                group.setLastModified(cursor.getLong(lastModified));

                groups.add(group);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return groups;
    }
}
