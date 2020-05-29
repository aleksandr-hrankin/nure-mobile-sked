package com.example.sked;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sked.adapter.CourseAdapter;
import com.example.sked.adapter.DepartmentAdapter;
import com.example.sked.adapter.DivisionAdapter;
import com.example.sked.adapter.FacultyAdapter;
import com.example.sked.adapter.GroupAdapter;
import com.example.sked.adapter.InstituteAdapter;
import com.example.sked.adapter.MyGroupAdapter;
import com.example.sked.adapter.SemesterAdapter;
import com.example.sked.database.Database;
import com.example.sked.domain.Course;
import com.example.sked.domain.Department;
import com.example.sked.domain.Division;
import com.example.sked.domain.Faculty;
import com.example.sked.domain.Group;
import com.example.sked.domain.Institute;
import com.example.sked.domain.MyGroup;
import com.example.sked.domain.Schedule;
import com.example.sked.domain.Semester;
import com.example.sked.service.NetworkService;
import com.example.sked.service.OnSwipeTouchListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    private LinkedList<String> historyLayout;
    private List<Institute> institutesFromServer;
    private List<Schedule> schedules;

    //text view
    private TextView tvGroupName;
    private TextView tvEmptyListMyInstitutes;
    private TextView tvEmptyListMyGroups;
    //edit text
    private EditText inputSearchInstitute;
    //button
    private ImageButton btnUpdateSchedule;
    private ImageButton btnAddInstitute;
    private Button btnGetInstitutesFromSever;
    //layout
    private RelativeLayout layoutInstitute;
    private LinearLayout layout_footer;
    private LinearLayout layoutMyInstitutes;
    private LinearLayout layoutDivisions;
    private LinearLayout layoutSemesters;
    private LinearLayout layoutFaculties;
    private LinearLayout layoutCourses;
    private LinearLayout layoutDepartments;
    private LinearLayout layoutGroups;
    private LinearLayout layoutMyGroups;
    private RelativeLayout layoutLessonInfo;
    private LinearLayout layoutSearchInstitute;
    //view
    private ListView listInstitutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyLayout = new LinkedList<>();

        //text view
        tvGroupName = findViewById(R.id.tv_group_name);
        tvEmptyListMyInstitutes = findViewById(R.id.tv_empty_list_my_institutes);
        tvEmptyListMyGroups = findViewById(R.id.tv_empty_list_my_groups);
        //edit text
        inputSearchInstitute = findViewById(R.id.searchInstitute);
        //button
        btnUpdateSchedule = findViewById(R.id.btn_update_schedule);
        btnAddInstitute = findViewById(R.id.btn_add_institute);
        btnGetInstitutesFromSever = findViewById(R.id.btn_get_institute_from_server);
        //layout
        layout_footer = findViewById(R.id.layout_footer);
        layoutInstitute = findViewById(R.id.layout_institute);
        layoutMyInstitutes = findViewById(R.id.layout_my_institutes);
        layoutDivisions = findViewById(R.id.layout_divisions);
        layoutSemesters = findViewById(R.id.layout_semesters);
        layoutFaculties = findViewById(R.id.layout_faculties);
        layoutCourses = findViewById(R.id.layout_courses);
        layoutDepartments = findViewById(R.id.layout_departments);
        layoutGroups = findViewById(R.id.layout_groups);
        layoutMyGroups = findViewById(R.id.layout_my_groups);
        layoutLessonInfo = findViewById(R.id.layout_lesson_info);
        layoutSearchInstitute = findViewById(R.id.layout_search_institute);
        //view
        listInstitutes = findViewById(R.id.listInstitutes);

        onClick();
        onSwipeFooter();
        onSwipeLessonInfo();
        onSearchInstituteChanged();

