package com.example.sked.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Institute {


    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("userId")
    @Expose
    private Long userId;

    public Institute() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Institute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", site='" + site + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", user=" + user +
                ", userId=" + userId +
                '}';
    }
}
