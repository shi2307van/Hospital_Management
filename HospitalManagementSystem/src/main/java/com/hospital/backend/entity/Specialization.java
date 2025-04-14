package com.hospital.backend.entity;

public class Specialization {
    private int Sp_Id;
    private String Sp_Name;
  
    
    public String getSp_Name() {
		return Sp_Name;
	}

	public void setSp_Name(String sp_Name) {
		Sp_Name = sp_Name;
	}

	// Default constructor
    public Specialization() {}
    
    // Getters and Setters
    public int getSp_Id() {
        return Sp_Id;
    }
    
    public void setSp_Id(int Sp_Id) {
        this.Sp_Id = Sp_Id;
    }
    
  
  
}