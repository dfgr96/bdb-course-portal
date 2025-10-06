package com.portal.dto;

import java.sql.Timestamp;

public record ModuleProgressDto(
        Long moduleId,
        String moduleTitle,
        String contentUrl,
        String contentType,
        Integer percent,
        Timestamp completedAt
) {}
