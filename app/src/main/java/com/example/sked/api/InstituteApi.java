package com.example.sked.api;

import com.example.sked.domain.Course;
import com.example.sked.domain.Department;
import com.example.sked.domain.Division;
import com.example.sked.domain.Faculty;
import com.example.sked.domain.Group;
import com.example.sked.domain.Institute;
import com.example.sked.domain.Schedule;
import com.example.sked.domain.Semester;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InstituteApi {

    @GET("/institute/get-institutions")
    public Call<List<Institute>> getInstitutes();

    @GET("/division/get-division-by-userId/{id}")
    public Call<List<Division>> getDivisions(@Path("id") String id);

    @GET("/semester/{id}")
    public Call<List<Semester>> getSemesters(@Path("id") String id);

    @GET("/faculty/{id}")
    public Call<List<Faculty>> getFaculties(@Path("id") String id);

    @GET("/course/{id}")
    public Call<List<Course>> getCourses(@Path("id") String id);

    @GET("/department/{id}")
    public Call<List<Department>> getDepartments(@Path("id") String id);

    @GET("/group/{id}")
    public Call<List<Group>> getGroups(@Path("id") String id);

    @GET("/schedule/{id}")
    public Call<List<Schedule>> getSchedule(@Path("id") String id);

    @GET("/group/get-version/{id}")
    public Call<Integer> getGroupVersion(@Path("id") String id);

}
