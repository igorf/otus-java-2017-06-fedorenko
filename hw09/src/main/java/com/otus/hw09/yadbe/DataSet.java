package com.otus.hw09.yadbe;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
abstract public class DataSet {
    @Id
    private long id;
}
