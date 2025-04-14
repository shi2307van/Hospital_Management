package com.hospital.backend.entity;

public class Prescription {
    private int Pr_ID;
    private Integer Ap_Id;
    private int P_ID;        // Added patient ID field (not nullable)
    private String medicine;
    private String advice;
    private String remark;
    
    // Default constructor
    public Prescription() {}
    
    // Getters and Setters
    public int getPr_ID() {
        return Pr_ID;
    }
    
    public void setPr_ID(int Pr_ID) {
        this.Pr_ID = Pr_ID;
    }
    
    public Integer getAp_Id() {
        return Ap_Id;
    }
    
    public void setAp_Id(Integer Ap_Id) {
        this.Ap_Id = Ap_Id;
    }
    
    public int getP_ID() {
        return P_ID;
    }
    
    public void setP_ID(int P_ID) {
        this.P_ID = P_ID;
    }
    
    public String getMedicine() {
        return medicine;
    }
    
    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
    
    public String getAdvice() {
        return advice;
    }
    
    public void setAdvice(String advice) {
        this.advice = advice;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}