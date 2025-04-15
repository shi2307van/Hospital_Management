package com.hospital.backend.entity;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
    private int Ap_ID;
    private Integer P_ID;
    private Integer DR_ID;
    private String Descript;
    private Integer cancel_confirm;
    private Date appointment_date;
    private Time appointment_time;
    private String status;
    
    // Default constructor
    public Appointment() {
        this.cancel_confirm = 0;
        this.status = "PENDING";
    }
    
    // Getters and Setters for existing fields
    public int getAp_ID() {
        return Ap_ID;
    }
    
    public void setAp_ID(int Ap_ID) {
        this.Ap_ID = Ap_ID;
    }
    
    public Integer getP_ID() {
        return P_ID;
    }
    
    public void setP_ID(Integer P_ID) {
        this.P_ID = P_ID;
    }
    
    public Integer getDR_ID() {
        return DR_ID;
    }
    
    public void setDR_ID(Integer DR_ID) {
        this.DR_ID = DR_ID;
    }
    
    public String getDescript() {
        return Descript;
    }
    
    public void setDescript(String Descript) {
        this.Descript = Descript;
    }
    
    public Integer getCancel_confirm() {
        return cancel_confirm;
    }
    
    public void setCancel_confirm(Integer cancel_confirm) {
        this.cancel_confirm = cancel_confirm;
    }
    
    // New getters and setters
    public Date getAppointment_date() {
        return appointment_date;
    }
    
    public void setAppointment_date(Date appointment_date) {
        this.appointment_date = appointment_date;
    }
    
    public Time getAppointment_time() {
        return appointment_time;
    }
    
    public void setAppointment_time(Time appointment_time) {
        this.appointment_time = appointment_time;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}