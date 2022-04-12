package com.mehmetfatih.ready2help;

import com.google.gson.GsonBuilder;

public class Person {
    private String name;
    private String email;
    private String password;
    private String dateOfBirth;
    private String city;
    private String phoneNumber;
    private boolean isMale;
    private boolean isElder;

    public Person() {
    }

    public Person(String name, String email, String password, String dateOfBirth, String city, String phoneNumber, boolean isMale, boolean isElder) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.isMale = isMale;
        this.isElder = isElder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isElder() {
        return isElder;
    }

    public void setElder(boolean elder) {
        isElder = elder;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, Person.class);
    }
}
