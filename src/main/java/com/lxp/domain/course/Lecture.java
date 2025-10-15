package com.lxp.domain.course;


public class Lecture {
    private long id;
    private String name;
    private int seconds; //이 강의가 몇 초짜리 인지 길이 저장하는 변수(초 단위)


    public Lecture(String name, int seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    /**
     * 이름을 변경하는 기능
     * @param newName
     */
    public void rename(String newName) {
        this.name = newName;
    }

    /**
     * 재생 시간을 변경하는 기능
     */
    public void changeDuration(int newSeconds) {
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

    public int getSeconds() {
        return seconds;
    }
}
