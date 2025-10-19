package com.lxp.user.domain.model;

import java.sql.Timestamp;

public class UserProfile {
    private Long id;
    private String introduction;
    private String resume;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public UserProfile(String introduction, String resume) {
        this.introduction = introduction;
        this.resume = resume;
    }

    public Long getId() {
        return id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getResume() {
        return resume;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
