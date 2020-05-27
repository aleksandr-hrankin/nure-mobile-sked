package com.example.sked.domain;

public class MyGroup {

    private Long id;
    private String name;
    private String departmentName;
    private String courseName;
    private String facultyName;
    private String semesterName;
    private Long semesterStart;
    private Long semesterFinish;
    private String divisionName;
    private Long lastModified;

    public MyGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public Long getSemesterStart() {
        return semesterStart;
    }

    public void setSemesterStart(Long semesterStart) {
        this.semesterStart = semesterStart;
    }

    public Long getSemesterFinish() {
        return semesterFinish;
    }

    public void setSemesterFinish(Long semesterFinish) {
        this.semesterFinish = semesterFinish;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "MyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", facultyName='" + facultyName + '\'' +
                ", semesterName='" + semesterName + '\'' +
                ", semesterStart=" + semesterStart +
                ", semesterFinish=" + semesterFinish +
                ", divisionName='" + divisionName + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
