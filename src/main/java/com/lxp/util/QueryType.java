package com.lxp.util;

public enum QueryType {

    // user_queries.xml
    USER_FIND_BY_ID("user.findById"),
    USER_FIND_ROLE_BY_ID("user.findRoleById"),
    USER_EXISTS_BY_ID("user.existsById"),
    USER_EXISTS_BY_EMAIL("user.existsByEmail"),
    USER_SAVE("user.save"),
    USER_PROFILE("userProfile.save"),
    USER_UPDATE_ROLE("user.updateRoleById"),
    USER_UPDATE_ACTIVE_STATUS("user.updateActiveStatus"),

    // category
    CATEGORY_SAVE("category.save"),
    CATEGORY_FIND_BY_ID("category.findById"),
    CATEGORY_FIND_ALL_BY_PARENT_ID("category.findAllByParentId"),
    CATEGORY_FIND_ALL_BY_PARENT_ID_IS_NULL("category.findAllByParentIdIsNull"),
    CATEGORY_DELETE_BY_ID("category.deleteById"),
    CATEGORY_UPDATE_NAME("category.updateName"),
    CATEGORY_UPDATE_PARENT("category.updateParent"),
    CATEGORY_FIND_BY_NAME_CONTAING("category.findByNameContaining"),

    // course
    COURSE_FIND_ALL("course.findAll"),
    COURSE_FIND_BY_ID("course.findById"),
    COURSE_FIND_BY_TITLE("course.findByTitle"),
    COURSE_SAVE("course.save"),
    COURSE_UPDATE_TITLE("course.updateTitle"),
    COURSE_TAG_SAVE("course_tag.save"),
    //tag
    TAG_FIND_BY_NAME("tag.findByName"),
    TAG_SAVE("tag.save"),
    // course_detail
    COURSE_DETAIL_SAVE("course_detail.save"),
    ENROLLMENT_SAVE("enrollment.save"),
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
