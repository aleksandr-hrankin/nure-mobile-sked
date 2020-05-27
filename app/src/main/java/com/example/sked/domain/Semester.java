package com.example.sked.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Semester {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("start")
    @Expose
    private Long start;
    @SerializedName("finish")
    @Expose
    private Long finish;
    @SerializedName("division")
    @Expose
    private Division division;

    public Semester() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getFinish() {
        return finish;
    }

    public void setFinish(Long finish) {
        this.finish = finish;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                ", division=" + division +
                '}';
    }
}
