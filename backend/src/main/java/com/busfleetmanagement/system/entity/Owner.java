package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "Owner")

public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OwnerID")
    private Integer ownerId;

    @Column(name = "Username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @Column(name = "ContactNo", length = 20)
    private String contactNo;

    public Owner(String username, String password, String name, String contactNo){
        this.username = username;
        this.password = name;
        this.name = name;
        this.contactNo = contactNo;
    }

    public Integer getOwnerId(){
        return ownerId;
    }

    public void setOwnerId(){
        this.ownerId = ownerId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
