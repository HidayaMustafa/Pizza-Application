package com.example.project;

public class User {
    private String Email;
    private String mPhone;
    private String mFName;
    private String mLName;
    private String mGender;
    private String Password;
    private String ConfPassword;
    private byte[] profilePicture; // New field for profile picture
    private int isCustomer;

    public User() {
    }

    public User(String Email, String mPhone, String mFName, String mLName, String mGender, String Password, String ConfPassword, byte [] profilePicture, int isCustomer) {
        this.Email = Email;
        this.mPhone = mPhone;
        this.mFName = mFName;
        this.mLName = mLName;
        this.mGender = mGender;
        this.Password = Password;
        this.ConfPassword = ConfPassword;
        this.profilePicture = profilePicture;
        this.isCustomer = isCustomer ;

    }

    public String getEmail() {
        return Email;
    }

    public String getmFName() {
        return mFName;
    }

    public String getmLName() {
        return mLName;
    }

    public String getPassword() {
        return Password;
    }

    public String getConfPassword() {
        return ConfPassword;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmGender() {
        return mGender;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setmFName(String mFName) {
        this.mFName = mFName;
    }

    public void setmLName(String mLName) {
        this.mLName = mLName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setConfPassword(String confPassword) {
        ConfPassword = confPassword;
    }

    // New methods for profile picture
    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture( byte[]  profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(int isCustomer) {
        this.isCustomer = isCustomer;
    }
}
