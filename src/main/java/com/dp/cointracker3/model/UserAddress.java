package com.dp.cointracker3.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
public class UserAddress {

    @Id
    @Column(name = "address_id")
    String addressId;
    @Column(name = "user_id")
    String user;
    @Column(name = "nickname")
    String nickName;
    @Column(name = "address")
    String address;


    public UserAddress(String addressId, String user, String nickName, String address) {
        this.addressId = addressId;
        this.user = user;
        this.nickName = nickName;
        this.address = address;
    }

    public UserAddress() {

    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
