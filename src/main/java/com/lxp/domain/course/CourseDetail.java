package com.lxp.domain.course;

public class CourseDetail {
    private long id;
    private String content;
    private String description;
    private Course course;

    public CourseDetail(String content, String description) {
        this.content = content;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
