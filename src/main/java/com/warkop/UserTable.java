package com.warkop;

public class UserTable {
    private String id;
    private String role;
    private String namaLengkap;
    private String email;
    
    public UserTable(String id, String role, String namaLengkap, String email) {
        this.id = id;
        this.role = role;
        this.namaLengkap = namaLengkap;
        this.email = email;
    }

    
    public String getUserID() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setUserID(String id){
        this.id = id;
    }

    public void setRole(String role){
        this.role = role;
    }

    public void setNamaLengkap(String namaLengkap){
        this.namaLengkap = namaLengkap;
    }

    public void setEmail(String email){
        this.email = email;
    }

    
}


