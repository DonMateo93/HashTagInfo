package com.donmateo.tables;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table
public class HashTag_HashTag {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer id1;
    private Integer id2;

    public HashTag_HashTag(){}

    public HashTag_HashTag(Integer id1, Integer id2) {

        setId1(id1);
        setId1(id2);
    }
    public Integer getId1() {
        return id1;
    }
    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}