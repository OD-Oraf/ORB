package com.odoraf.resumebuilder.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Job {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private int id;
    private String company;
    private String occupationTitle;
    //get dates to show up in the profile-edit
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private boolean isCurrentJob;
    //Needed for primitive data types so hibernate knows how to process
    @ElementCollection(targetClass = String.class)
    private List<String> responsibilities = new ArrayList<>();

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public boolean isCurrentJob(){
        return isCurrentJob;
    }

    public void setCurrentJob(boolean currentJob) {
        isCurrentJob = currentJob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOccupationTitle() {
        return this.occupationTitle;
    }

    public void setOccupationTitle(String occupationTitle) {
        this.occupationTitle = occupationTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getFormattedStartDate(){
        return startDate.format(DateTimeFormatter.ofPattern("MMM yyyy"));
    }

    public String getFormattedEndDate(){
        return endDate.format(DateTimeFormatter.ofPattern("MMM yyyy"));
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", occupationTitle='" + occupationTitle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isCurrentJob=" + isCurrentJob +
                ", responsibilities=" + responsibilities +
                '}';
    }
}




