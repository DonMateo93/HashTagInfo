package com.donmateo.tables;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table
public class HashTag_Info {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer id_hashtag;

    private Integer id_info;

    private String text;

    public HashTag_Info(){}

    public HashTag_Info(Integer id_hashtag, Integer id_info) {
        setId_hashtag(id_hashtag);
        setId_info(id_info);
    }

    public Integer getId_hashtag() {
        return id_hashtag;
    }

    public void setId_hashtag(Integer id_hashtag) {
        this.id_hashtag = id_hashtag;
    }

    public Integer getId_info() {
        return id_info;
    }

    public void setId_info(Integer id_info) {
        this.id_info = id_info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}