package com.lxp.domain.course;

public class Tag {
    private long id;
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public void rename(String newName) {
        this.name = newName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
