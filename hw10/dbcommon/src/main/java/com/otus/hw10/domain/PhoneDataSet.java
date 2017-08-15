package com.otus.hw10.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "phone")
public class PhoneDataSet implements DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String number;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    AdvancedUserDataSet user;

    public PhoneDataSet() {
        this("");
    }

    public PhoneDataSet(String phone) {
        this.number = phone;
    }

    @Transient
    public void addToUser(AdvancedUserDataSet user) {
        setUser(user);
        user.getPhones().add(this);
    }
}