//        getInstitutesFromServer();

        outputMyInstitutes();
        outputTitleGroupName();
        outputSchedule();

    }

    // get from server ######################################################################################################################################################################################
    private void getInstitutesFromServer() {
        if (!isNetworkAvailable()) {
            Toast.makeText(MainActivity.this, "Увімкніть інтернет", Toast.LENGTH_LONG).show();
            return;
        }

        btnUpdateSchedule.setVisibility(View.GONE);
        showGifLoad(R.id.gif_load_schedule);

        NetworkService.getInstance()
                .getInstitutionApi()
                .getInstitutes()
                .enqueue(new Callback<List<Institute>>() {
                    @Override
                    public void onResponse(Call<List<Institute>> call, Response<List<Institute>> response) {
                        if (response.isSuccessful()) {
                            institutesFromServer = response.body();

                            hideGifLoad(R.id.gif_load_schedule);
                            btnUpdateSchedule.setVisibility(View.VISIBLE);
                            btnGetInstitutesFromSever.setVisibility(View.GONE);
                            layoutSearchInstitute.setVisibility(View.VISIBLE);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Institute>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void clearInputSearchInstitute() {
        inputSearchInstitute.setText("");
    }

    //click and swipe #######################################################################################################################################################################################
    public void onClick() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_add_institute:
                        showLayoutInstitute();
                        break;
                    case R.id.btn_clear_search_institute:
                        clearInputSearchInstitute();
                        break;
                    case R.id.btn_my_groups:
                        outputMyGroups();
                        break;
                    case R.id.btn_get_institute_from_server:
                        getInstitutesFromServer();
                        break;
                    case R.id.btn_update_schedule:
                        updateSchedule();
                        break;
                }
            }
        };

        btnAddInstitute.setOnClickListener(onClickListener);
        btnGetInstitutesFromSever.setOnClickListener(onClickListener);
        btnUpdateSchedule.setOnClickListener(onClickListener);

        ImageButton btnClearSearchInstitute = findViewById(R.id.btn_clear_search_institute);
        btnClearSearchInstitute.setOnClickListener(onClickListener);

        ImageButton btnMyGroups = findViewById(R.id.btn_my_groups);
        btnMyGroups.setOnClickListener(onClickListener);



    }

    private void updateSchedule() {
        MyGroup group = Database.getInstance(getApplicationContext()).getFavoritesGroup();

        if (group.getId() == null) {
            return;
        }

        if (isNetworkAvailable()) {
            checkUpdateSchedule();
        } else {
            Toast.makeText(MainActivity.this, "Увімкніть інтернет", Toast.LENGTH_LONG).show();
        }
    }

    public void onSwipeFooter() {
        View footer = findViewById(R.id.layout_footer);
        footer.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeUp() {
                super.onSwipeUp();
                if (historyLayout.size() == 0) {
                    historyLayout.addLast("main");
                    setVisibility("institute");
                }
//                showLayoutInstitute();
            }

            @Override
            public void onSwipeDown() {
                super.onSwipeUp();
                if (historyLayout.size() > 0) {
                    historyLayout.clear();
                    setVisibility("main");
                }
//                hideLayoutInstitute();
            }
        });
    }

    public void onSwipeLessonInfo() {
        layoutLessonInfo.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeDown() {
                super.onSwipeUp();
                    historyLayout.clear();
                    setVisibility("main");

            }
        });
    }

    // watch ################################################################################################################################################################################################
    public void onSearchInstituteChanged() {
        inputSearchInstitute.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String instituteName = inputSearchInstitute.getText().toString().toUpperCase();
                fillInstitutes(instituteName);
            }
        });
    }

    private void outputMyGroups() {
        if(historyLayout.isEmpty()) {
            btnAddInstitute.setBackgroundResource(R.drawable.btn_hide_footer);
            historyLayout.addLast("main");
            setVisibility("my-groups");
        } else {
            setVisibility("main");
            setVisibility("my-groups");
        }

        final List<MyGroup> myGroups = Database.getInstance(this).getAllGroups();

        if (myGroups.isEmpty()) {
            tvEmptyListMyGroups.setVisibility(View.VISIBLE);
            return;
        } else {
            tvEmptyListMyGroups.setVisibility(View.GONE);
        }

        final RecyclerView listMyGroups = findViewById(R.id.list_my_groups);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listMyGroups.setLayoutManager(layoutManager);

        final MyGroupAdapter adapter = new MyGroupAdapter(this);
        adapter.setItems(myGroups);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listMyGroups.indexOfChild(v);
                Database.getInstance(getApplicationContext()).setFavoritesGroup(myGroups.get(position).getId());

                if (isNetworkAvailable()) {
                    checkUpdateSchedule();
                } else {
                    Toast.makeText(MainActivity.this, "Увімкніть інтернет", Toast.LENGTH_LONG).show();
                }

                outputTitleGroupName();
                outputSchedule();
            }
        });
        listMyGroups.setAdapter(adapter);
    }

    private void outputTitleGroupName() {
        MyGroup group = Database.getInstance(getApplicationContext()).getFavoritesGroup();
        TextView tvGroupName = findViewById(R.id.tv_group_name);
        tvGroupName.setText(group.getName());
    }

    private void checkUpdateSchedule() {
        btnUpdateSchedule.setVisibility(View.GONE);
        showGifLoad(R.id.gif_load_schedule);
        final MyGroup group = Database.getInstance(getApplicationContext()).getFavoritesGroup();

        NetworkService.getInstance()
                .getInstitutionApi()
                .getGroupVersion(String.valueOf(group.getId()))
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            int version = Integer.parseInt(String.valueOf(response.body()));
                            if (group.getVersion() < version) {
                                loadScheduleFromServer(version);
                            } else {
                                Toast.makeText(MainActivity.this, "Встановлена остання версія розкладу", Toast.LENGTH_LONG).show();
                                hideGifLoad(R.id.gif_load_schedule);
                                btnUpdateSchedule.setVisibility(View.VISIBLE);
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void loadScheduleFromServer(final int version) {
        final MyGroup group = Database.getInstance(getApplicationContext()).getFavoritesGroup();
        NetworkService.getInstance()
                .getInstitutionApi()
                .getSchedule(String.valueOf(group.getId()))
                .enqueue(new Callback<List<Schedule>>() {
                    @Override
                    public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                        if (response.isSuccessful()) {
                            List<Schedule> schedules = response.body();
                            Database.getInstance(getApplicationContext()).clearScheduleByGroupId(group.getId());
                            Database.getInstance(getApplicationContext()).addListSchedule(schedules);
                            Database.getInstance(getApplicationContext()).setGroupVersion(group.getId(), version);
                            Toast.makeText(MainActivity.this, "Розклад обновлено", Toast.LENGTH_LONG).show();
                            hideGifLoad(R.id.gif_load_schedule);
                            btnUpdateSchedule.setVisibility(View.VISIBLE);
                            outputSchedule();
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Schedule>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private boolean outputSchedule() {
        if (Database.getInstance(this).getFavoritesGroup().getId() == null) {
            return false;
        }

        LinearLayout layoutSchedule = findViewById(R.id.layout_schedule);
        layoutSchedule.removeAllViews();

        MyGroup group = Database.getInstance(this).getFavoritesGroup();
        schedules = Database.getInstance(this).getListScheduleByGroupId(group.getId());

        Long semesterStart = group.getSemesterStart();
        Long semesterFinish = group.getSemesterFinish();

        Calendar calendarStart = new GregorianCalendar();
        calendarStart.setTimeInMillis(semesterStart);

        Calendar calendarFinish = new GregorianCalendar();
        calendarFinish.setTimeInMillis(semesterFinish);

        for (int year = calendarStart.get(Calendar.YEAR); year <= calendarFinish.get(Calendar.YEAR); year++) {
            for (int month = calendarStart.get(Calendar.MONTH); month <= calendarFinish.get(Calendar.MONTH); month++) {
                Calendar calendar = new GregorianCalendar(year, month, 1);
                for (int day = calendarStart.get(Calendar.DAY_OF_MONTH); day <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); day++) {
                    String date = ((month + 1) <= 9 ? "0" + (month + 1) : (month + 1)) + "." + (day <= 9 ? "0" + day : day);

                    LayoutInflater inflater = getLayoutInflater();
                    LinearLayout layoutDay = (LinearLayout) inflater.inflate(R.layout.layout_day, layoutSchedule, false);

                    TextView tvDayDate = layoutDay.findViewById(R.id.tv_day_date);
                    tvDayDate.setText(date);

                    layoutDay.removeAllViews();

                    layoutDay.addView(tvDayDate);

                    List<Schedule> formatSchedule = getSchedulesByDate(new GregorianCalendar(year, month, day));
                    Collections.sort(formatSchedule);

                    for (int i = 1; i <= 5; i++) {
                        int existence = 0;
                        for (Schedule schedule : formatSchedule) {
                            if (schedule.getLessonNumber() == i) {
                                existence = i;
                                break;
                            } else {
                                existence = 0;
                            }
                        }

                        if (existence != 0) {
                            for (final Schedule schedule : formatSchedule) {
                                if (schedule.getLessonNumber() == existence) {
                                    LinearLayout layoutLesson = (LinearLayout) inflater.inflate(R.layout.layout_lesson, layoutDay, false);

                                    TextView tvLessonName = layoutLesson.findViewById(R.id.tv_lesson_name);
                                    TextView tvLessonType = layoutLesson.findViewById(R.id.tv_lesson_type);
                                    TextView tvCabinet = layoutLesson.findViewById(R.id.tv_cabinet);

                                    tvLessonName.setText(schedule.getLessonName());
                                    tvLessonType.setText(schedule.getLessonType());
                                    tvCabinet.setText(schedule.getCabinet());

                                    layoutLesson.removeAllViews();

                                    layoutLesson.addView(tvLessonName);
                                    layoutLesson.addView(tvLessonType);
                                    layoutLesson.addView(tvCabinet);

                                    layoutLesson.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            showLayoutLessonInfo(schedule);
                                        }
                                    });

                                    int color = getColorForLayoutLesson(schedule.getLessonType());
                                    GradientDrawable drawable = (GradientDrawable) layoutLesson.getBackground();
                                    drawable.setColor(color);
                                    layoutDay.addView(layoutLesson);
                                }
                            }

                        } else {
                            LinearLayout layoutLesson = (LinearLayout) inflater.inflate(R.layout.layout_empty_lesson, layoutDay, false);
                            layoutDay.addView(layoutLesson);
                        }

                    }
                    layoutSchedule.addView(layoutDay);
                }
            }
        }
        return true;
    }

    private void showLayoutLessonInfo(Schedule schedule) {
        historyLayout.addLast("main");
        setVisibility("lesson_info");

        TextView tvLessonName = findViewById(R.id.tv_info_lessonName);
        TextView tvLessonType = findViewById(R.id.tv_info_lessonType);
        TextView tvTeacherSurname = findViewById(R.id.tv_info_teacherSurname);
        TextView tvCabinet = findViewById(R.id.tv_info_cabinet);
        TextView tvNote = findViewById(R.id.tv_info_note);
        TextView tvDate = findViewById(R.id.tv_info_lessonDate);

        tvLessonName.setText(schedule.getLessonName());
        tvLessonType.setText(schedule.getLessonType());
        tvTeacherSurname.setText(schedule.getTeacherSurname());
        tvCabinet.setText(schedule.getCabinet());
        tvNote.setText(schedule.getNote());

        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(schedule.getLessonDate());
        tvDate.setText(format.format(date.getTime()));
    }

    private List<Schedule> getSchedulesByDate(Calendar date) {
        List<Schedule> resultSchedule = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String dateFormat = format.format(date.getTime());
        Calendar lessonDate = new GregorianCalendar();

        for (Schedule schedule : schedules) {
            lessonDate.setTimeInMillis(schedule.getLessonDate());
            String lessonDateFormat = format.format(lessonDate.getTime());
            if (lessonDateFormat.equals(dateFormat)) {
                resultSchedule.add(schedule);
            }
        }
        return resultSchedule;
    }

    private void fillInstitutes(String instituteNameEntered) {
        if (instituteNameEntered.isEmpty()) {
            listInstitutes.setVisibility(View.GONE);
            layoutMyInstitutes.setVisibility(View.VISIBLE);
            outputInstitutes(new String[0]);
            return;
        }
        if (institutesFromServer == null) {
            Toast.makeText(MainActivity.this, "Обновите.", Toast.LENGTH_LONG).show();
            return;
        }

        List<Institute> institutes = new ArrayList<>();
        for (Institute institute : institutesFromServer) {
            String instituteName = institute.getName().toUpperCase();

            if (instituteName.contains(instituteNameEntered)) {
                institutes.add(institute);
            }
        }

        String[] instituteNames = new String[institutes.size()];

        int i = 0;
        for (Institute institute : institutes) {
            instituteNames[i] = institute.getName();
            i++;
        }

        listInstitutes.setVisibility(View.VISIBLE);
        layoutMyInstitutes.setVisibility(View.GONE);
        outputInstitutes(instituteNames);
    }

    private void outputInstitutes(final String[] institutes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, institutes);
        listInstitutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String instituteName = institutes[position];
                if (saveInstitute(instituteName)) {
                    clearInputSearchInstitute();
                    listInstitutes.setVisibility(View.GONE);
                    layoutMyInstitutes.setVisibility(View.VISIBLE);
                    outputMyInstitutes();
                    Toast.makeText(MainActivity.this, "Інститут збережено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ви вже маєте цей інститут", Toast.LENGTH_LONG).show();
                }
            }
        });
        listInstitutes.setAdapter(adapter);
    }

    // output ################################################################################################################################################################################################

    private void outputMyInstitutes() {
        final List<Institute> myInstitutesFromDb = Database.getInstance(this).getAllInstituteNames();
        if (myInstitutesFromDb.isEmpty()) {
            tvEmptyListMyInstitutes.setVisibility(View.VISIBLE);
            return;
        } else {
            tvEmptyListMyInstitutes.setVisibility(View.GONE);
        }

        final RecyclerView listMyInstitutes = findViewById(R.id.list_my_institutes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listMyInstitutes.setLayoutManager(layoutManager);



        final InstituteAdapter adapter = new InstituteAdapter(this);
        adapter.setItems(myInstitutesFromDb);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listMyInstitutes.indexOfChild(v);
                String userId = String.valueOf(myInstitutesFromDb.get(position).getUserId());
                historyLayout.addLast("institute");
                setVisibility("divisions");

                loadDivisionFromServer(userId);
            }
        });
        listMyInstitutes.setAdapter(adapter);
    }

    private void loadDivisionFromServer(String id) {
        NetworkService.getInstance()
                .getInstitutionApi()
                .getDivisions(id)
                .enqueue(new Callback<List<Division>>() {
                    @Override
                    public void onResponse(Call<List<Division>> call, Response<List<Division>> response) {
                        if (response.isSuccessful()) {
                            outputDivisions(response.body());
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Division>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void outputDivisions(final List<Division> divisions) {
        final RecyclerView listDivisions = findViewById(R.id.list_divisions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDivisions.setLayoutManager(layoutManager);

        final DivisionAdapter adapter = new DivisionAdapter();
        adapter.setItems(divisions);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listDivisions.indexOfChild(v);
                String divisionId = String.valueOf(divisions.get(position).getId());
                historyLayout.addLast("divisions");
                setVisibility("semesters");
                loadSemesterFromServer(divisionId);
            }
        });
        listDivisions.setAdapter(adapter);
    }

    private void loadSemesterFromServer(final String id) {
        NetworkService.getInstance()
                .getInstitutionApi()
                .getSemesters(id)
                .enqueue(new Callback<List<Semester>>() {
                    @Override
                    public void onResponse(Call<List<Semester>> call, Response<List<Semester>> response) {
                        if (response.isSuccessful()) {
                            outputSemesters(response.body());
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Semester>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void outputSemesters(final List<Semester> semesters) {
        final RecyclerView listSemesters = findViewById(R.id.list_semesters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        listSemesters.setLayoutManager(layoutManager);

        final SemesterAdapter adapter = new SemesterAdapter();
        adapter.setItems(semesters);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listSemesters.indexOfChild(v);
                String semesterId = String.valueOf(semesters.get(position).getId());
                historyLayout.addLast("semesters");
                setVisibility("faculties");
                loadFacultyFromServer(semesterId);

            }
        });
        listSemesters.setAdapter(adapter);
    }

    private void loadFacultyFromServer(String id) {
        NetworkService.getInstance()
                .getInstitutionApi()
                .getFaculties(id)
                .enqueue(new Callback<List<Faculty>>() {
                    @Override
                    public void onResponse(Call<List<Faculty>> call, Response<List<Faculty>> response) {
                        if (response.isSuccessful()) {
                            outputFaculties(response.body());
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Faculty>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void outputFaculties(final List<Faculty> faculties) {
        final RecyclerView listSemesters = findViewById(R.id.list_faculties);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        listSemesters.setLayoutManager(layoutManager);

        final FacultyAdapter adapter = new FacultyAdapter();
        adapter.setItems(faculties);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listSemesters.indexOfChild(v);
                String facultyId = String.valueOf(faculties.get(position).getId());
                historyLayout.addLast("faculties");
                setVisibility("courses");
                loadCoursesFromServer(facultyId);

            }
        });
        listSemesters.setAdapter(adapter);
    }

    private void loadCoursesFromServer(String id) {
        NetworkService.getInstance()
                .getInstitutionApi()
                .getCourses(id)
                .enqueue(new Callback<List<Course>>() {
                    @Override
                    public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                        if (response.isSuccessful()) {
                            outputCourses(response.body());
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Course>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void outputCourses(final List<Course> courses) {
        final RecyclerView listSemesters = findViewById(R.id.list_courses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        listSemesters.setLayoutManager(layoutManager);

        final CourseAdapter adapter = new CourseAdapter();
        adapter.setItems(courses);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listSemesters.indexOfChild(v);
                String courseId = String.valueOf(courses.get(position).getId());
                historyLayout.addLast("courses");
                setVisibility("departments");
                loadDepartmentFromServer(courseId);

            }
        });
        listSemesters.setAdapter(adapter);
    }

    private void loadDepartmentFromServer(String id) {
        NetworkService.getInstance()
                .getInstitutionApi()
                .getDepartments(id)
                .enqueue(new Callback<List<Department>>() {
                    @Override
                    public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                        if (response.isSuccessful()) {
                            outputDepartments(response.body());
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Department>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void outputDepartments(final List<Department> departments) {
        final RecyclerView listSemesters = findViewById(R.id.list_departments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        listSemesters.setLayoutManager(layoutManager);

        final DepartmentAdapter adapter = new DepartmentAdapter();
        adapter.setItems(departments);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listSemesters.indexOfChild(v);
                String departmentId = String.valueOf(departments.get(position).getId());
                historyLayout.addLast("departments");
                setVisibility("groups");
                loadGroupsFromServer(departmentId);

            }
        });
        listSemesters.setAdapter(adapter);
    }

    private void loadGroupsFromServer(String id) {
        NetworkService.getInstance()
                .getInstitutionApi()
                .getGroups(id)
                .enqueue(new Callback<List<Group>>() {
                    @Override
                    public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                        if (response.isSuccessful()) {
                            outputGroups(response.body());
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Group>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void outputGroups(final List<Group> groups) {
        final RecyclerView listGroups = findViewById(R.id.list_groups);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        listGroups.setLayoutManager(layoutManager);

        final GroupAdapter adapter = new GroupAdapter();
        adapter.setItems(groups);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listGroups.indexOfChild(v);
                if (saveGroup(groups.get(position))) {
                    outputMyGroups();
                    Toast.makeText(getApplicationContext(), "Група збережена", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Група вже збережена", Toast.LENGTH_SHORT).show();
                }
            }
        });
        listGroups.setAdapter(adapter);
    }

    private boolean saveInstitute(String name) {
        List<Institute> myInstitutes = Database.getInstance(this).getAllInstituteNames();

        for (Institute institute : myInstitutes) {
            if (institute.getName().equals(name)) {
                return false;
            }
        }
        for (Institute institute : institutesFromServer) {
            if (institute.getName().equals(name)) {
                Database.getInstance(this).addInstitute(institute);
                return true;
            }
        }
        return false;
    }

    private boolean saveGroup(Group group) {
        List<MyGroup> myGroups = Database.getInstance(this).getAllGroups();

        for (MyGroup myGroup : myGroups) {
            if (myGroup.getName().equals(group.getName())) {
                return false;
            }
        }

        group.setVersion(group.getVersion() - 1);
        Database.getInstance(this).addGroup(group);
        return true;
    }

    // show and hide ########################################################################################################################################################################################
    private void showLayoutInstitute() {
        if(historyLayout.isEmpty()) {
            btnAddInstitute.setBackgroundResource(R.drawable.btn_hide_footer);
            historyLayout.addLast("main");
            setVisibility("institute");
        } else {
            historyLayout.clear();
            setVisibility("main");
            btnAddInstitute.setBackgroundResource(R.drawable.btn_add);
        }
//        if (layoutInstitute.getVisibility() == View.GONE) {
//            layoutInstitute.setVisibility(View.VISIBLE);
//            final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_institute);
//            footer.startAnimation(show);
//        }
    }

    private void hideLayoutInstitute() {

        if (layoutInstitute.getVisibility() == View.VISIBLE) {
            final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide_institute);
            hide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    layoutInstitute.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            layout_footer.startAnimation(hide);
        }
    }

    private void showGifLoad(int id) {
        ImageView image = findViewById(id);
        image.setVisibility(View.VISIBLE);
    }

    private void hideGifLoad(int id) {
        ImageView image = findViewById(id);
        image.setVisibility(View.GONE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    // close app ############################################################################################################################################################################################
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (historyLayout.size() > 0) {
                setVisibility(historyLayout.getLast());

                if (historyLayout.size() == 1) {
                    btnAddInstitute.setBackgroundResource(R.drawable.btn_add);
                }

                historyLayout.removeLast();
                return false;
            }

            closeApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void closeApp() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Натисніть ще раз, щоб вийти", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    private void setVisibilityGoneAll() {
        layoutInstitute.setVisibility(View.GONE);
        layoutDivisions.setVisibility(View.GONE);
        layoutSemesters.setVisibility(View.GONE);
        layoutFaculties.setVisibility(View.GONE);
        layoutCourses.setVisibility(View.GONE);
        layoutDepartments.setVisibility(View.GONE);
        layoutGroups.setVisibility(View.GONE);
        layoutMyGroups.setVisibility(View.GONE);
        layoutLessonInfo.setVisibility(View.GONE);
    }

    private void setVisibility(String name) {
        setVisibilityGoneAll();

        switch (name) {
            case "institute":
                layoutInstitute.setVisibility(View.VISIBLE);
                break;
            case "divisions":
                layoutDivisions.setVisibility(View.VISIBLE);
                break;
            case "semesters":
                layoutSemesters.setVisibility(View.VISIBLE);
                break;
            case "faculties":
                layoutFaculties.setVisibility(View.VISIBLE);
                break;
            case "courses":
                layoutCourses.setVisibility(View.VISIBLE);
                break;
            case "departments":
                layoutDepartments.setVisibility(View.VISIBLE);
                break;
            case "groups":
                layoutGroups.setVisibility(View.VISIBLE);
                break;
            case "my-groups":
                layoutMyGroups.setVisibility(View.VISIBLE);
                break;
            case "lesson_info":
                layoutLessonInfo.setVisibility(View.VISIBLE);
                break;
            case "main":
                setVisibilityGoneAll();
                break;
        }
    }

    private int getColorForLayoutLesson(String typeLesson) {
        switch (typeLesson) {
            case "Лекція":
                return getResources().getColor(R.color.lecture);
            case "Практика":
                return getResources().getColor(R.color.practice);
            case "Групове заняття":
                return getResources().getColor(R.color.group_lesson);
            case "Семінар":
                return getResources().getColor(R.color.seminar);
            case "Контрольна":
                return getResources().getColor(R.color.control_work);
            default:
                return getResources().getColor(R.color.default_lesson_color);
        }
    }
}


//    public void onSwipe() {
//        View layout = findViewById(R.id.layout_wrapper_institute);
//        layout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
//
//            @Override
//            public void onClick() {
//                super.onClick();
//                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onDoubleClick() {
//                super.onDoubleClick();
//                Toast.makeText(MainActivity.this, "double click", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onLongClick() {
//                super.onLongClick();
//                Toast.makeText(MainActivity.this, "long click", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onSwipeUp() {
//                super.onSwipeUp();
//                Toast.makeText(MainActivity.this, "up", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onSwipeDown() {
//                super.onSwipeDown();
//                Toast.makeText(MainActivity.this, "down", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onSwipeLeft() {
//                super.onSwipeLeft();
//                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onSwipeRight() {
//                super.onSwipeRight();
//                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }



