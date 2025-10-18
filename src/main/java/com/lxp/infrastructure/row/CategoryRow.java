package com.lxp.infrastructure.row;

import java.sql.Timestamp;


/*
* DB의 Category 테이블과 1:1로
* 정혹하게 일치하는 '날것 그대로의 접시'
* DB에서 꺼내온 데이터를 가장 먼저 담는 역할을 합니다.*/
public record CategoryRow(
        Long categoryId,
        String name,
        Long parentId,
        int depth,
        Timestamp createdAt,
        Timestamp updatedAt
) {}
