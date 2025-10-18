package com.lxp.util;

public enum QueryType {

    // user
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
    CATEGORY_FIND_BY_NAME("category.findByName"),
    CATEGORY_FIND_ALL_BY_PARENT_ID("category.findAllByParentId"),
    CATEGORY_FIND_ALL_BY_PARENT_ID_IS_NULL("category.findAllByParentIdIsNull"),
    CATEGORY_EXISTS_BY_ID("category.existsById"),
    CATEGORY_DELETE_BY_ID("category.deleteById"),
    CATEGORY_UPDATE_NAME("category.updateName"),
    CATEGORY_UPDATE_PARENT("category.updateParent"),
    ;

    private final String key;

    QueryType(String key) {
        this.key = key;
    }

    public String getQuery() {
        return QueryUtil.getQuery(this.key);
    }
}
