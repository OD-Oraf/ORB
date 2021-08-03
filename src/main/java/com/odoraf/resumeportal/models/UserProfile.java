package com.odoraf.resumeportal.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String userName;
    private int theme;
    private String summary;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String designation;

    //One profile can have many jobs
    //When a profile delete, the jobs delete
    @OneToMany(cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JoinColumn(name = "job_id")
    List<Job> jobs = new ArrayList<>();


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesignation(String designation) {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
