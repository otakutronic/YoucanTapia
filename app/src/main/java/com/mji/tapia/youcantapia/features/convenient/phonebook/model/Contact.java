package com.mji.tapia.youcantapia.features.convenient.phonebook.model;

/**
 * Created by andy on 2018/05/16.
 */

public class Contact {

    private int id;
    private String name;
    private String telephoneNumber;

    public Contact() {

    }

    public Contact(int id, String name, String telephoneNumber) {
        this.id = id;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String toString() {
        return "ContactInformation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", telephoneNumber=" + telephoneNumber +
                '}';
    }
}

