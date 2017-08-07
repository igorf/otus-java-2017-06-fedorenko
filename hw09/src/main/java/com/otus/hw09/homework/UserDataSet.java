package com.otus.hw09.homework;

import com.otus.hw09.yadbe.DataSet;
import lombok.Data;

import javax.persistence.Entity;

@Entity(name = "user")
@Data
public class UserDataSet extends DataSet {
    private String name;
    private int age;
}
