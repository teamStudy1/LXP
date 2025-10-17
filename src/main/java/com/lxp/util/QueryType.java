package com.lxp.util;

public enum QueryType {

    // user_queries.xml
    USER_FIND_BY_ID("user.findById"),
    USER_FIND_ROLE_BY_ID("user.findRoleById"),
    USER_EXISTS_BY_ID("user.existsById"),

    // course_queries.xml
    COURSE_FIND_BY_ID("course.findById"),


    //section_queries.xml
    SECTION_FIND_ALL_BY_COURSE_ID("section.findAllByCourseId"),

    //lecture_queries.xml
    LECTURE_FIND_ALL_BY_SECTION_IDS("section.findAllBySectionIds"),

    //tag
    TAG_FIND_ALL_BY_COURSE_ID("tag.findAllByCourseId"),

    ;
    private final String key;

    QueryType(String key) {
        this.key = key;
    }

    public String getQuery() {
        return QueryUtil.getQuery(this.key);
    }
}
