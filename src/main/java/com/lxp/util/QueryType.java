package com.lxp.util;

public enum QueryType {
    // user
    USER_FIND_BY_ID("user.findById"),
    USER_FIND_ROLE_BY_ID("user.findRoleById"),
    USER_EXISTS_BY_ID("user.existsById"),

    // course
    COURSE_FIND_ALL("course.findAll"),
    COURSE_FIND_BY_ID("course.findById"),
    COURSE_FIND_BY_TITLE("course.findByTitle"),
    COURSE_SAVE("course.save"),
    COURSE_UPDATE_TITLE("course.updateTitle"),
    // course_detail
    COURSE_DETAIL_SAVE("course_detail.save"),
    ENROLLMENT_SAVE("enrollment.save"),       // [추가]
    ENROLLMENT_DELETE("enrollment.delete");
    ;



    private final String key;

    QueryType(String key) {
        this.key = key;
    }

    public String getQuery() {
        return QueryUtil.getQuery(this.key);
    }
}