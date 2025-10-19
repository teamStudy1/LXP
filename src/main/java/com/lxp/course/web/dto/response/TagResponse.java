package com.lxp.course.web.dto.response;

import com.lxp.course.domain.model.Tag;

public record TagResponse(Long id, String name) {
    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}
