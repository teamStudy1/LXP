package com.lxp.util;

public enum QueryType {

    // user_queries.xml
    USER_FIND_BY_ID("user.findById"),
    USER_FIND_BY_NAME("user.findByName");

    private final String key;

    QueryType(String key) {
        this.key = key;
    }

    public String getQuery() {
        return QueryUtil.getQuery(this.key);
    }
}
