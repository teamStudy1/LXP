package com.lxp.domain.course;


public class Lecture {
    private long id;
    private String name;
    private long seconds;


    public Lecture() {
    }

    public Lecture(String name, long seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    public void changeDuration(long newSeconds) {
        this.seconds = newSeconds;
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

    public long getSeconds() {
        return seconds;
    }
}
