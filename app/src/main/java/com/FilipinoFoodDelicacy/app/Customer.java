package com.FilipinoFoodDelicacy.app;

public class Customer {


    private String City,ConfirmPassword,EmailID,FirstName,LastName,Mobileno,Password,Region,LocalAddress;

    public Customer() {


    }

    public Customer(String city, String confirmPassword, String emailID, String firstName, String lastName, String mobileno, String password, String region, String localAddress) {

        City = city;
        ConfirmPassword = confirmPassword;
        EmailID = emailID;
        FirstName = firstName;
        LastName = lastName;
        Mobileno = mobileno;
        Password = password;
        Region = region;
        LocalAddress = localAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMobileno() {
        return Mobileno;
    }

    public void setMobileno(String mobileno) {
        Mobileno = mobileno;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getLocalAddress() {
        return LocalAddress;
    }

    public void setLocalAddress(String localAddress) {
        LocalAddress = localAddress;
    }
}
