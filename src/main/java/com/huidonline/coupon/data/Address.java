package com.huidonline.coupon.data;

/**
 * Created by raber on 22/04/15.
 */
public class Address {

    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;

    @Override
    public String toString() {
        return getStreet() + " " + getHouseNumber() + " " + getPostalCode() + " " + getCity() + " " +
                " " + getCountry() + '\'';
    }

    public String getHouseNumber() {

        return houseNumber == null ? "" : houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode == null ? "" : postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country == null ? "" : country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getStreet() {
        return street == null ? "" : street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
