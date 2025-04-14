package com.hospital.backend.entity;

public class Doctor {
    private int DR_ID;
    private String Dr_name;
    private String Mobile_no;
    private String Email_id;
    private String Gender;
    private Integer Age;
    private Integer Experience;
    private String Password;
    private Integer Sp_Id;

    // Default constructor
    public Doctor() {}

    // Getters and Setters
    public int getDR_ID() {
        return DR_ID;
    }

    public void setDR_ID(int DR_ID) {
        this.DR_ID = DR_ID;
    }

    public String getDr_name() {
        return Dr_name;
    }

    public void setDr_name(String Dr_name) {
        this.Dr_name = Dr_name;
    }

    public String getMobile_no() {
        return Mobile_no;
    }

    public void setMobile_no(String Mobile_no) {
        this.Mobile_no = Mobile_no;
    }

    public String getEmail_id() {
        return Email_id;
    }

    public void setEmail_id(String Email_id) {
        this.Email_id = Email_id;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer Age) {
        this.Age = Age;
    }

    public Integer getExperience() {
        return Experience;
    }

    public void setExperience(Integer Experience) {
        this.Experience = Experience;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public Integer getSp_Id() {
        return Sp_Id;
    }

    public void setSp_Id(Integer Sp_Id) {
        this.Sp_Id = Sp_Id;
    }
}