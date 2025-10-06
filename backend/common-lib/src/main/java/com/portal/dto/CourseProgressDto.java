package com.portal.dto;

public record CourseProgressDto(
        Long courseId,
        String courseTitle,
        int progressPercent,
        boolean completed
) { }
