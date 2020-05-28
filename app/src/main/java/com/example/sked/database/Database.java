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
import com.example.sked.domain.Schedule;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static Database instance;

    private static String DATABASE_NAME = "db_sked";
    private static int DATABASE_VERSION = 12;

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
    private static final String FAVORITES_G = "favorites";
    private static final String VERSION_G = "version";

    //schedule
    private static final String TABLE_SCHEDULE = "schedule";
    private static final String KEY_ID_S = "_id";
    private static final String LESSON_NUMBER_S = "lesson_number";
    private static final String LESSON_NAME_S = "lesson_name";
    private static final String LESSON_TYPE_S = "lesson_type";
    private static final String TEACHER_SURNAME_S = "teacher_surname";
    private static final String BUILDING_S = "building";
    private static final String CABINET_S = "cabinet";
    private static final String LESSON_DATE_S = "lesson_date";
    private static final String NOTE_S = "note";
    private static final String GROUP_ID_S = "groupId";


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
                + LAST_MODIFIED_G + " integer,"
                + FAVORITES_G + " integer,"
                + VERSION_G + " integer"
                + ")");
        db.execSQL("create table " + TABLE_SCHEDULE + "("
                + KEY_ID_S + " integer primary key, "
                + LESSON_NUMBER_S + " integer,"
                + LESSON_NAME_S + " text,"
                + LESSON_TYPE_S + " text,"
                + TEACHER_SURNAME_S + " text,"
                + BUILDING_S + " text,"
                + CABINET_S + " text,"
                + LESSON_DATE_S + " integer,"
                + NOTE_S + " text,"
                + GROUP_ID_S + " integer"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_MY_INSTITUTIONS);
        db.execSQL("drop table if exists " + TABLE_MY_GROUPS);
        db.execSQL("drop table if exists " + TABLE_SCHEDULE);
        onCreate(db);
    }
    // institute ############################################################################################################################################################################################

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

    public void deleteInstituteById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MY_INSTITUTIONS, KEY_ID_INST + "= ?", new String[]{id});
        db.close();
    }

    public List<Institute> getAllInstituteNames() {
        List<Institute> institutes = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
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

    // group ################################################################################################################################################################################################

    public void addGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(KEY_ID_G, group.getId());
        content.put(NAME_G, group.getName());
        content.put(DEPARTMENT_NAME_G, group.getDepartment().getName());
        content.put(COURSE_NAME_G, group.getDepartment().getCourse().getName());
        content.put(FACULTY_NAME_G, group.getDepartment().getCourse().getFaculty().getName());
        content.put(SEMESTER_NAME_G, group.getDepartment().getCourse().getFaculty().getSemester().getName());
        content.put(SEMESTER_START_G, group.getDepartment().getCourse().getFaculty().getSemester().getStart());
        content.put(SEMESTER_FINISH_G, group.getDepartment().getCourse().getFaculty().getSemester().getFinish());
        content.put(DIVISION_NAME_G, group.getDepartment().getCourse().getFaculty().getSemester().getDivision().getName());
        content.put(LAST_MODIFIED_G, group.getDepartment().getCourse().getFaculty().getSemester().getDivision().getLastModified());
        content.put(FAVORITES_G, 0);
        content.put(VERSION_G, group.getVersion());


        db.insert(TABLE_MY_GROUPS, null, content);
        db.close();
    }

    public void deleteGroupById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MY_GROUPS, KEY_ID_G + "= ?", new String[]{id});
        db.close();
    }

    public List<MyGroup> getAllGroups() {
        List<MyGroup> groups = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
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
            int favorites = cursor.getColumnIndex(FAVORITES_G);
            int version = cursor.getColumnIndex(VERSION_G);

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
                group.setFavorites(cursor.getInt(favorites));
                group.setVersion(cursor.getInt(version));

                groups.add(group);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return groups;
    }

    public void setFavoritesGroup(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FAVORITES_G, 0);
        db.update(TABLE_MY_GROUPS, values, null, null);

        values.clear();
        values.put(FAVORITES_G, 1);
        String selection = KEY_ID_G + "= ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(TABLE_MY_GROUPS, values, selection, selectionArgs);
        db.close();
    }

    public MyGroup getFavoritesGroup() {
        MyGroup group = new MyGroup();

        String selection = FAVORITES_G + "= ?";
        String[] selectionArgs = {"1"};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_MY_GROUPS, null, selection, selectionArgs, null, null, null);

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
            int favorites = cursor.getColumnIndex(FAVORITES_G);
            int version = cursor.getColumnIndex(VERSION_G);

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
            group.setFavorites(cursor.getInt(favorites));
            group.setVersion(cursor.getInt(version));

        }
        cursor.close();
        db.close();

        return group;
    }

    public void setGroupVersion(Long id, int version) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VERSION_G, version);
        String selection = KEY_ID_G + "= ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(TABLE_MY_GROUPS, values, selection, selectionArgs);
        db.close();
    }

    // schedule #############################################################################################################################################################################################

    public void addListSchedule(List<Schedule> listSchedule) {
        if (!listSchedule.isEmpty()) {
            SQLiteDatabase db = this.getWritableDatabase();

            for (Schedule schedule : listSchedule) {
                ContentValues content = new ContentValues();
                content.put(KEY_ID_S, schedule.getId());
                content.put(LESSON_NUMBER_S, schedule.getLessonNumber());
                content.put(LESSON_NAME_S, schedule.getLessonName());
                content.put(TEACHER_SURNAME_S, schedule.getTeacherSurname());
                content.put(BUILDING_S, schedule.getBuilding());
                content.put(CABINET_S, schedule.getCabinet());
                content.put(LESSON_DATE_S, schedule.getLessonDate());
                content.put(NOTE_S, schedule.getNote());
                content.put(GROUP_ID_S, schedule.getGroup().getId());

                db.insert(TABLE_SCHEDULE, null, content);
            }
            db.close();
        }
    }

    public void clearScheduleByGroupId(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = GROUP_ID_S + "= ?";
        String [] selectionArgs = {String.valueOf(id)};
        db.delete(TABLE_SCHEDULE, selection, selectionArgs);
        db.close();
    }

    public List<Schedule> getListScheduleByGroupId(Long gId) {
        List<Schedule> schedules = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = GROUP_ID_S + " = ?";
        String [] selectionArgs = {String.valueOf(gId)};

        Cursor cursor = db.query(TABLE_SCHEDULE, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(KEY_ID_S);
            int lessonNumber = cursor.getColumnIndex(LESSON_NUMBER_S);
            int lessonName = cursor.getColumnIndex(LESSON_NAME_S);
            int lessonType = cursor.getColumnIndex(LESSON_TYPE_S);
            int teacherSurname = cursor.getColumnIndex(TEACHER_SURNAME_S);
            int building = cursor.getColumnIndex(BUILDING_S);
            int cabinet = cursor.getColumnIndex(CABINET_S);
            int lessonDate = cursor.getColumnIndex(LESSON_DATE_S);
            int note = cursor.getColumnIndex(NOTE_S);
            int groupId = cursor.getColumnIndex(GROUP_ID_S);

            do {
                Schedule schedule = new Schedule();
                schedule.setId(cursor.getLong(id));
                schedule.setLessonNumber(cursor.getInt(lessonNumber));
                schedule.setLessonName(cursor.getString(lessonName));
                schedule.setLessonType(cursor.getString(lessonType));
                schedule.setTeacherSurname(cursor.getString(teacherSurname));
                schedule.setBuilding(cursor.getString(building));
                schedule.setCabinet(cursor.getString(cabinet));
                schedule.setLessonDate(cursor.getLong(lessonDate));
                schedule.setNote(cursor.getString(note));
                schedule.setGroupId(cursor.getLong(groupId));

                schedules.add(schedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return schedules;
    }
}
