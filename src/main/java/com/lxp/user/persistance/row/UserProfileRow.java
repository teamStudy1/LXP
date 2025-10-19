package com.lxp.user.persistance.row;

import java.sql.Timestamp;

public record UserProfileRow(
        Long id, String introduction, String resume, Timestamp createdAt, Timestamp updatedAt) {}
