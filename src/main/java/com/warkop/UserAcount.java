package com.warkop;

public class UserAcount {

    private static UserAcount currentUser; // Static field to store the current user instance

    private String id;
    private String role;
    private String namaLengkap;
    private String email;
    private String password;
    
    public UserAcount() {}

    public static UserAcount getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserAcount user) {
        currentUser = user;
    }

    public void setUserAccount(String id, String role, String namaLengkap, String email, String password) {
        this.id = id;
        this.role = role;
        this.namaLengkap = namaLengkap;
        this.email = email;
        this.password = password;
        setCurrentUser(this);
    }

    public void removeUserAccount() {
        currentUser = null;
        this.id = null;
        this.role = null;
        this.namaLengkap = null;
        this.email = null;
        this.password = null;
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

    public String getPassword() {
        return password;
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

    public void setPassword(String password){
        this.password = password;
    }
 
}

