package com.donmateo.tables;

import javax.persistence.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

@Entity
@Table
public class Info {

//    @Id
//    @GeneratedValue
    private Integer id;

    private String text;
    private String description;

    @Column(name="clipboard",columnDefinition="text")
    private String clipboard;

    public Info(){}

    public Info(String text, String description) {

        this.setText(text);
        this.setDescription(description);
    }

    public Info(String text, String description, String clipboard) {

        this.setText(text);
        this.setDescription(description);
        this.setClipboard(clipboard);
    }

    public Info(String text, String description, Integer id) {

        this.setText(text);
        this.setDescription(description);
        setId(id);
    }

    public Info(String text, String description, Integer id, String clipboard) {

        this.setText(text);
        this.setDescription(description);
        setClipboard(clipboard);
        setId(id);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        String output;
        output = "\nDESCRIPTION:\n"+ getDescription();
        output += "\n\n" + "CONTENT:\n" + getText();
        if(!clipboard.isEmpty()){
            output += "\n\n" + "CLIPBOARD:\n" + getClipboard();

            //copy to clipboard
            StringSelection selection = new StringSelection(getClipboard());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
        return output;
    }

    public String getClipboard() {
        return clipboard;
    }

    public void setClipboard(String clipboard) {
        this.clipboard = clipboard;
    }
}