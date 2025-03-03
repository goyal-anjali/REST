package com.adobe.orderapp.dto;


//import com.fasterxml.jackson.annotation.JsonView;

public class Person {

//    @JsonView(MessageView.Summary.class)
    private Long id;

//    @JsonView(MessageView.Summary.class)
    private String firstname;

//    @JsonView(MessageView.Summary.class)
    private String lastname;

    private String email;
    private String address;
    private String postalCode;
    private String city;
    private String country;

    public Person() {
    }

    public Person(Long id, String firstname, String lastname, String email, String address,
                  String postalCode, String city, String country) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}