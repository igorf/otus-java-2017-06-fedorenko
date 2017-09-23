package com.otus.hw15.db.model;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "country")
public class Country implements Serializable {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore @Id @Column(name = "id")    private long    id;
    @Column(name = "locale")                private String  locale;
    @JsonIgnore @Column(name = "version")   private int     version;
}
