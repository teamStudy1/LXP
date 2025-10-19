package com.lxp.util;

public enum QueryType {

    // course_queries.xml
    COURSE_FIND_BY_ID("course.findById"),
    COURSE_FIND_BY_ID_JOIN_INSTRUCTOR("course.findByIdJoinInstructor"),
    COURSE_FIND_BY_ID_JOIN_CATEGORY("course.findByIdJoinCategory"),
    COURSE_INSERT("course.insert"),
    COURSE_FIND_ALL("course.findAll"),
    COURSE_FIND_BY_KEYWORD("course.findByKeyword"),
    COURSE_UPDATE("course.update"),

    // section_queries.xml
    SECTION_FIND_ALL_BY_COURSE_ID("section.findAllByCourseId"),
    SECTION_BATCH_INSERT("section.batchInsert"),
    // lecture_queries.xml
    LECTURE_FIND_ALL_BY_SECTION_IDS("lecture.findAllBySectionIds"),
    LECTURE_BATCH_INSERT("lecture.batchInsert"),
    // tag
    TAG_FIND_ALL_BY_COURSE_ID("tag.findAllByCourseId"),
    TAG_FIND_BY_NAME_IN("tag.findByNameIn"),
    TAG_INSERT("tag.insert"),

    COURSE_TAG_BATCH_INSERT("course_tag.batchInsert"),

    // user
    USER_FIND_BY_ID("user.findById"),
    USER_FIND_ROLE_BY_ID("user.findRoleById"),
    USER_EXISTS_BY_ID("user.existsById"),
    USER_EXISTS_BY_EMAIL("user.existsByEmail"),
    USER_SAVE("user.save"),
    USER_PROFILE("userProfile.save"),
    USER_UPDATE("user.update"),

    // category
    CATEGORY_FIND_ALL_DESCENDANTS_BY_PARENT_ID("category.findAllDescendantsByParentId"),
    CATEGORY_FIND_ALL_BY_ID("category.findAllByCategoryId"),
    CATEGORY_FIND_BY_ID("category.findById"),
    CATEGORY_SAVE("category.save"),
    CATEGORY_FIND_ALL_BY_PARENT_ID("category.findAllByParentId"),
    CATEGORY_FIND_ALL_BY_PARENT_ID_IS_NULL("category.findAllByParentIdIsNull"),
    CATEGORY_DELETE_BY_ID("category.deleteById"),
    CATEGORY_UPDATE_NAME("category.updateName"),
    CATEGORY_UPDATE_PARENT("category.updateParent"),
    CATEGORY_FIND_BY_NAME_CONTAING("category.findByNameContaining");

    private final String key;

    QueryType(String key) {
        this.key = key;
    }

    public String getQuery() {
        return QueryUtil.getQuery(this.key);
    }
}
