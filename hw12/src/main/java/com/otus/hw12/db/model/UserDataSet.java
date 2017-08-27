package com.otus.hw12.db.model;

import com.otus.hw.domain.DataSet;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "user")
@Data
public class UserDataSet implements DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String name;
    private int age;
    private String password;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }
}
