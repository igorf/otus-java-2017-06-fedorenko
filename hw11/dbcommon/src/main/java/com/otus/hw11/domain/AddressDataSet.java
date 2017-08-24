package com.otus.hw11.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "address")
public class AddressDataSet implements DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String address;
    @OneToOne(fetch = FetchType.EAGER)
    private AdvancedUserDataSet user;

    @Transient
    public void addToUser(AdvancedUserDataSet user) {
        setUser(user);
        user.setAddress(this);
    }

    public AddressDataSet(String address) {
        this.address = address;
    }

    public AddressDataSet() {
        this("");
    }
}
