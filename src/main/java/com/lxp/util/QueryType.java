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
    ;

    private final String key;

    QueryType(String key) {
        this.key = key;
    }

    public String getQuery() {
        return QueryUtil.getQuery(this.key);
    }
}
