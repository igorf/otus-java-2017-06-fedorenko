package com.otus.hw11.domain;

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
}
