package com.donmateo.tables;

import javax.persistence.*;

@Entity
@Table
public class HashTag {

    @Id
    @GeneratedValue
    private Integer id;


    private String text;

    public HashTag(){}

    public HashTag(String text) {
        this.setText(text);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}