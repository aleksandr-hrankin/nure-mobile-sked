package com.example.sked.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("lessonNumber")
    @Expose
    private int lessonNumber;
    @SerializedName("lessonName")
    @Expose
    private String lessonName;
    @SerializedName("lessonType")
    @Expose
    private String lessonType;
    @SerializedName("teacherSurname")
    @Expose
    private String teacherSurname;
    @SerializedName("building")
    @Expose
    private String building;
    @SerializedName("cabinet")
    @Expose
    private String cabinet;
    @SerializedName("lessonDate")
    @Expose
    private Long lessonDate;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("group")
    @Expose
    private Group group;

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public String getTeacherSurname() {
        return teacherSurname;
    }

    public void setTeacherSurname(String teacherSurname) {
        this.teacherSurname = teacherSurname;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public Long getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(Long lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", lessonNumber=" + lessonNumber +
                ", lessonName='" + lessonName + '\'' +
                ", lessonType='" + lessonType + '\'' +
                ", teacherSurname='" + teacherSurname + '\'' +
                ", building='" + building + '\'' +
                ", cabinet='" + cabinet + '\'' +
                ", lessonDate=" + lessonDate +
                ", note='" + note + '\'' +
                ", group=" + group +
                '}';
    }
}
